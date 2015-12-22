package com.haalthy.service.oss;

import com.aliyun.oss.model.CopyObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>阿里云OSS服务类
 * <p>提供对阿里云OSS上传下载服务
 * @author john
 * @version 1.0  2015-5-22 12:36
 * @see AliyunServiceImpl
 */
public interface AliyunService {

    /**
     * 上传文件
     * @param bytes
     * @param dir
     * @return
     * @throws Exception
     */
    OSSObject uploadFile(byte[] bytes, String dir, String contentType) throws Exception;

    /**
     * 上传文件
     * @param file
     * @param dir
     * @return
     * @throws Exception
     */
    OSSObject uploadFile(MultipartFile file, String dir) throws Exception;

    /**
     * 批量上传文件
     * @param file
     * @param dir
     * @return
     * @throws Exception
     */
	List<OSSObject> uploadFile(MultipartFile[] file, String dir) throws Exception;

	/**
	 * 删除文件
	 * @param uri
	 * @throws Exception
	 */
	void deleteFile(String uri);

	/**
	 * 判断文件是否存在
	 * @param uri
	 * @return
	 */
	boolean exists(String uri);

	/**
	 * <p>获取完整的URL
	 * <p>此方法会在uri的前面加上域名，并将其返回
	 * <p>例如：uri = aliyun/20130923/aliyun.jpg
	 * 那么最后得到的 URL = 域名 + uri
	 * <p>如，   http://pp-dev.oss.aliyuncs.com/aliyun/20130923/aliyun.jpg
	 * @param uri
	 * @return
	 */
	String makeFullUrl(String uri);

	/**
	 * <p>获取完整的URL
	 * <p>此方法会在uri的前面加上域名，并将其返回
	 * <p>例如：uri = aliyun/20130923/aliyun.jpg
	 * 那么最后得到的 URL = 域名 + uri
	 * <p>如，   http://pp-dev.oss.aliyuncs.com/aliyun/20130923/aliyun.jpg
	 * @param uri
	 * @return
	 */
	List<String> makeFullUrl(List<String> uri);

	/**
	 * 获取文件，根据给定的uri，并将其以流的方式返回
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	InputStream getFile(String uri) throws IOException;

    /**
     * 获取OSS文件对象
     * @param uri
     * @return
     * @throws Exception
     */
    OSSObject getObject(String uri) throws Exception;

    /**
     * 获取文件列表
     * @param dir
     * @return
     * @throws Exception
     */
    List<OSSObject> listObject(String dir) throws Exception;

    /**
     * 置顶
     * @param key
     * @return
     * @throws Exception
     */
    String toFirst(String key) throws Exception;

    /**
     * 拷贝文件
     * @param srcKey    源文件名
     * @param desKey    目标文件名
     * @return
     */
    CopyObjectResult copyObject(String srcKey, String desKey);

    /**
     * 获取OSS Url
     * @return
     */
    String getOssUrl();
}
