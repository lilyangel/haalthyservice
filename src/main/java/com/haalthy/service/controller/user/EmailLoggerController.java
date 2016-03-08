package com.haalthy.service.controller.user;

import com.haalthy.service.controller.Interface.Response;
import com.haalthy.service.controller.Interface.StringRequest;
import com.haalthy.service.openservice.EmailLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ken on 2016-03-07.
 */
@Controller
@RequestMapping("/open/logEmail")
public class EmailLoggerController {
    @Autowired
    private transient EmailLoggerService emailLoggerService;

    @RequestMapping(value = "/log",method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public Response LogEmail(@RequestBody StringRequest request)
    {
        Response response = new Response();
        try {
            String result = emailLoggerService.insertNewEmail(request.getContent());
            if(request.equals("-1"))
            {
                response.setResult(-1);
                response.setResultDesp("该信息已存在!");
                response.setContent(null);
            }
            else
            {
                response.setResult(0);
                response.setResultDesp("已经成功记录您的信息，我们将尽快与您联系，敬请期待！");
                response.setContent(request);
            }
        } catch (Exception e) {
                response.setResult(-2);
                response.setResultDesp("其他错误");
                response.setContent(null);
        }
        return response;
    }
}
