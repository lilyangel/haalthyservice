package com.haalthy.service.openservice;

import com.haalthy.service.cache.RedisCache;
import com.haalthy.service.common.DateUtils;
import com.haalthy.service.common.EncodeUtil;
import com.haalthy.service.domain.EMail;
import com.haalthy.service.persistence.EMailMapper;
import com.haalthy.service.sms.SmsSDKClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ken on 2016-01-05.
 */
@Service
public class AuthCodeService {
    protected Logger logger=Logger.getLogger(this.getClass());
    private  static String eMailTemplate = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body><div>尊敬的用户：</div><div>&nbsp; &nbsp; 您好！</div><div>&nbsp; &nbsp; 您重置密码的验证码为：<b><span style=\"color: rgb(0, 128, 0);\">&#8203;{authCode}</span>（不区分大小写）</b></div><div>&nbsp; &nbsp; 验证码有效期为2天（有效期至：<span style=\"color: rgb(0, 128, 0);\"><b>{validity}</b></span>），请尽快进入APP重置您的密码。</div><div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;haalthy团队</div></body>";

    @Autowired
    private EMailMapper eMailMapper;

    @Autowired
    private RedisCache redisCache;



    public int addEmailAuthCode(String toEmail) throws Exception {
        int iReturn = 0;
        String strAuthCode = EncodeUtil.getAuthCode(8);
        String context = (eMailTemplate.replace("{authCode}", strAuthCode)).replace("{validity}", DateUtils.addDay2(new Date(), 2));
        EMail eMail = new EMail();
        eMail.setContent(context);
        eMail.setCreateTime(new Date());
        eMail.setPriority(0);
        eMail.setSubject("haalthy用户验证码");
        eMail.setToEmail(toEmail.toLowerCase());

        eMailMapper.insertEmail(eMail);
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
        redisCache.putObject(stringBuilder.toString(),EncodeUtil.md5Encrypt(strAuthCode),1200);
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
