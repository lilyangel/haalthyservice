package com.haalthy.service.controller.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.haalthy.service.controller.Interface.OSSFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ken on 2015-12-28.
 */
@Controller
@RequestMapping("/open/oss")
public class OssController {

    //private OssFile file;
    private static String strBucket = "haalthy";
    private static String Access_ID = "vUvBZoEgcPosj9ZB";
    private static String Secret_ID = "59cbtCaHVo44XXaic6n5lDQoyp9cBu";
    private static String Endpoint = "http://haalthy.oss-cn-beijing.aliyuncs.com/";

    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public String ossUploadFile(@RequestBody OSSFile oss)
            throws Exception
    {
        //get file type
        String fileType = "";
        if(oss.getFileType().equals("BMP")||oss.getFileType().equals("bmp")){fileType = "image/bmp";}
        if(oss.getFileType().equals("GIF")||oss.getFileType().equals("gif")){fileType = "image/gif";}
        if(oss.getFileType().equals("JPEG")||oss.getFileType().equals("jpeg")||
                oss.getFileType().equals("JPG")||oss.getFileType().equals("jpg")||
                oss.getFileType().equals("PNG")||oss.getFileType().equals("png")){fileType = "image/jpeg";}
        if(oss.getFileType().equals("HTML")||oss.getFileType().equals("html")){fileType = "text/html";}
        if(oss.getFileType().equals("TXT")||oss.getFileType().equals("txt")){fileType = "text/plain";}
        if(oss.getFileType().equals("VSD")||oss.getFileType().equals("vsd")){fileType = "application/vnd.visio";}
        if(oss.getFileType().equals("PPTX")||oss.getFileType().equals("pptx")||
                oss.getFileType().equals("PPT")||oss.getFileType().equals("ppt")){fileType = "application/vnd.ms-powerpoint";}
        if(oss.getFileType().equals("DOCX")||oss.getFileType().equals("docx")||
                oss.getFileType().equals("DOC")||oss.getFileType().equals("doc")){fileType = "application/msword";}
        if(oss.getFileType().equals("XML")||oss.getFileType().equals("xml")){fileType = "text/xml";}
        fileType = "text/html";

        //get file name
        String FileName = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        FileName =  sdf.format(date)+System.currentTimeMillis()+"."+oss.getFileType();


        ByteArrayInputStream in = new ByteArrayInputStream(oss.getImg());
        ObjectMetadata objectMeta = new ObjectMetadata();
        if (oss.getRemark().length > 0) {
            objectMeta.getUserMetadata().put("remark", oss.getRemark()[0]);
        }
        objectMeta.setContentLength(in.available());
        objectMeta.setContentType(fileType);

        String filePath = Endpoint +oss.getFunctionID()+"//"+FileName;

        OSSClient client = new OSSClient(Endpoint,Access_ID,Secret_ID);

        if(!client.doesBucketExist(strBucket))
            client.createBucket(strBucket);

        client.putObject(strBucket, filePath, in, objectMeta);
        in.close();

        return filePath;
    }
}