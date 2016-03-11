package com.haalthy.service.controller.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.haalthy.service.controller.Interface.OSSFile;
import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.oss.OSSFileOperate;
import com.haalthy.service.oss.OSSSetting;
import com.haalthy.service.oss.RefreshImgPath;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Ken on 2015-12-28.
 */
@Controller
@RequestMapping("/open/oss")
public class OssController {

    //private OssFile file;
    @Autowired
    private transient RefreshImgPath refreshImgPath;
    private OSSSetting setting ;
    protected Logger logger = Logger.getLogger(this.getClass());

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    public static final String STS_API_VERSION = "2015-04-01";

    static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public int ossUploadFile(@RequestBody OSSFile oss) throws Exception
    {
        //get file type
        setting =OSSSetting.getInstance();
        String fileName = setting.getFileName(oss.getFileType());
        //get file name

        OSSClient client = new OSSClient(setting.getEndpoint(),setting.getAccess_ID(),setting.getSecret_ID());

        if(!client.doesBucketExist(setting.getBucket()))
            client.createBucket(setting.getBucket());

        ByteArrayInputStream in = new ByteArrayInputStream(oss.getImg());
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(in.available());
        objectMeta.setContentType(setting.getContentType(oss.getFileType()));
        OSSFileOperate simple = new OSSFileOperate();
        simple.putSimpleObject(client,setting.getBucket(), setting.getOSSKey(oss.getFunctionType(), fileName),
                in, objectMeta);
        in.close();
        client.shutdown();

        return refreshImgPath.refreshImg(oss.getFunctionType(),oss.getModifyType(),oss.getId(),
                setting.getUrl(oss.getFunctionType(),fileName), -1);
    }

    @RequestMapping(value = "/uploadimgs", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public String ossUploadFileList(@RequestBody List<OSSFile> ossList) throws Exception
    {
        //get file type
        setting = OSSSetting.getInstance();
        //get file name

        OSSClient client = new OSSClient(setting.getEndpoint(),setting.getAccess_ID(),setting.getSecret_ID());

        if(!client.doesBucketExist(setting.getBucket()))
            client.createBucket(setting.getBucket());
        String functionType = "";
        String id = "";
        StringBuilder result = new StringBuilder();
        for (OSSFile oss:ossList
             ) {
            String fileName = setting.getFileName(oss.getFileType());

            ByteArrayInputStream in = new ByteArrayInputStream(oss.getImg());
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(in.available());
            objectMeta.setContentType(setting.getContentType(oss.getFileType()));
            OSSFileOperate simple = new OSSFileOperate();
            simple.putSimpleObject(client,setting.getBucket(), setting.getOSSKey(oss.getFunctionType(), fileName),
                    in, objectMeta);
            in.close();
            functionType = oss.getFunctionType();
            id = oss.getId();
            result.append(setting.getUrl(oss.getFunctionType(),fileName));
        }

        client.shutdown();

        try {
            refreshImgPath.refreshImg(functionType,"update",id,result.toString(), -1);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage()+"functionType:"+functionType+";id:"+id+";filePath:"+result.toString();
        }
        return "functionType:"+functionType+";id:"+id+";filePath:"+result.toString();
    }

    @RequestMapping(value = "/uploadtestsimple", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public int ossUploadFileTestSimple(@RequestBody byte[] img)
            throws Exception
    {
        //get file type
        setting = OSSSetting.getInstance();
        String fileName = setting.getFileName("jpg");
        //get file name

        OSSClient client = new OSSClient(setting.getEndpoint(),setting.getAccess_ID(),setting.getSecret_ID());

        if(!client.doesBucketExist(setting.getBucket()))
            client.createBucket(setting.getBucket());

        ByteArrayInputStream in = new ByteArrayInputStream(img);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(in.available());
        objectMeta.setContentType(setting.getContentType("jpg"));

        OSSFileOperate simple = new OSSFileOperate();
        simple.putSimpleObject(client,setting.getBucket(), setting.getOSSKey("Test", fileName), in, objectMeta);
        in.close();
        client.shutdown();

        return 0;
    }


    @RequestMapping(value = "/getToken", method = RequestMethod.GET, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public Response ossSTSGetToken()
    {
        Response res = new Response();
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        String accessKeyId = "CQblESpYp6rvGUqK";
        String accessKeySecret = "DdVDpEfJXWOWxvBR9lEkA3aANt1OpK";

        // AssumeRole API 请求参数: RoleArn, RoleSessionName, Polciy, and DurationSeconds

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = "acs:ram::1453842368624030:role/aliyunosstokengeneratorrole";

        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "external-username";

        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:GetObject\", \n" +
                "                \"oss:PutObject\" \n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:haalthy\",\n" +
                "                \"acs:oss:*:*:haalthy/*\"\n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        try {
            final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
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