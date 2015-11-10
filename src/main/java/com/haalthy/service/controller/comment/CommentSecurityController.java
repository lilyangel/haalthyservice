package com.haalthy.service.controller.comment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import com.haalthy.service.openservice.PostService;

@Controller
@RequestMapping("/security/comment")
public class CommentSecurityController {
	@Autowired
	private transient CommentService commentService;
	
	@Autowired
	private transient PostService postService;
		
    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdateCommentResponse addComment(@RequestBody AddCommentRequest addCommentRequest){
    	AddUpdateCommentResponse addCommentResponse = new AddUpdateCommentResponse();
    	Comment comment = new Comment();
    	comment.setBody(addCommentRequest.getBody());
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
    	comment.setInsertUsername(currentSessionUsername);
    	
    	Date now = new Date();
//    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	String currentDt = sdf.format(now);
    	Timestamp ts_now = new Timestamp(now.getTime());
    	comment.setDateInserted(ts_now);
    	comment.setIsActive(1);
    	comment.setPostID(addCommentRequest.getPostID());
    	postService.increasePostCountComment(addCommentRequest.getPostID());
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
 	   	comment.setInsertUsername(currentSessionUsername);
    	if(commentService.inactiveComment(comment)!=0)
    		updateCommentResponse.setStatus("inactive successful!");
    	else 
    		updateCommentResponse.setStatus("inactive unsuccessful");
    	return updateCommentResponse;
    }
    
    @RequestMapping(value = "/unreadcommentscount", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int getUnreadCommentsCount(){
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
    	return commentService.getUnreadCommentsCount(currentSessionUsername);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Comment> getCommentsByUsername(){
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
    	return commentService.getCommentsByUsername(currentSessionUsername);
    }
    
    @RequestMapping(value = "/readallcomments", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int markCommentsAsReadByUsername(){
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
    	return commentService.markCommentsAsReadByUsername(currentSessionUsername);
    }
}
