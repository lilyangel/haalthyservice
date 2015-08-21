package com.haalthy.service.controller.post;
import com.haalthy.service.configuration.*;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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
import com.haalthy.service.controller.Interface.GetFeedsRequest;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostAndUser;
import com.haalthy.service.domain.PostTag;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.openservice.PostService;

@Controller
@RequestMapping("/security/post")
public class PostSecurityController {
	@Autowired
	private transient PostService postService;
	
//	{"body": "it's a test",
//		"closed":0,
//		"isBroadcast": 0,
//		"tags":
//		[{
//		"name": "tarceva",
//		"description": "tarceva",
//		"tagId": 3
//		},
//		{
//		"name": "易瑞沙",
//		"description": "",
//		"tagId": 4
//		},
//		{
//		"name": "腺癌",
//		"description": "",
//		"tagId": 5
//		}]
//		}
    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdatePostResponse addPost(@RequestBody AddPostRequest addPostRequest){
    	AddUpdatePostResponse addPostResponse = new AddUpdatePostResponse();
    	Post post = new Post();
    	post.setBody(addPostRequest.getBody());
    	post.setClosed(addPostRequest.getClosed());
    	PostType postType = PostType.valueOf(addPostRequest.getType());
    	post.setType(postType.getValue());
    	post.setCountBookmarks(0);
    	post.setCountComments(0);;
    	post.setCountViews(0);
    	post.setIsActive(1);
    	
        java.util.Date today = new java.util.Date();
    	Timestamp now = new java.sql.Timestamp(today.getTime());
    	post.setDateInserted(now);
    	post.setDateUpdated(now);
    	
 	   	Authentication a = SecurityContextHolder.getContext().getAuthentication();
 	   	String currentSessionUsername = ((OAuth2Authentication) a).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	
 	   	
    	post.setInsertUsername(currentSessionUsername);
    	post.setIsBroadcast(addPostRequest.getIsBroadcast());
    	
    	String tagString = null;
		if (addPostRequest.getTags() != null) {
			Iterator<Tag> tagItr = addPostRequest.getTags().iterator();
			StringBuilder stringBuilder = new StringBuilder();
			while (tagItr.hasNext()) {
				String tag = tagItr.next().getName();
				stringBuilder.append(tag);
				stringBuilder.append("**");
			}
			tagString = stringBuilder.toString();
			post.setTags(tagString);
		}
		int insertPostRow = postService.addPost(post);
		if (addPostRequest.getTags() != null) {
			List<PostTag> postTagList = new ArrayList<PostTag>();
			Iterator<Tag> tagDBItr = addPostRequest.getTags().iterator();
			while (tagDBItr.hasNext()) {
				PostTag postTag = new PostTag();
				postTag.setPostID(post.getPostID());
				postTag.setTagId(tagDBItr.next().getTagId());
				postTag.setCreateTime(now);
				postTagList.add(postTag);
			}
			postService.addPostTag(postTagList);
		}
        if(insertPostRow != 0 )
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
 	   	post.setInsertUsername(currentSessionUsername);

 	   if(postService.inactivePost(post)!=0)
    		updatePostResponse.setStatus("inactive successful!");
    	else 
    		updatePostResponse.setStatus("inactive unsuccessful");
    	return updatePostResponse;
    }
    
    @RequestMapping(value = "/feeds", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Post> getFeeds(@RequestBody GetFeedsRequest getFeedsRequest) throws IOException{
 	   	String currentSessionUsername = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	    getFeedsRequest.setUsername(currentSessionUsername);
 	    List<Post> posts = postService.getFeeds(getFeedsRequest);
    	Iterator<Post> postItr = posts.iterator();
    	ImageService imageService = new ImageService();
    	while(postItr.hasNext()){
    		Post currentPost = postItr.next();
    		if(currentPost.getImage()!=null){
    			currentPost.setImage(imageService.scale(currentPost.getImage(), 32, 32));
    		}
    	}
 	    return posts;
    }
    
    @RequestMapping(value = "/posts", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Post> getPosts(@RequestBody GetFeedsRequest getFeedsRequest){
 	   	String currentSessionUsername = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	    getFeedsRequest.setUsername(currentSessionUsername);
 	    List<Post> posts = postService.getPosts(getFeedsRequest);
//    	Iterator<Post> postItr = posts.iterator();
//    	while(postItr.hasNext()){
//    		Post post= postItr.next();
//    		post.setPatientProfile(post.getGender()+"**"+post.getAge()+"**"+post.getPathological()+"**"+post.getStage()+"**"+post.getMetastasis());
//    	}
 	    return posts;
    }
}
