package com.haalthy.service.controller.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.haalthy.service.controller.Interface.AddPostRequest;
import com.haalthy.service.controller.Interface.AddUpdatePostResponse;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostTag;
import com.haalthy.service.openservice.PostService;

@Controller
@RequestMapping("/security/post")
public class PostSecurityController {
	@Autowired
	private transient PostService postService;
	
//	http://localhost:8080/haalthyservice/security/post/add?access_token=
//	{
//		"body": "it's a test",
//		"tags":["lung cancer", "squamous carcinoma"],
//		"closed":0,
//		"isBroadcast": 0
//		}
    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdatePostResponse addPost(@RequestBody AddPostRequest addPostRequest){
    	AddUpdatePostResponse addPostResponse = new AddUpdatePostResponse();
    	Post post = new Post();
    	post.setBody(addPostRequest.getBody());
    	post.setClosed(addPostRequest.getClosed());
    	post.setCountBookmarks(0);
    	post.setCountComments(0);;
    	post.setCountViews(0);
    	post.setIsActive(1);
    	
    	Date now = new Date();
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String currentDt = sdf.format(now);
    	post.setDateInserted(currentDt);
    	post.setDateUpdated(currentDt);
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	
 	   	
    	post.setInsertUserName(currentSessionUsername);
    	post.setIsBroadcast(addPostRequest.getIsBroadcast());
    	
    	String tagString = null;
    	Iterator<String> tagItr = addPostRequest.getTags().iterator();
    	StringBuilder stringBuilder = new StringBuilder();
        while(tagItr.hasNext()) {
            String tag = tagItr.next();
            stringBuilder.append(tag);
            stringBuilder.append("**");
         }
        tagString = stringBuilder.toString();
        
        post.setTags(tagString);
        
        int insertPostRow = postService.addPost(post);
        List<PostTag> postTagList = new ArrayList<PostTag>();
        Iterator<String> tagDBItr = addPostRequest.getTags().iterator();
        while(tagDBItr.hasNext()) {
        	PostTag postTag = new PostTag();
        	postTag.setPostID(post.getPostID());
        	postTag.setTagName(tagDBItr.next());
        	postTag.setCreateTime(currentDt);
        	postTagList.add(postTag);
        }
        int insertPostTagRow = postService.addPostTag(postTagList);
        if(insertPostRow != 0 && insertPostTagRow != 0)
        	addPostResponse.setStatus("insert post successful");
        return addPostResponse;
    }
    
    //http://localhost:8080/haalthyservice/security/post/inactive/42?access_token=
    
    @RequestMapping(value = "/inactive/{postid}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdatePostResponse inactivePost(@PathVariable int postid){
    	AddUpdatePostResponse updatePostResponse = new AddUpdatePostResponse();
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	 	   
 	   	Post post = new Post();
 	   	post.setPostID(postid);
 	   	post.setInsertUserName(currentSessionUsername);

 	   if(postService.inactivePost(post)!=0)
    		updatePostResponse.setStatus("inactive successful!");
    	else 
    		updatePostResponse.setStatus("inactive unsuccessful");
    	return updatePostResponse;
    }
}
