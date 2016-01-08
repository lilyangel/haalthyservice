package com.haalthy.service.controller.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.haalthy.service.controller.Interface.OSSFile;
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
        setting = new OSSSetting();
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
                setting.getUrl(oss.getFunctionType(),fileName));
    }

    @RequestMapping(value = "/uploadimgs", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public String ossUploadFileList(@RequestBody List<OSSFile> ossList) throws Exception
    {
        //get file type
        setting = new OSSSetting();
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
            refreshImgPath.refreshImg(functionType,"update",id,result.toString());
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
        setting = new OSSSetting();
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
}