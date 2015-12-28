package com.haalthy.service.controller.Interface;

/**
 * Created by Ken on 2015-12-28.
 */
public class OSSFile {
    private String fileType;
    private byte[] img;
    private String functionID;
    private String[] remark;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getFunctionID() {
        return functionID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public String[] getRemark() {
        return remark;
    }

    public void setRemark(String[] remark) {
        this.remark = remark;
    }
}
