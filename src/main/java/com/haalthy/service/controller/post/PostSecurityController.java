package com.haalthy.service.controller.post;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

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
import com.haalthy.service.controller.Interface.GetUnreadMentionedPostRequest;
import com.haalthy.service.domain.Comment;
import com.haalthy.service.domain.Mention;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostAndUser;
import com.haalthy.service.domain.PostTag;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.openservice.PostService;

import com.haalthy.service.configuration.*;

@Controller
@RequestMapping("/security/post")
public class PostSecurityController {
	@Autowired
	private transient PostService postService;
	
	private static final String imageLocation = "/Users/lily/haalthyServer/post/";
	
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
    public AddUpdatePostResponse addPost(@RequestBody AddPostRequest addPostRequest) throws IOException{
    	AddUpdatePostResponse addPostResponse = new AddUpdatePostResponse();
    	Post post = new Post();
    	post.setBody(addPostRequest.getBody());
    	post.setClosed(addPostRequest.getClosed());
//    	PostType postType = PostType.valueOf(addPostRequest.getType());
//    	post.setType(postType.getValue());
    	post.setType(0);
    	post.setCountBookmarks(0);
    	post.setCountComments(0);
    	post.setCountViews(0);
    	post.setIsActive(1);
    	
        java.util.Date today = new java.util.Date();
    	Timestamp now = new java.sql.Timestamp(today.getTime());
    	post.setDateInserted(now);
    	post.setDateUpdated(now);
    	
    	post.setInsertUsername(addPostRequest.getInsertUsername());
    	post.setIsBroadcast(addPostRequest.getIsBroadcast());
    	
    	if (addPostRequest.getImages() != null){
    		post.setHasImage(addPostRequest.getImages().size());
    	}else{
    		post.setHasImage(0);
    	}
    	
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
	    if (addPostRequest.getMentionUsers() != null) {
	    	List<Mention> mentionList = new ArrayList<Mention>();
	    	Iterator<String> usernameItr = addPostRequest.getMentionUsers().iterator();
	    	while (usernameItr.hasNext()){
	    		Mention mention = new Mention();
	    		mention.setIsUnRead(1);
	    		mention.setPostID(post.getPostID());
	    		String mUsername = usernameItr.next();
	    		mention.setUsername(mUsername);
	    		
	    		mentionList.add(mention);
	    	}
	    	postService.addMention(mentionList);
	    }
    	if (addPostRequest.getImages() != null){
    		Iterator<byte[]> imageItr = addPostRequest.getImages().iterator();
    		int index = 1;
    		while(imageItr.hasNext()){
    	    	ImageService imageService = new ImageService();
    			byte[] imageInByte = imageItr.next();
    			byte[] smallImageInByte = imageService.scale(imageInByte, 128, 128);
    			
    			// convert byte array back to BufferedImage
    			InputStream in = new ByteArrayInputStream(imageInByte);
    			BufferedImage bImageFromConvert = ImageIO.read(in);
    			String path = imageLocation + Integer.toString(post.getPostID()) + "."+ index +".jpg";
    			ImageIO.write(bImageFromConvert, "jpg", new File(path));
    			
    	    	in = new ByteArrayInputStream(smallImageInByte);
    	    	bImageFromConvert = ImageIO.read(in);
    			path = imageLocation + Integer.toString(post.getPostID()) + "."+ index + ".small" +".jpg";
    			ImageIO.write(bImageFromConvert, "jpg", new File(path));
    	    	
    			index++;
    			in.close();
    		}
    	}
        if(insertPostRow != 0 )
        	addPostResponse.setStatus("insert post successful");
        return addPostResponse;
    }
    //http://localhost:8080/haalthyservice/security/post/inactive/42?access_token=
    
    @RequestMapping(value = "/inactive/{postid}", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public AddUpdatePostResponse inactivePost(@RequestBody Post post){
    	AddUpdatePostResponse updatePostResponse = new AddUpdatePostResponse();

 	   if(postService.inactivePost(post)!=0)
    		updatePostResponse.setStatus("inactive successful!");
    	else 
    		updatePostResponse.setStatus("inactive unsuccessful");
    	return updatePostResponse;
    }
    
//    @RequestMapping(value = "/feeds", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
//    @ResponseBody
//    public List<Post> getFeeds(@RequestBody GetFeedsRequest getFeedsRequest) throws IOException{
// 	   	String currentSessionUsername = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getAuthorizationRequest().getAuthorizationParameters().get("username");
// 	    getFeedsRequest.setUsername(currentSessionUsername);
// 	    List<Post> posts = postService.getFeeds(getFeedsRequest);
//    	Iterator<Post> postItr = posts.iterator();
//    	ImageService imageService = new ImageService();
//    	while(postItr.hasNext()){
//    		Post currentPost = postItr.next();
//    		if(currentPost.getImage()!=null){
//    			currentPost.setImage(imageService.scale(currentPost.getImage(), 32, 32));
//    		}
//    	}
// 	    return posts;
//    }
    
    @RequestMapping(value = "/posts", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Post> getPosts(@RequestBody GetFeedsRequest getFeedsRequest) throws IOException{
// 	   	String currentSessionUsername = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getAuthorizationRequest().getAuthorizationParameters().get("username");
// 	    getFeedsRequest.setUsername(currentSessionUsername);
 	    if (getFeedsRequest.getCount() == 0){
 	    	getFeedsRequest.setCount(50);
 	    }
 	    List<Post> posts = postService.getPosts(getFeedsRequest);
    	Iterator<Post> postItr = posts.iterator();
		while (postItr.hasNext()) {
			Post post = postItr.next();
			if (post.getHasImage() != 0) {
				List<byte[]> postImageList = new ArrayList();
				int index = 1;
				while (index <= post.getHasImage()) {
					BufferedImage img = null;
					String path = imageLocation + Integer.toString(post.getPostID()) + "." + index + ".small" + ".jpg";
					File smallImageFile = new File(path);
					if (smallImageFile.exists()) {
						img = ImageIO.read(new File(path));
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(img, "jpg", baos);
						byte[] bytes = baos.toByteArray();
						postImageList.add(bytes);
					}
					index++;
				}
				post.setPostImageList(postImageList);
			}
		}
 	    return posts;
    }
    
    @RequestMapping(value = "/postcount", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public int getUpdatedPostCount(@RequestBody GetFeedsRequest getFeedsRequest){
    	return postService.getUpdatedPostCount(getFeedsRequest);
    }
    
    @RequestMapping(value = "/posts/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Post> getPostsByUsername(@PathVariable String username){
    	return postService.getPostsByUsername(username);
    }
    
    @RequestMapping(value = "/comments/{username}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Comment> getCommentsByUsername(@PathVariable String username){
    	return postService.getCommentsByUsername(username);
    }
    
    @RequestMapping(value = "/mentionedpost/unreadcount", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public int getUnreadMentionedPostCountByUsername(@RequestBody GetUnreadMentionedPostRequest getUnreadMentionedPostRequest){
		return postService.getUnreadMentionedPostCountByUsername(getUnreadMentionedPostRequest.getUsername());
	}
	
    @RequestMapping(value = "/mentionedpost/list", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public List<Post> getMentionedPostsByUsername(@RequestBody GetFeedsRequest request) throws IOException{
// 	   	String currentSessionUsername = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getAuthorizationRequest().getAuthorizationParameters().get("username");
//		request.setUsername(currentSessionUsername);
 	    List<Post> posts = postService.getMentionedPostsByUsername(request);
    	Iterator<Post> postItr = posts.iterator();
		while (postItr.hasNext()) {
			Post post = postItr.next();
			if (post.getHasImage() != 0) {
				List<byte[]> postImageList = new ArrayList();
				int index = 1;
				while (index <= post.getHasImage()) {
					BufferedImage img = null;
					String path = imageLocation + Integer.toString(post.getPostID()) + "." + index + ".small" + ".jpg";
					File smallImageFile = new File(path);
					if (smallImageFile.exists()) {
						img = ImageIO.read(new File(path));
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(img, "jpg", baos);
						byte[] bytes = baos.toByteArray();
						postImageList.add(bytes);
					}
					index++;
				}
				post.setPostImageList(postImageList);
			}
		}
 	   	return posts;
	}
    
    @RequestMapping(value = "/mentionedpost/markasread", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public int refreshUnreadMentionedPostsByUsername(@RequestBody GetUnreadMentionedPostRequest getUnreadMentionedPostRequest){
// 	   	String currentSessionUsername = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getAuthorizationRequest().getAuthorizationParameters().get("username");
 	   	return postService.markMentionedPostAsRead(getUnreadMentionedPostRequest.getUsername());
	}
}
