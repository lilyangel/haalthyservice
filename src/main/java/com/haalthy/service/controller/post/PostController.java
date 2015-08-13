package com.haalthy.service.controller.post;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
	
    @RequestMapping(value = "/{postid}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Post getPostById(@PathVariable int postid) throws IOException{
    	ImageService imageService = null;
    	Post post = postService.getPostById(postid);
    	if(post.getImage()!=null){
    		post.setImage(imageService.scale(post.getImage(), 32, 32));
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
    	List<Post> posts = postService.getPostsByTags(request);
    	Iterator<Post> postItr = posts.iterator();
    	ImageService imageService = new ImageService();
    	while(postItr.hasNext()){
    		Post currentPost = postItr.next();
    		if(currentPost.getImage()!=null){
    			currentPost.setImage(imageService.scale(currentPost.getImage(), 32, 32));
    		}
    	}
    	return postService.getPostsByTags(request);
    }

}
