package com.haalthy.service.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * 本地OSS数据传输实现
 * @author: john
 * @version: 1.0 2015-5-22 12:36
 */
@Service
public class LocalOSSDataTransfer implements OSSDataTransfer {

    String store_path;

    public LocalOSSDataTransfer() {
        store_path = System.getProperty("user.home") + File.separator;
    }

    @Override
    public Bucket createBucket(String bucketName) throws OSSException {
        File file = new File(store_path + bucketName);
        file.mkdir();
        Bucket bucket = new Bucket(bucketName);
        bucket.setCreationDate(new Date(file.lastModified()));
        return bucket;
    }

    @Override
    public void deleteBucket(String bucketName) throws OSSException, ClientException {
        try {
            FileUtils.deleteDirectory(new File(store_path + bucketName));
        } catch (IOException e) {
            throw new OSSException("Bucket not found");
        }
    }

    @Override
    public void deleteObject(String bucketName, String key) throws OSSException, ClientException {
        FileUtils.deleteQuietly(new File(store_path + bucketName + "/" + key));
        FileUtils.deleteQuietly(new File(store_path + bucketName + "/" + key + ".metadata"));
    }

    @Override
    public boolean doesBucketExist(String bucketName) throws OSSException, ClientException {
        File dir = new File(store_path + bucketName);
        return dir.exists();
    }

    @Override
    public com.aliyun.oss.model.OSSObject getObject(String bucketName, String key) throws OSSException, ClientException {
        InputStream input = null;
        String realFile = store_path + bucketName + "/" + key;
        try {
            File file = new File(realFile);
            if (file.isFile()) {
                input = new FileInputStream(file);
                byte[] byteData = IOUtils.toByteArray(input);
                input.close();
                input = new ByteArrayInputStream(byteData);
            } else {
                input = new ByteArrayInputStream(new byte[]{});
            }

        } catch (Exception e) {
            throw new OSSException(realFile +" not found");
        }
        com.aliyun.oss.model.OSSObject object = new LocalOSSObject();
        object.setKey(key);
        object.setBucketName(bucketName);
        object.setObjectContent(input);
        object.setObjectMetadata(new ObjectMetadata());
        return object;
    }

    @Override
    public ObjectMetadata getObjectMetadata(String bucketName, String key) throws OSSException, ClientException {
        File file = new File(store_path + bucketName + "/" + key);
        if (file.isDirectory()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("dir");
            return metadata;
        }
        File dataFile = new File(store_path + bucketName + "/" + key + ".metadata");
        if (dataFile.exists()) {
            ObjectMetadata metadata = new ObjectMetadata();
            try {
                Properties prop = new Properties();
                FileInputStream fis = new FileInputStream(dataFile);
                prop.load(fis);
                fis.close();
                HashMap<String, String> map = new HashMap<String, String>();
                Set<Object> set = prop.keySet();
                for (Object o : set) {
                    map.put(o.toString(), prop.getProperty(o.toString()));
                }
                metadata.setUserMetadata(map);
            } catch (Exception e) {

            }
            return metadata;
        }
        throw new OSSException(dataFile.getName() + " not found");
    }

    @Override
    public ObjectListing listObjects(String bucketName) throws OSSException, ClientException {
        ObjectListing list = new ObjectListing();
        File file = new File(store_path + bucketName);
        list.setBucketName(bucketName);
        List<OSSObjectSummary> summaries = list.getObjectSummaries();
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.getName().endsWith(".metadata") && !f.getName().endsWith(".metadata") && f.isFile();
            }
        });
        for (File f : files) {
            OSSObjectSummary os = new OSSObjectSummary();
            os.setBucketName(bucketName);
            os.setKey(f.getName());
            os.setLastModified(new Date(f.lastModified()));
            os.setSize(f.length());

            summaries.add(os);
        }
        return list;
    }

    @Override
    public ObjectListing listObjects(String bucketName, String prefix) throws OSSException, ClientException {
        ObjectListing list = new ObjectListing();
        final String path = prefix.substring(0, prefix.lastIndexOf("/"));
        final String filePrefix = prefix.substring(prefix.lastIndexOf("/") + 1);
        File file = new File(store_path + bucketName + "/" + prefix.substring(0, prefix.lastIndexOf("/")));
        list.setBucketName(bucketName);
        List<OSSObjectSummary> summaries = list.getObjectSummaries();
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().startsWith(filePrefix) && !f.getName().endsWith(".metadata") && f.isFile();
            }
        });
        if (files != null) {
            for (File f : files) {
                OSSObjectSummary os = new OSSObjectSummary();
                os.setBucketName(bucketName);
                os.setKey(path + "/" + f.getName());
                os.setLastModified(new Date(f.lastModified()));
                os.setSize(f.length());

                summaries.add(os);
            }
        }
        return list;
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, byte[] data, ObjectMetadata metadata) throws OSSException, ClientException {
        PutObjectResult result = new PutObjectResult();
        File file = new File(store_path + bucketName + "/" + key);
        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            OutputStream output = new FileOutputStream(file);

            File dataFile = new File(store_path + bucketName + "/" + key + ".metadata");
            FileOutputStream dataOut = new FileOutputStream(dataFile);
            Properties prop = new Properties();
            prop.putAll(metadata.getUserMetadata());
            prop.store(dataOut, "user metadata");

            if (!dataFile.getParentFile().exists()) dataFile.getParentFile().mkdirs();
            dataFile.createNewFile();

            IOUtils.write(data, output);
            output.close();
            dataOut.close();
            result.setETag("success");
        } catch (IOException e) {
            e.printStackTrace();
            result.setETag("fails");
        }
        return result;
    }
}
