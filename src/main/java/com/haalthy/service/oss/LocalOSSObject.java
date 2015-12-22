package com.haalthy.service.oss;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author: john
 * @version: 1.0 2015-5-22 12:36
 */
public class LocalOSSObject extends com.aliyun.oss.model.OSSObject implements Serializable {

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public InputStream getObjectContent() {
        return new ByteArrayInputStream(data);
    }

    @Override
    public void setObjectContent(InputStream objectContent) {
        try {
            data = IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
        }
    }
}
