package com.haalthy.service.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>简单的阿里云OSS服务实现
 * <p>提供简单便捷的服务
 * @author john
 * @version 1.0  2015-5-22 12:36
 */
@Service
public class AliyunServiceImpl implements AliyunService {

    @Value("${aliyun.useLocalClient}")
    private String useLocalClient;

    @Value("${aliyun.url}")
    private String url;

    @Value("${aliyun.localURL}")
    private String localURL;

    @Value("${aliyun.ACCESS_ID}")
    private String ACCESS_ID;

    @Value("${aliyun.ACCESS_KEY}")
    private String ACCESS_KEY;

    private String bucket="haalthy";

    @Autowired
    private OSSDataTransfer dataTransfer;

    private OSS client;

    private static final Logger logger = LoggerFactory.getLogger(AliyunServiceImpl.class);

    @PostConstruct
    public void init() {
        // 根据aliyunConfig配置判断使用本地实现还是远程实现
        if ("true".equals(useLocalClient)) {
            client = new LocalOSSClient(dataTransfer);
        } else {
            client = new OSSClient(ACCESS_ID, ACCESS_KEY);
        }


        //client = new OSSClient(ACCESS_ID, ACCESS_KEY);

        // 创建根目录，如果不存在
        if (!client.doesBucketExist(bucket)) {
            client.createBucket(bucket);
        }
    }


    @Override
    public OSSObject uploadFile(byte[] bytes, String dir, String contentType) throws Exception {
        String uri = makeUri(dir);
        if ("image/jpeg".equals(contentType)) {
            uri += ".jpg";
        }
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        uploadFile(in, uri, "image/jpeg");
        OSSObject obj = new OSSObject();
        obj.setUri(uri);
        obj.setUrl(url + uri);
        return obj;
    }

    @Override
    public OSSObject uploadFile(MultipartFile file, String dir)
            throws Exception {
        String uri = makeUri(file, dir);
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        uploadFile(file.getInputStream(), uri, file.getContentType(), fileName);

        OSSObject obj = new OSSObject();
        obj.setUri(uri);
        obj.setRemark(fileName);
        obj.setUrl(url + uri);
        return obj;
    }

    @Override
    public List<OSSObject> uploadFile(MultipartFile[] file, String dir)
            throws Exception {
        List<OSSObject> list = new ArrayList<OSSObject>();
        for (MultipartFile up : file) {
            list.add(uploadFile(up, dir));
        }
        return list;
    }


    @Override
    public void deleteFile(String uri) {
        logger.debug("delete {} from aliyun oss", uri);
        try {
            if (StringUtils.isNotBlank(uri) && uri.contains("/")) {
                client.deleteObject(bucket, uri);
            }
        } catch(Exception e) {

        }
    }

    @Override
    public boolean exists(String uri) {
        try {
            return client.getObjectMetadata(bucket, uri) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String makeFullUrl(String uri) {
        if (uri == null) {
            return "";
        }
        if (uri.startsWith("/")) {
            uri = uri.replaceFirst("/", "");
        }
        if (uri.indexOf("http") != -1) {
            return uri;
        }
        return url + uri;
    }

    @Override
    public List<String> makeFullUrl(List<String> uri) {
        for (int i = 0; i < uri.size(); i++) {
            uri.set(i, makeFullUrl(uri.get(i)));
        }
        return uri;
    }

    @Override
    public InputStream getFile(String uri) throws IOException {
        return client.getObject(bucket, uri).getObjectContent();
    }

    @Override
    public OSSObject getObject(String uri) throws Exception {
        ObjectMetadata metadata = client.getObjectMetadata(bucket, uri);
        OSSObject obj = new OSSObject();
        obj.uri = uri;
        obj.url = makeFullUrl(obj.uri);
        obj.remark = metadata.getUserMetadata().get("remark");
        return obj;
    }

    @Override
    public List<OSSObject> listObject(String dir) throws Exception {
        List<OSSObject> list = new ArrayList<OSSObject>();

        ObjectListing lo = client.listObjects(bucket, dir);

        List<OSSObjectSummary> ls = lo.getObjectSummaries();
        for (OSSObjectSummary o : ls) {
            ObjectMetadata metadata = client.getObjectMetadata(bucket, o.getKey());
            OSSObject obj = new OSSObject();
            obj.uri = o.getKey();
            obj.url = makeFullUrl(obj.uri);
            obj.remark = metadata.getUserMetadata().get("remark");

            // 过滤掉二级目录图片
            String newPrefix = dir.replaceAll("/", "");
            int dirCount = dir.length() - newPrefix.length();

            String newUri = obj.uri.replaceAll("/", "");
            int dirCount2 = obj.uri.length() - newUri.length();

            if (dirCount == dirCount2) {
                list.add(obj);
            }

        }

        logger.debug("get file from aliyun oss with prefix {}, matches {}", new Object[]{dir, list.size()});
        return list;
    }


    @Override
    public String toFirst(String key) throws Exception {
        if (key != null) {
            int firstDir = key.indexOf("/");
            String prefix = key.substring(key.indexOf("/", firstDir + 1) + 1, key.lastIndexOf("/") + 1);
            String time = key.substring(key.lastIndexOf("/") + 1, key.indexOf("."));
            List<OSSObject> fs = listObject(prefix);
            if (fs != null && fs.size() > 0) {
                String firstUri = fs.get(0).getUri();
                String time2 = firstUri.substring(firstUri.lastIndexOf("/") + 1, firstUri.indexOf("."));
                try {
                    time2 = (Long.valueOf(time2) - 1 + "");
                    String newKey = key.replace(time, time2);
                    client.copyObject(bucket, key, bucket, newKey);
                    client.deleteObject(bucket, key);
                    return newKey;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return key;
        }
        return null;
    }

    @Override
    public CopyObjectResult copyObject(String srcKey, String desKey) {
        return client.copyObject(bucket, srcKey, bucket, desKey);
    }

    @Override
    public String getOssUrl() {
        return url;
    }

    private void uploadFile(InputStream in, String uri, String mediaType, String... remark) throws Exception {
        ObjectMetadata objectMeta = new ObjectMetadata();
        if (remark.length > 0) {
            objectMeta.getUserMetadata().put("remark", remark[0]);
        }
        objectMeta.setContentLength(in.available());
        objectMeta.setContentType(mediaType);
        client.putObject(bucket, uri, in, objectMeta);
        in.close();
    }

    private String makeUri(MultipartFile file, String dir) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        suffix = suffix != null ? suffix.toLowerCase() : "";
        return dir + "/" + System.currentTimeMillis() + suffix;
    }

    private String makeUri(String dir) {
        return dir + "/" + System.currentTimeMillis();
    }

}
