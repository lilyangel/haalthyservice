package com.haalthy.service.oss;

/**
 * <p>OSS 鏂囦欢瀵硅薄</p>
 * <p>璇ョ被鏄疧SS SDK鑷甫鐨凮SSObject鐨勭畝鍖栫増鏈�</p>
 * @author: john
 * @version: 1.0 2015-5-22 12:36
 */
public class OSSObject {

    /**
     *  鏂囦欢uri
     */
    String uri;

    /**
     * 鏂囦欢瀹屾暣url
     */
    String url;

    /**
     * 鏂囦欢鎻忚堪
     */
    String remark;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OSSObject{" +
                "uri='" + uri + '\'' +
                ", url='" + url + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
