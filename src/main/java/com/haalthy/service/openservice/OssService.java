package com.haalthy.service.openservice;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.haalthy.service.controller.Interface.OSSFile;
import com.haalthy.service.oss.OSSFileOperate;
import com.haalthy.service.oss.OSSSetting;
import com.haalthy.service.oss.RefreshImgPath;

public class OssService {
    //private OssFile file;
    private OSSSetting setting = new OSSSetting();
    protected Logger logger = Logger.getLogger(this.getClass());
    
    @Autowired
	private transient  RefreshImgPath refreshImgPath;
    
    public int ossUploadFile(List<OSSFile> ossList) throws Exception
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
        for (OSSFile oss:ossList) {
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
        return refreshImgPath.refreshImg(functionType,"update",id,result.toString());
    }
}
