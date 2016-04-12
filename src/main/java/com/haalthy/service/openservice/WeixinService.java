package com.haalthy.service.openservice;

import com.haalthy.service.common.JsonUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author John
 * @version 1.0 2015/8/19 15:48
 */
@Service
public class WeixinService {


    protected Logger logger=Logger.getLogger(this.getClass());
    String appid = "wxe1ddd7a7d6ab1bf7";

    String secret = "578bff819bc390448e26adb75c43190e";

    String token;
    String apiTicket;
    long updateTokenTime = 0;
    long updateApiTokenTime = 0;

    HttpClient client = new HttpClient();

    public WeixinService() {
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    }

    /**
     * 获取基础Token
     * @return
     * @throws Exception
     */
    public String _getToken() throws Exception {
        GetMethod method = new GetMethod("https://api.weixin.qq.com/cgi-bin/token");
        method.setQueryString(new NameValuePair[]{
                new NameValuePair("grant_type", "client_credential"),
                new NameValuePair("appid", appid),
                new NameValuePair("secret", secret)
        });
        client.executeMethod(method);
        String resp = method.getResponseBodyAsString();
        Map<String, String> map = JsonUtils.fromJson(resp);
        System.out.println(resp);
        logger.trace(resp);
        return map.get("access_token");
    }

    public String getToken() throws Exception {
        // token 有效期为2小时，在大于115分钟的时候，重新获取
        if (token == null || System.currentTimeMillis() - updateTokenTime > 6900000) {
            updateTokenTime = System.currentTimeMillis();
            token = _getToken();
        }
        return token;
    }

    public String getAppid() {
        return appid;
    }

    public long getExpires() {
        return 7000 + (updateTokenTime - System.currentTimeMillis()) / 1000;
    }

    /**
     * 获取access_token
     * 用户获取用户信息
     * @param code
     * @return
     * @throws Exception
     */
    public Map<String,String> getAccessToken(String code) throws Exception {
        GetMethod method = new GetMethod("https://api.weixin.qq.com/sns/oauth2/access_token");
        method.setQueryString(new NameValuePair[]{
                new NameValuePair("grant_type", "authorization_code"),
                new NameValuePair("appid", appid),
                new NameValuePair("secret", secret),
                new NameValuePair("code", code)
        });
        client.executeMethod(method);
        String resp = method.getResponseBodyAsString();
        Map<String, String> map = JsonUtils.fromJson(resp);
        System.out.println(code + " " +resp);
        logger.trace(code + " " +resp);
        return map;
    }

    /**
     * 获取ApiTicket
     * @return
     * @throws Exception
     */
    public String _getApiTicket() throws Exception {
        GetMethod method = new GetMethod("https://api.weixin.qq.com/cgi-bin/ticket/getticket");
        method.setQueryString(new NameValuePair[]{
                new NameValuePair("type", "jsapi"),
                new NameValuePair("access_token", getToken())
        });
        client.executeMethod(method);
        String resp = method.getResponseBodyAsString();
        Map<String, String> map = JsonUtils.fromJson(resp);
        System.out.println(resp);
        return map.get("ticket");
    }

    public String getApiTicket() throws Exception {
        // token 有效期为2小时，在大于115分钟的时候，重新获取
        if (apiTicket == null || System.currentTimeMillis() - updateApiTokenTime > 6900000) {
            updateApiTokenTime = System.currentTimeMillis();
            apiTicket = _getApiTicket();
        }
        return apiTicket;
    }

    /**
     * 获取签名
     * @return
     * @throws Exception
     */
    public String getSignature(String strUrl,String timestamp,String noncestr) throws Exception
    {
        /*
        * jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
        * &noncestr=Wm3WZYTPz0wzccnW
        * &timestamp=1414587457
        * &url=http://mp.weixin.qq.com?params=value
        * */
//        String ApiTicket = "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg";

        String srcString = "jsapi_ticket="+getApiTicket()
                          +"&noncestr="+noncestr
                          +"&timestamp="+timestamp
                          +"&url="+strUrl;

        return DigestUtils.shaHex(srcString);
    }

    public String getExampleSignature()
    {
        String srcString = "jsapi_ticket="+"sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg"
                +"&noncestr=Wm3WZYTPz0wzccnW"
                +"&timestamp=1414587457"
                +"&url=http://mp.weixin.qq.com?params=value";

        return  DigestUtils.shaHex(srcString);
    }

    /**
     * 获取用户信息
     * @param accessCode
     * @param openId
     * @return
     * @throws Exception
     */
    public Map<String,String> getUserInfo(String accessCode, String openId) throws Exception {
        GetMethod method = new GetMethod("https://api.weixin.qq.com/sns/userinfo");
        method.setQueryString(new NameValuePair[]{
                new NameValuePair("access_token", accessCode),
                new NameValuePair("openid", openId),
                new NameValuePair("lang", "zh_CN")
        });
        client.executeMethod(method);
        String resp = method.getResponseBodyAsString();
        Map<String, String> map = JsonUtils.fromJson(resp);
        System.out.println(resp);
        logger.trace(openId + " "+resp);
        return map;
    }

    public Map<String,String> getShortUrl(String longUrl) throws Exception {
        String access_token = getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl";
        PostMethod method = new PostMethod(url);
        method.setQueryString(new NameValuePair[]{
                new NameValuePair("access_token", access_token),
                new NameValuePair("action", "long2short")
        });
        method.setRequestBody(new NameValuePair[]{
                new NameValuePair("long_url", longUrl),

        });
        client.executeMethod(method);
        String resp = method.getResponseBodyAsString();
        Map<String, String> map = JsonUtils.fromJson(resp);
        System.out.println(resp);
        return map;
    }

    public void sendMessage(String content) throws Exception {
        String access_token = getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
        PostMethod method = new PostMethod(url);
        System.out.println(content);
        method.setRequestEntity(new StringRequestEntity(content));
        client.executeMethod(method);
        System.out.println(method.getResponseBodyAsString());
    }

    public byte[] downloadFile(String mediaId) throws Exception {
        String accessToken = getToken();
        GetMethod method = new GetMethod("http://file.api.weixin.qq.com/cgi-bin/media/get");
        method.setQueryString(new NameValuePair[]{
                new NameValuePair("access_token", accessToken),
                new NameValuePair("media_id", mediaId)
        });
        client.executeMethod(method);
        return method.getResponseBody();
    }



}
