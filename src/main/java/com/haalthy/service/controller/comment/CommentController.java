package com.haalthy.service.controller.comment;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.controller.Interface.comment.AddCommentRequest;
import com.haalthy.service.controller.Interface.comment.AddUpdateCommentResponse;
import com.haalthy.service.controller.Interface.comment.GetCommentsByPostIdResponse;
import com.haalthy.service.domain.Comment;
import com.haalthy.service.openservice.CommentService;

@Controller
@RequestMapping("/open/comment")
public class CommentController {
	@Autowired
	private transient CommentService commentService;
	
    @RequestMapping(value = "/post/{postid}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetCommentsByPostIdResponse getCommentsByPostId(@PathVariable int postid){
    	GetCommentsByPostIdResponse getCommentsByPostIdResponse = new GetCommentsByPostIdResponse();
    	try{
    		getCommentsByPostIdResponse.setResult(1);
    		getCommentsByPostIdResponse.setResultDesp("返回成功");
    		getCommentsByPostIdResponse.setComments(commentService.getCommentsByPostId(postid));
    	}catch(Exception e){
    		getCommentsByPostIdResponse.setResult(-1);
    		getCommentsByPostIdResponse.setResultDesp("数据库连接错误");
    	}
    	return getCommentsByPostIdResponse;
    }
}
