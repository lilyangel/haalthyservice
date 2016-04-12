package com.haalthy.service.oss;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.haalthy.service.common.ConfigLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ken on 2015-12-29.
 */
public class OSSSetting {
    //private OssFile file;
    private static String Bucket;
    private static String Access_ID;
    private static String Secret_ID;
    private static String Endpoint;
    private static String domain;
    private static String roleArn;
    private static String region;
    private static String stsApiVersion;
    private static String roleSession;
    private static String armAccessID;
    private static String armSecretKey;
    private long updateTokenTime = 0;
    private long updateApiTokenTime = 0;

    private AssumeRoleResponse roleResponse;

    private static String policy = "{\n" +
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

    private static OSSSetting ossSetting;

    public static synchronized OSSSetting getInstance()
    {
        if(ossSetting == null)
            ossSetting = new OSSSetting();
        return ossSetting;
    }

    private OSSSetting()
    {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        Bucket = configLoader.getConfigProperty("oss.Bucket");
        Access_ID = configLoader.getConfigProperty("oss.AccessID");
        Secret_ID = configLoader.getConfigProperty("oss.SecretID");
        Endpoint = configLoader.getConfigProperty("oss.Endpoint");
        domain = configLoader.getConfigProperty("oss.domain");
        roleArn = configLoader.getConfigProperty("oss.roleArn");
        region = configLoader.getConfigProperty("oss.region");
        stsApiVersion = configLoader.getConfigProperty("oss.stsApiVersion");
        roleSession = configLoader.getConfigProperty("oss.roleSession");
        armAccessID = configLoader.getConfigProperty("oss.armAccessID");
        armSecretKey = configLoader.getConfigProperty("oss.armSecretKey");
        roleResponse = null;

    }

    public String getBucket() {
        return Bucket;
    }

    public String getAccess_ID() {
        return Access_ID;
    }

    public String getSecret_ID() {
        return Secret_ID;
    }

    public String getEndpoint() {
        return Endpoint;
    }

    public String getDomain() {
        return domain;
    }

    public String getFileName(String fileType)
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(date)+"."+fileType;
    }
    
    public String getOSSKey(String functionID,String fileName)
    {
        return functionID+"/"+fileName;
    }
    
    public String getUrl(String ossKey)
    {
        return domain + ossKey;
    }
    
    public String getUrl(String functionID,String fileName)
    {
        return domain + getOSSKey(functionID,fileName)+";";
    }
    
    public String getContentType(String fileType)
    {
        if(fileType.toLowerCase().equals("bmp")){return "image/bmp";}
        if(fileType.toLowerCase().equals("gif")){return "image/gif";}
        if(fileType.toLowerCase().equals("jpeg")||
                fileType.toLowerCase().equals("jpg")||
                fileType.toLowerCase().equals("png")){return "image/jpeg";}
        if(fileType.toLowerCase().equals("html")){return "text/html";}
        if(fileType.toLowerCase().equals("txt")){return "text/plain";}
        if(fileType.toLowerCase().equals("vsd")){return "application/vnd.visio";}
        if(fileType.toLowerCase().equals("pptx")||
                fileType.toLowerCase().equals("ppt")){return "application/vnd.ms-powerpoint";}
        if(fileType.toLowerCase().equals("docx")||
                fileType.toLowerCase().equals("doc")){return "application/msword";}
        if(fileType.toLowerCase().equals("xml")){return "text/xml";}
        return "text/html";
    }

    public AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
                                         String roleArn, String roleSessionName, String policy,
                                         ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(stsApiVersion, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(region);
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

    public AssumeRoleResponse getRoleResponse() {
        if(roleResponse == null
        || System.currentTimeMillis() - updateTokenTime > 6900000)
        {
            updateTokenTime = System.currentTimeMillis();
            try {
                roleResponse = assumeRole(armAccessID, armSecretKey,
                        roleArn, roleSession, policy, ProtocolType.HTTPS);
            }
            catch (ClientException e)
            {
                roleResponse = null;
                throw e;
            }
        }
        return roleResponse;
    }

}
