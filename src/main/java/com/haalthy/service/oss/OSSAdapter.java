package com.haalthy.service.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.*;
import com.aliyun.oss.model.OSSObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>OSS适配器，该类继承了实现了OSS接口，但是并没有真正实现该接口中的方法
 * <p>该类不能直接使用，可以通过继承该类，然后在子类中实现需要使用的方法
 * @author john
 * @version 1.0  2015-5-22 12:36
 */
public class OSSAdapter implements OSS {


	@Override
	public Bucket createBucket(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public Bucket createBucket(CreateBucketRequest createBucketRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void deleteBucket(String s) throws OSSException, ClientException {

	}

	@Override
	public List<Bucket> listBuckets() throws OSSException, ClientException {
		return null;
	}

	@Override
	public BucketList listBuckets(String s, String s1, Integer integer) throws OSSException, ClientException {
		return null;
	}

	@Override
	public BucketList listBuckets(ListBucketsRequest listBucketsRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void setBucketAcl(String s, CannedAccessControlList cannedAccessControlList) throws OSSException, ClientException {

	}

	@Override
	public AccessControlList getBucketAcl(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void setBucketReferer(String s, BucketReferer bucketReferer) throws OSSException, ClientException {

	}

	@Override
	public BucketReferer getBucketReferer(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public String getBucketLocation(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public boolean doesBucketExist(String s) throws OSSException, ClientException {
		return false;
	}

	@Override
	public ObjectListing listObjects(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public ObjectListing listObjects(String s, String s1) throws OSSException, ClientException {
		return null;
	}

	@Override
	public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public PutObjectResult putObject(String s, String s1, InputStream inputStream, ObjectMetadata objectMetadata) throws OSSException, ClientException {
		return null;
	}

	@Override
	public PutObjectResult putObject(URL url, String s, Map<String, String> map) throws OSSException, ClientException {
		return null;
	}

	@Override
	public PutObjectResult putObject(URL url, String s, Map<String, String> map, boolean b) throws OSSException, ClientException {
		return null;
	}

	@Override
	public PutObjectResult putObject(URL url, InputStream inputStream, long l, Map<String, String> map) throws OSSException, ClientException {
		return null;
	}

	@Override
	public PutObjectResult putObject(URL url, InputStream inputStream, long l, Map<String, String> map, boolean b) throws OSSException, ClientException {
		return null;
	}

	@Override
	public CopyObjectResult copyObject(String s, String s1, String s2, String s3) throws OSSException, ClientException {
		return null;
	}

	@Override
	public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public OSSObject getObject(String s, String s1) throws OSSException, ClientException {
		return null;
	}

	@Override
	public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File file) throws OSSException, ClientException {
		return null;
	}

	@Override
	public OSSObject getObject(GetObjectRequest getObjectRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public OSSObject getObject(URL url, Map<String, String> map) throws OSSException, ClientException {
		return null;
	}

	@Override
	public ObjectMetadata getObjectMetadata(String s, String s1) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void deleteObject(String s, String s1) throws OSSException, ClientException {

	}

	@Override
	public URL generatePresignedUrl(String s, String s1, Date date) throws ClientException {
		return null;
	}

	@Override
	public URL generatePresignedUrl(String s, String s1, Date date, HttpMethod httpMethod) throws ClientException {
		return null;
	}

	@Override
	public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws ClientException {
		return null;
	}

	@Override
	public void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws OSSException, ClientException {

	}

	@Override
	public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public PartListing listParts(ListPartsRequest listPartsRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public UploadPartCopyResult uploadPartCopy(UploadPartCopyRequest uploadPartCopyRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void setBucketCORS(SetBucketCORSRequest setBucketCORSRequest) throws OSSException, ClientException {

	}

	@Override
	public List<SetBucketCORSRequest.CORSRule> getBucketCORSRules(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void deleteBucketCORSRules(String s) throws OSSException, ClientException {

	}

	@Override
	public ResponseMessage optionsObject(OptionsRequest optionsRequest) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void setBucketLogging(SetBucketLoggingRequest setBucketLoggingRequest) throws OSSException, ClientException {

	}

	@Override
	public BucketLoggingResult getBucketLogging(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void deleteBucketLogging(String s) throws OSSException, ClientException {

	}

	@Override
	public void setBucketWebsite(SetBucketWebsiteRequest setBucketWebsiteRequest) throws OSSException, ClientException {

	}

	@Override
	public BucketWebsiteResult getBucketWebsite(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void deleteBucketWebsite(String s) throws OSSException, ClientException {

	}

	@Override
	public String generatePostPolicy(Date date, PolicyConditions policyConditions) {
		return null;
	}

	@Override
	public String calculatePostSignature(String s) {
		return null;
	}

	@Override
	public void setBucketLifecycle(SetBucketLifecycleRequest setBucketLifecycleRequest) throws OSSException, ClientException {

	}

	@Override
	public List<LifecycleRule> getBucketLifecycle(String s) throws OSSException, ClientException {
		return null;
	}

	@Override
	public void deleteBucketLifecycle(String s) throws OSSException, ClientException {

	}
}
