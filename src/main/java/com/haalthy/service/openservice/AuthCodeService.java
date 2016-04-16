package com.haalthy.service.openservice;

import com.haalthy.service.cache.RedisCache;
import com.haalthy.service.common.ConfigLoader;
import com.haalthy.service.common.DateUtils;
import com.haalthy.service.common.EncodeUtil;
import com.haalthy.service.domain.EMail;
import com.haalthy.service.persistence.EMailMapper;
import com.haalthy.service.sms.SmsSDKClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;




/**
 * Created by Ken on 2016-01-05.
 */
@Service
public class AuthCodeService {
    protected Logger logger=Logger.getLogger(this.getClass());
    @Autowired
    private EMailMapper eMailMapper;

    @Autowired
    private RedisCache redisCache;

    private ConfigLoader configLoader = ConfigLoader.getInstance();

    private String from = null;
    private String host = null;
    private String port = "25";
    private String username=null;
    private String password = null;
    private String title = null;
    private String content = null;
    private String type = null;
//    private JavaMailSender javaMailSender;

    private void getMailConfig(String sender,String messager) throws UnsupportedEncodingException {
        host = configLoader.getConfigProperty("email."+sender+".host");
        from = configLoader.getConfigProperty("email."+sender+".from");
        port = configLoader.getConfigProperty("email."+sender+".port");
        username = configLoader.getConfigProperty("email."+sender+".username");
        password = configLoader.getConfigProperty("email."+sender+".password");
        title = new String(configLoader
                .getConfigProperty("email."+messager+".title")
                .getBytes("ISO-8859-1"),
                "utf-8");
        content = new String(configLoader
                        .getConfigProperty("email."+messager+".content")
                        .getBytes("ISO-8859-1"),
                "utf-8");
        type = configLoader.getConfigProperty("email."+messager+".type");

        logger.error(host+"\n"+from+"\n"+port+"\n"+username+"\n"+password+"\n");
    }

    private String setContent(String content,Map<String,String> params)
    {
        if(params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()
                    ) {
                content = content.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        //String result = content;
        return content;
    }

    public void sendEmail(String receiver, Map<String,String>params) throws MessagingException,UnsupportedEncodingException
    {
        try {
            getMailConfig("info","authcode");
            String contentReal = setContent(content, params);
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            //props.put("mail.smtp.starttls.enable", "true");// 使用 STARTTLS安全连接
            props.put("mail.smtp.port", port); //google使用465或587端口
            props.put("mail.smtp.auth", "true"); // 使用验证
            // props.put("mail.debug", "true");

            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session sendMailSession = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username,password);
                }
            });

            // 创建邮件对象
            Message msg = new MimeMessage(sendMailSession);

            // 发件人
            msg.setFrom(new InternetAddress(from));

            msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver));

            msg.setSubject(title);
            msg.setContent(contentReal,"text/html;charset=UTF-8");
            Transport.send(msg);
        }
        catch (MessagingException e)
        {
            logger.error(e);
        }
    }

    public int addEmailAuthCode(String toEmail) throws Exception {
        int iReturn = 0;
        String strAuthCode = EncodeUtil.getAuthCode(8);
        Map<String,String> map = new HashMap<String,String>();
        map.put("authCode",strAuthCode);
        map.put("validity",DateUtils.addDay2(new Date(), 2));
        sendEmail(toEmail.toLowerCase(),map);

        StringBuilder stringBuilder = new StringBuilder("AuthCode.Email.");
        StringBuilder append = stringBuilder.append(toEmail.toLowerCase());
        redisCache.putObject(stringBuilder.toString(),EncodeUtil.md5Encrypt(strAuthCode),172800);

        iReturn = 0;

        return iReturn;
    }

    public int authEmailAuthCode( String toEmail, String authCode) throws Exception{
        int iReturn = 1;
        String lastAuthCode = EncodeUtil.md5Encrypt(authCode.toUpperCase());
        StringBuilder stringBuilder = new StringBuilder("AuthCode.Email.");
        stringBuilder.append(toEmail.toLowerCase());
        try {
            Object oReturn = redisCache.getObject(stringBuilder.toString());
            if (oReturn.toString().equals(lastAuthCode)){
                redisCache.deleteObject(stringBuilder.toString());
                logger.info("delete key");
                iReturn = 0;
            }
            else
                iReturn = -1;

        } catch (Exception e) {
            e.printStackTrace();
            iReturn = -2;
        }
        return iReturn;
    }


    public String addMobileAuthCode(String toMobile) throws Exception{
        String strAuthCode = EncodeUtil.getNumericAuthCode(6);
        /*
        * send sms
        * */
        logger.info(strAuthCode);
        List<String> param = new ArrayList<String>();
        param.add(strAuthCode);
        param.add("2");

        logger.info(param.toArray());
        SmsSDKClient smsSDKClient = SmsSDKClient.getInstance();
        logger.info(smsSDKClient);
        smsSDKClient.sendAuthSms(toMobile,param);
        logger.info(smsSDKClient);

        /*
        *
        * */
        StringBuilder stringBuilder = new StringBuilder("AuthCode.Mobile.");
        StringBuilder append = stringBuilder.append(toMobile.toLowerCase());
        redisCache.putObject(stringBuilder.toString(),EncodeUtil.md5Encrypt(strAuthCode),3000);
        return strAuthCode;
    }


    public int authMobileAuthCode(String toMobile, String authCode) throws Exception {
        int iReturn = 1;
        String lastAuthCode = EncodeUtil.md5Encrypt(authCode.toUpperCase());
        StringBuilder stringBuilder = new StringBuilder("AuthCode.Mobile.");
        stringBuilder.append(toMobile.toLowerCase());
        try {
            Object oReturn = redisCache.getObject(stringBuilder.toString());
            if (oReturn.toString().equals(lastAuthCode)){
                redisCache.deleteObject(stringBuilder.toString());
                logger.info("delete key");
                iReturn = 0;
            }
            else
                iReturn = -1;

        } catch (Exception e) {
            e.printStackTrace();
            iReturn = -2;
        }
        return iReturn;
    }
}
