package com.haalthy.service.controller.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.haalthy.service.controller.Interface.OSSFile;
import com.haalthy.service.oss.OSSPutSimple;
import com.haalthy.service.oss.OSSSetting;
import com.haalthy.service.oss.RefreshImgPath;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;

/**
 * Created by Ken on 2015-12-28.
 */
@Controller
@RequestMapping("/open/oss")
public class OssController {

    //private OssFile file;
    private OSSSetting setting = new OSSSetting();
    protected Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public int ossUploadFile(@RequestBody OSSFile oss) throws Exception
    {
        //get file type
        String fileName = setting.getFileName(oss.getFileType());
        //get file name

        OSSClient client = new OSSClient(setting.getEndpoint(),setting.getAccess_ID(),setting.getSecret_ID());

        if(!client.doesBucketExist(setting.getBucket()))
            client.createBucket(setting.getBucket());

        ByteArrayInputStream in = new ByteArrayInputStream(oss.getImg());
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(in.available());
        objectMeta.setContentType(setting.getContentType(oss.getFileType()));
        OSSPutSimple simple = new OSSPutSimple();
        simple.putSimpleObject(client,setting.getBucket(), setting.getOSSKey(oss.getFunctionType(), fileName),
                in, objectMeta);
        in.close();
        client.shutdown();

        RefreshImgPath refreshImgPath = new RefreshImgPath();
        return refreshImgPath.refreshImg(oss.getFunctionType(),oss.getModifyType(),oss.getId(),
                setting.getUrl(oss.getFunctionType(),fileName));
    }

    @RequestMapping(value = "/uploadtestsimple", method = RequestMethod.POST, headers = "Accept=application/json",
            produces = {"application/json"})
    @ResponseBody
    public String ossUploadFileTestSimple(@RequestBody byte[] img)
            throws Exception
    {
        //get file type
        String fileName = setting.getFileName("jpg");
        //get file name

        OSSClient client = new OSSClient(setting.getEndpoint(),setting.getAccess_ID(),setting.getSecret_ID());

        if(!client.doesBucketExist(setting.getBucket()))
            client.createBucket(setting.getBucket());

        ByteArrayInputStream in = new ByteArrayInputStream(img);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(in.available());
        objectMeta.setContentType(setting.getContentType("jpg"));

        OSSPutSimple simple = new OSSPutSimple();
        simple.putSimpleObject(client,setting.getBucket(), setting.getOSSKey("Test", fileName), in, objectMeta);
        in.close();
        client.shutdown();

        return "{url:"+setting.getUrl("Test",fileName)+"}";
    }
}