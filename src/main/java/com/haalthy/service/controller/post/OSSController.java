package com.haalthy.service.controller.post;

import com.haalthy.service.controller.Interface.UploadImgRequest;
import com.haalthy.service.oss.AliyunServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by Ken on 2015-12-25.
 */
@Controller
@RequestMapping("/open/img")
public class OSSController {
    @Autowired
    AliyunServiceImpl service;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public String uploadImg(@RequestBody UploadImgRequest uploadImgRequest) throws Exception {

        service = new AliyunServiceImpl();

        return service.uploadFile(uploadImgRequest.getFileStream(),uploadImgRequest.getDir(),uploadImgRequest.getExt()).getUrl();
    }

}
