package com.haalthy.service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.InputStream;

/**
 * Created by Ken on 2015-12-29.
 */
public class OSSPutSimple {
    public int putSimpleObject(OSSClient client, String bucket, String key, InputStream inputStream,ObjectMetadata metadata)
    {
        try {
            client.putObject(bucket, key, inputStream, metadata);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        finally {
            return 0;
        }
    }

    public int delSimpleObject(OSSClient client,String bucket,String domain,String fileName)
    {
        try {
            client.deleteObject(bucket,fileName.replace(domain,""));
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        finally {
            return 0;
        }
}
}
