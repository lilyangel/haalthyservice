package com.haalthy.service.controller.comment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.JPush.JPushService;
import com.haalthy.service.controller.Interface.ContentIntEapsulate;
import com.haalthy.service.controller.Interface.UnreadCommentRequest;
import com.haalthy.service.controller.Interface.comment.AddCommentRequest;
import com.haalthy.service.controller.Interface.comment.AddUpdateCommentResponse;
import com.haalthy.service.controller.Interface.comment.GetCommentsResponse;
import com.haalthy.service.controller.Interface.comment.GetUnreadCommentCountResponse;
import com.haalthy.service.controller.Interface.comment.MarkCommentsAsReadByUsernameResponse;
import com.haalthy.service.domain.Comment;
import com.haalthy.service.domain.Post;
import com.haalthy.service.openservice.CommentService;
import com.haalthy.service.openservice.PostService;

@Controller
@RequestMapping("/security/comment")
public class CommentSecurityController {
	@Autowired
	private transient CommentService commentService;
	
	@Autowired
	private transient PostService postService;
	
    @Autowired
    private transient JPushService jPushService;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateCommentResponse addComment(@RequestBody AddCommentRequest addCommentRequest){
    	AddUpdateCommentResponse addCommentResponse = new AddUpdateCommentResponse();
    	Comment comment = new Comment();
    	comment.setBody(addCommentRequest.getBody());
    	Date now = new Date();
    	Timestamp ts_now = new Timestamp(now.getTime());
    	comment.setDateInserted(ts_now);
    	comment.setIsActive(1);
    	comment.setPostID(addCommentRequest.getPostID());
    	comment.setInsertUsername(addCommentRequest.getInsertUsername());
		ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
		try {
			Post post = postService.getPostById(addCommentRequest.getPostID());
			Map<String,String> extras = new HashMap();
			extras.put("type", "commented");
			extras.put("id", String.valueOf(addCommentRequest.getPostID()));
			int unreadCommentsCount = commentService.getUnreadCommentsCount(post.getInsertUsername());
			extras.put("count", String.valueOf(unreadCommentsCount));
			jPushService.SendMessageToUser(post.getInsertUsername(), addCommentRequest.getInsertUsername(), "您有一条新评论", extras);
			contentIntEapsulate.setCount(postService.increasePostCountComment(addCommentRequest.getPostID()));
			addCommentResponse.setContent(contentIntEapsulate);
			if (commentService.addComment(comment) > 0){
				addCommentResponse.setResult(1);
				addCommentResponse.setResultDesp("返回成功");
			}else{
				addCommentResponse.setResult(-2);
				addCommentResponse.setResultDesp("插入失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			contentIntEapsulate.setCount(0);
			addCommentResponse.setResult(-1);
			addCommentResponse.setResultDesp("数据库连接错误");
			addCommentResponse.setContent(contentIntEapsulate);
		}
    	return addCommentResponse;
    }
    
    @RequestMapping(value = "/inactive/{commentid}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public AddUpdateCommentResponse inactiveComment(@RequestBody Comment comment) {
		AddUpdateCommentResponse updateCommentResponse = new AddUpdateCommentResponse();
		try {
			int inactiveCommentCount = commentService.inactiveComment(comment);
			ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
			contentIntEapsulate.setCount(inactiveCommentCount);
			updateCommentResponse.setContent(contentIntEapsulate);
			if ( inactiveCommentCount != 0) {
				updateCommentResponse.setResult(1);
				updateCommentResponse.setResultDesp("返回成功");
			} else {
				updateCommentResponse.setResult(-2);
				updateCommentResponse.setResultDesp("删除失败");
			}
		} catch (Exception e) {
			updateCommentResponse.setResult(-2);
			updateCommentResponse.setResultDesp("数据库连接错误");
		}
		return updateCommentResponse;
	}
    
    @RequestMapping(value = "/unreadcommentscount", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetUnreadCommentCountResponse getUnreadCommentsCount(@RequestBody UnreadCommentRequest unreadCommentReqest){
    	GetUnreadCommentCountResponse getUnreadCommentCountResponse = new GetUnreadCommentCountResponse();
    	try{
    		ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
    		getUnreadCommentCountResponse.setResult(1);
    		getUnreadCommentCountResponse.setResultDesp("返回成功");
    		contentIntEapsulate.setCount(commentService.getUnreadCommentsCount(unreadCommentReqest.getUsername()));
    		getUnreadCommentCountResponse.setContent(contentIntEapsulate);
    	}catch(Exception e){
    		getUnreadCommentCountResponse.setResult(-1);
    		getUnreadCommentCountResponse.setResultDesp("数据库连接错误");
    	}
    	return getUnreadCommentCountResponse;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetCommentsResponse getCommentsByUsername(@RequestBody UnreadCommentRequest unreadCommentReqest){
    	GetCommentsResponse getCommentsByUsernameResponse = new GetCommentsResponse();
    	try{
    		unreadCommentReqest.setBeginIndex(unreadCommentReqest.getCount() * unreadCommentReqest.getPage());
    		getCommentsByUsernameResponse.setResult(1);
    		getCommentsByUsernameResponse.setResultDesp("返回成功");
    		getCommentsByUsernameResponse.setContent(commentService.getCommentsByUsername(unreadCommentReqest));
    	}catch(Exception e){
    		e.printStackTrace();
    		getCommentsByUsernameResponse.setResult(-1);
    		getCommentsByUsernameResponse.setResultDesp("数据库连接错误");
    	}
    	return getCommentsByUsernameResponse;
    }
    
    @RequestMapping(value = "/readallcomments", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public MarkCommentsAsReadByUsernameResponse markCommentsAsReadByUsername(@RequestBody UnreadCommentRequest unreadCommentReqest){
    	MarkCommentsAsReadByUsernameResponse markCommentsAsReadByUsernameResponse = new MarkCommentsAsReadByUsernameResponse();
    	try{
    		ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
    		contentIntEapsulate.setCount(commentService.markCommentsAsReadByUsername(unreadCommentReqest.getUsername()));
    		markCommentsAsReadByUsernameResponse.setContent(contentIntEapsulate);
    		markCommentsAsReadByUsernameResponse.setResult(1);
    		markCommentsAsReadByUsernameResponse.setResultDesp("返回成功");
    	}catch(Exception e){
    		markCommentsAsReadByUsernameResponse.setResult(-1);
    		markCommentsAsReadByUsernameResponse.setResultDesp("数据库连接错误");
    	}
    	return markCommentsAsReadByUsernameResponse;
    }
}
