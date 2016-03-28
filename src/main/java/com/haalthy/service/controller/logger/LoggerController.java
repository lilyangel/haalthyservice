package com.haalthy.service.controller.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.ContentIntEapsulate;
import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.controller.Interface.UploadFileRequest;
import com.haalthy.service.openservice.FileService;

@Controller
@RequestMapping("/open/logger")
public class LoggerController {
	@Autowired
	private transient FileService fileService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json" })
	@ResponseBody
	public Response uploadLoggerFile(@RequestBody UploadFileRequest uploadFileRequest)  {
		Response response = new Response();
		ContentIntEapsulate result = new ContentIntEapsulate();
		try {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateStr = dateFormat.format(now);
			String filePath = "/usr/local/haalthyServiceLogger/" + uploadFileRequest.getPlatform() + "." + uploadFileRequest.getTag() + "." + dateStr
					+ ".txt";
			StringBuffer sb = new StringBuffer();
			sb.append(uploadFileRequest.getContent());
			result.setCount(fileService.saveLogFile(filePath, sb));
			response.setResult(1);
			response.setResultDesp("返回成功");
			response.setContent(result);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult(-1);
			response.setResultDesp("系统异常");
			response.setContent(result);
		}
		return response;
	}
}
