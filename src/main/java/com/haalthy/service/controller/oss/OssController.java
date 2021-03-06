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
import com.haalthy.service.controller.Interface.OssStsToken;
import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.controller.Interface.StringRequest;
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
    public Response ossSTSGetToken(@RequestBody StringRequest request)
    {
        Response res = new Response();
        setting = OSSSetting.getInstance();
        String fileName = setting.getFileName("JPG");
        try {
            final AssumeRoleResponse response = setting.getRoleResponse();

            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());

            OssStsToken stsToken = new OssStsToken();
            stsToken.setAccessKeyID(response.getCredentials().getAccessKeyId());
            stsToken.setAccessKeySecert(response.getCredentials().getAccessKeySecret());
            stsToken.setSecurityToken(response.getCredentials().getSecurityToken());
            stsToken.setExpiration(response.getCredentials().getExpiration());
            stsToken.setEndpoint(setting.getEndpoint());
            stsToken.setObjectKey(setting.getOSSKey(request.getContent(), fileName));
            stsToken.setObjectUrl(setting.getUrl(request.getContent(), fileName));
            res.setResult(1);
            res.setResultDesp("获取OSS Token成功");
            res.setContent(stsToken);
        } catch (ClientException e) {
            res.setResult(-1);
            res.setResultDesp("获取OSS Token失败");
            res.setContent(null);
            System.out.println("Failed to get a token."+e);
        }
        return res;
    }

}