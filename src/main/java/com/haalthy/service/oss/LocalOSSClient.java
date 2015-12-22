package com.haalthy.service.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import com.aliyun.oss.model.OSSObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;


/**
 * 本地OSS客户端实现
 * 简单的将文件保存在本地，提高速度
 * 注意，该类只是简单的实现部分方法
 * 并没有全部实现，只提供开发测试中使用
 * @author John
 * @version 1.0  2015-5-22 12:36
 */
public class LocalOSSClient extends OSSAdapter implements OSS {
	
    OSSDataTransfer dataTransfer;
	
	public LocalOSSClient(OSSDataTransfer dataTransfer) {
		this.dataTransfer = dataTransfer;
	}

	@Override
	public Bucket createBucket(String bucketName) throws OSSException,
			ClientException {
		return dataTransfer.createBucket(bucketName);
	}

	@Override
	public void deleteBucket(String bucketName) throws OSSException, ClientException {
        dataTransfer.deleteBucket(bucketName);
	}

	@Override
	public void deleteObject(String bucketName, String key) throws OSSException,
			ClientException {
		dataTransfer.deleteObject(bucketName, key);
	}

	@Override
	public boolean doesBucketExist(String bucketName) throws OSSException,
			ClientException {
		return dataTransfer.doesBucketExist(bucketName);
	}

	@Override
	public com.aliyun.oss.model.OSSObject getObject(String bucketName, String key) throws OSSException,
			ClientException {
        com.aliyun.oss.model.OSSObject obj = dataTransfer.getObject(bucketName, key);
        obj.setObjectMetadata(getObjectMetadata(bucketName, key));
        return obj;
	}


	@Override
	public ObjectMetadata getObjectMetadata(String bucketName, String key)
			throws OSSException, ClientException {
		return dataTransfer.getObjectMetadata(bucketName, key);
	}


	@Override
	public ObjectListing listObjects(String bucketName) throws OSSException,
			ClientException {
		return dataTransfer.listObjects(bucketName);
	}

	@Override
	public ObjectListing listObjects(String bucketName, String prefix)
			throws OSSException, ClientException {
		return dataTransfer.listObjects(bucketName, prefix);
	}

	@Override
	public PutObjectResult putObject(String bucketName, String key,
			InputStream input, ObjectMetadata metadata) throws OSSException,
			ClientException {
        byte[] data = null;
        Map<String,String> userMetadata = metadata.getUserMetadata();
        metadata = new ObjectMetadata();
        metadata.setUserMetadata(userMetadata);
        try {
            data = IOUtils.readStreamAsByteArray(input);
        } catch (IOException e) {

        }
        return dataTransfer.putObject(bucketName, key, data, metadata);
	}

    @Override
    public CopyObjectResult copyObject(String srcBucket, String srcKey, String desBucket, String desKey) throws OSSException, ClientException {
        OSSObject o = getObject(srcBucket, srcKey);
        putObject(desBucket, desKey, o.getObjectContent(), o.getObjectMetadata());
        CopyObjectResult result = new CopyObjectResult();
        result.setEtag(o.getObjectMetadata().getUserMetadata().get("remark"));
        result.setLastModified(new Date());
        return result;
    }
}
