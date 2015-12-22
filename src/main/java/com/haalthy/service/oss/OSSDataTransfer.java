package com.haalthy.service.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * OSS 底层数据传输
 *
 * @author: john
 * @version: 1.0 2015-5-22 12:36
 */
public interface OSSDataTransfer {

    Bucket createBucket(String bucketName) throws OSSException;

    void deleteBucket(String bucketName) throws OSSException, ClientException;

    void deleteObject(String bucketName, String key) throws OSSException,
            ClientException;

    boolean doesBucketExist(String bucketName) throws OSSException,
            ClientException;

    com.aliyun.oss.model.OSSObject getObject(String bucketName, String key) throws OSSException,
            ClientException;

    ObjectMetadata getObjectMetadata(String bucketName, String key)
            throws OSSException, ClientException;

    ObjectListing listObjects(String bucketName) throws OSSException,
            ClientException;

    ObjectListing listObjects(String bucketName, String prefix)
            throws OSSException, ClientException;

    PutObjectResult putObject(String bucketName, String key,
                                     byte[] data, ObjectMetadata metadata) throws OSSException,
            ClientException;

}
