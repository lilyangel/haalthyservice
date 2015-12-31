package com.haalthy.service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 2015-12-31.
 */
public class OSSFileOperate {
    public int putSimpleObject(OSSClient client, String bucket, String key, InputStream inputStream, ObjectMetadata metadata) {
        try {
            client.putObject(bucket, key, inputStream, metadata);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        } finally {
            return 0;
        }
    }
    /*
    * fileName 为OSS文件的全路径
    *
    * */
    public int delSimpleObject(OSSClient client, String bucket, String domain, String fileName)
    {
        try {
            client.deleteObject(bucket,fileName.replace(domain,"").replace(";",""));
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        finally {
            return 0;
        }
    }

    /*
    * fileName 为OSS文件的全路径；
    * 多个文件 路径以;分割
    * */
    public int delMultiObjects(OSSClient client,String bucket,String domain,String fileName)
    {
        List<String> keys = new ArrayList<String>();
        String[] keyArray = fileName.split(";");
        for (String str: keyArray
                ) {
            keys.add(str.replace(domain,""));
        }
        try {
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(
                    new DeleteObjectsRequest(bucket).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        finally {
            return 0;
        }
    }
}
