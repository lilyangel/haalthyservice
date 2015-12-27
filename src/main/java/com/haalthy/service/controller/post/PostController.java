package com.haalthy.service.controller.post;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostTag;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.openservice.PostService;
import com.haalthy.service.controller.Interface.AddPostRequest;
import com.haalthy.service.controller.Interface.AddUpdatePostResponse;
import com.haalthy.service.controller.Interface.GetPostsByTagsRequest;

@Controller
@RequestMapping("/open/post")
public class PostController {
	@Autowired
	private transient PostService postService;
	private static final String imageLocation = "/Users/lily/haalthyServer/post/";

    @RequestMapping(value = "/{postid}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Post getPostById(@PathVariable int postid) throws IOException{
    	ImageService imageService = new ImageService();
    	Post post = postService.getPostById(postid);
    	if(post.getImage()!=null){
    		post.setImage(imageService.scale(post.getImage(), 32, 32));
    	}
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
    	return post;
    }

//    [{
//		"name": "tarceva",
//		"description": "tarceva",
//		"tagId": 3
//		},
//		{
//		"name": "易瑞沙",
//		"description": "",
//		"tagId": 4
//		}]
    @RequestMapping(value = "/tags", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Post> getPostByTags(@RequestBody GetPostsByTagsRequest request) throws IOException{
    	List<Post> posts = null;
 	    if (request.getCount() == 0){
 	    	request.setCount(50);
 	    }
    	if((request.getTags() == null) || (request.getTags().size() == 0)){
    		posts = postService.getAllBroadcast(request);
    	}else{
    		posts = postService.getPostsByTags(request);
    	}
    	Iterator<Post> postItr = posts.iterator();
    	ImageService imageService = new ImageService();
    	while(postItr.hasNext()){
    		Post post = postItr.next();
//    		if (post.getType() == 1){
//    			String postTitle = "";
//    			String[] treatmentList = post.getBody().split("\\*\\*",-1);
//    			for(int i=0;i <treatmentList.length; i++){
//    				while((treatmentList[i].length()>1)&&(treatmentList[i].charAt(0) == '*')){
//    					treatmentList[i] = treatmentList[i].substring(1);
//    				}
//    				String[] treatmentNameAndInfo = treatmentList[i].split("\\*", -1);
//    				if(treatmentNameAndInfo.length>0){
//    					
//    					postTitle = postTitle.concat(treatmentNameAndInfo[0]+"*");
//    				}
//    			}
//    			post.setHighlightTitle(postTitle);
//    		}
//    		if (post.getType() == 2){
//    			String postTitle = "";
//    			String patientStatusStr = "";
//    			if (post.getBody().contains("##")){
//    				String[] patientStatusAndClinicReport = post.getBody().split("##",-1);
//    				if(patientStatusAndClinicReport.length > 1){
//    					patientStatusStr = patientStatusAndClinicReport[0];
//    				}
//    			}else{
//    				patientStatusStr = post.getBody();
//    			}
//    			String[] patientStatusArr = patientStatusStr.split("\\*\\*", -1);
//    			if(patientStatusArr.length > 0){
//    				post.setHighlightTitle(patientStatusArr[0]);
//    			}
//    		}
//    		if(post.getType() != 0){
//    			post.setBody(post.getBody().replace('*', ' '));
//    		}
    		if(post.getImage()!=null){
    			post.setImage(imageService.scale(post.getImage(), 32, 32));
    		}
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
    
    @RequestMapping(value = "/tags/count", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public int getPostsByTagsCount(@RequestBody GetPostsByTagsRequest request) throws IOException{
    	int postCount = 0;
    	if((request.getTags() == null) || (request.getTags().size() == 0)){
    		postCount = postService.getAllBroadcastCount(request);

    	}else{
    		postCount = postService.getPostsByTagsCount(request);
    	}
    	return postCount;
    }
}
