package com.haalthy.service.controller.comment;

import java.util.Date;

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

import com.haalthy.service.controller.Interface.AddCommentRequest;
import com.haalthy.service.controller.Interface.AddUpdateCommentResponse;
import com.haalthy.service.domain.Comment;
import com.haalthy.service.openservice.CommentService;

@Controller
@RequestMapping("/security/comment")
public class CommentSecurityController {
	@Autowired
	private transient CommentService commentService;
		
    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateCommentResponse addComment(@RequestBody AddCommentRequest addCommentRequest){
    	AddUpdateCommentResponse addCommentResponse = new AddUpdateCommentResponse();
    	Comment comment = new Comment();
    	comment.setBody(addCommentRequest.getBody());
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
    	comment.setInsertUserName(currentSessionUsername);
    	
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	comment.setDateInserted(currentDt);
    	comment.setIsActive(1);
    	comment.setPostID(addCommentRequest.getPostID());
    	if(commentService.addComment(comment)>0)
    		addCommentResponse.setStatus("create comment sucessful!");
    	return addCommentResponse;
    }
    
    @RequestMapping(value = "/inactive/{commentid}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateCommentResponse inactiveComment(@PathVariable int commentid){
    	AddUpdateCommentResponse updateCommentResponse = new AddUpdateCommentResponse();
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	
 	   	Comment comment = new Comment();
 	   	comment.setCommentID(commentid);
 	   	comment.setInsertUserName(currentSessionUsername);
    	if(commentService.inactiveComment(comment)!=0)
    		updateCommentResponse.setStatus("inactive successful!");
    	else 
    		updateCommentResponse.setStatus("inactive unsuccessful");
    	return updateCommentResponse;
    }
}
