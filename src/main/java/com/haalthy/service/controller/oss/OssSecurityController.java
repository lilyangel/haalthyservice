package com.haalthy.service.controller.oss;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.oss.OSSSetting;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ken on 2016-03-10.
 */

@Controller
@RequestMapping("/security/oss")
public class OssSecurityController {
    private OSSSetting setting ;
    protected Logger logger = Logger.getLogger(this.getClass());

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    public static final String STS_API_VERSION = "2015-04-01";

    static AssumeRoleResponse securityAssumeRole(String accessKeyId, String accessKeySecret,
                                         String roleArn, String roleSessionName, String policy,
                                         ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);

            // 发起请求，并得到response
            AssumeRoleResponse response = new AssumeRoleResponse();
            try {
                response = client.getAcsResponse(request);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        } catch (ClientException e) {
            throw e;
        }
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public Response ossSTSGetToken()
    {
        Response res = new Response();
        setting = OSSSetting.getInstance();
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        String accessKeyId = "CQblESpYp6rvGUqK";
        String accessKeySecret = "DdVDpEfJXWOWxvBR9lEkA3aANt1OpK";

        // AssumeRole API 请求参数: RoleArn, RoleSessionName, Polciy, and DurationSeconds

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = " acs:ram::1453842368624030:role/aliyunosstokengeneratorrole";

        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "external-username";

        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\" \n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:*\"\n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        try {
            final AssumeRoleResponse response = securityAssumeRole(accessKeyId, accessKeySecret,
                    roleArn, roleSessionName, policy, protocolType);

            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            res.setResult(1);
            res.setResultDesp("获取OSS Token成功");
            res.setContent(response);
        } catch (ClientException e) {
            res.setResult(-1);
            res.setResultDesp("获取OSS Token失败");
            res.setContent(null);
            System.out.println("Failed to get a token."+e);
        }
        return res;
    }
}
