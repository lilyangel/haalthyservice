package com.haalthy.service.oss;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ken on 2015-12-29.
 */
public class OSSSetting {
    //private OssFile file;
    private static String Bucket = "haalthy";
    private static String Access_ID = "vUvBZoEgcPosj9ZB";
    private static String Secret_ID = "59cbtCaHVo44XXaic6n5lDQoyp9cBu";
    private static String Endpoint = "http://oss-cn-beijing.aliyuncs.com/";
    private static String domain = "http://haalthy.oss-cn-beijing.aliyuncs.com/";

    public static String getBucket() {
        return Bucket;
    }

    public static String getAccess_ID() {
        return Access_ID;
    }

    public static String getSecret_ID() {
        return Secret_ID;
    }

    public static String getEndpoint() {
        return Endpoint;
    }

    public static String getDomain() {
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
}
