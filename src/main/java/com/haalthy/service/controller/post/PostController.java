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

import com.haalthy.service.domain.PatientStatus;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostTag;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.PostService;
import com.haalthy.service.common.ProcessImageURL;
import com.haalthy.service.controller.Interface.ContentIntEapsulate;
import com.haalthy.service.controller.Interface.GetCountResponse;
import com.haalthy.service.controller.Interface.InputUsernameRequest;
import com.haalthy.service.controller.Interface.IntRequest;
import com.haalthy.service.controller.Interface.post.AddPostRequest;
import com.haalthy.service.controller.Interface.post.AddUpdatePostResponse;
import com.haalthy.service.controller.Interface.post.GetFeedsRequest;
import com.haalthy.service.controller.Interface.post.GetPostResponse;
import com.haalthy.service.controller.Interface.post.GetPostsByTagsCountResponse;
import com.haalthy.service.controller.Interface.post.GetPostsByTagsRequest;
import com.haalthy.service.controller.Interface.post.GetPostsResponse;

@Controller
@RequestMapping("/open/post")
public class PostController {
	@Autowired
	private transient PostService postService;
	private static final String imageLocation = "/Users/lily/haalthyServer/post/";
	
	@Autowired
	private transient PatientService patientService;
	
    @RequestMapping(value = "/getpost", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetPostResponse getPostById(@RequestBody IntRequest postid){
    	GetPostResponse getPostResponse = new GetPostResponse();
		try {
			ImageService imageService = new ImageService();
			Post post = postService.getPostById(postid.getId());
			if (post != null) {
				if (post.getType() == 2) {
					PatientStatus patientStatus = patientService.getPatientStatusById(post.getPatientStatusID());
					if ((patientStatus != null) && (patientStatus.getImageURL() != null)){
						post.setImageURL(patientStatus.getImageURL());
					}
				}
				if (post.getImageURL() != null){
					
					ProcessImageURL processImageURL = new ProcessImageURL();
					post.setImageURL(processImageURL.processImageURL(post.getImageURL()));
				}
				getPostResponse.setContent(post);
				getPostResponse.setResult(1);
				getPostResponse.setResultDesp("返回成功");
			} else {
				getPostResponse.setResult(-3);
				getPostResponse.setResultDesp("post不存在");
			}
		} catch(NullPointerException nullPE){
			nullPE.printStackTrace();
			getPostResponse.setResult(-2);
			getPostResponse.setResultDesp("系统异常");
		} 
		catch (Exception e) {
			e.printStackTrace();
			getPostResponse.setResult(-1);
			getPostResponse.setResultDesp("数据库连接错误");
		} 
    	return getPostResponse;
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
	public GetPostsResponse getPostByTags(@RequestBody GetPostsByTagsRequest request) throws IOException {
		GetPostsResponse getPostsResponse = new GetPostsResponse();
		try {
			List<Post> posts = null;
			if (request.getCount() == 0) {
				request.setCount(20);
			}
			request.setBeginIndex(request.getCount() * request.getPage());
			if ((request.getTags() == null) || (request.getTags().size() == 0)) {
				posts = postService.getAllBroadcast(request);
			} else {
				posts = postService.getPostsByTags(request);
			}
			for (Post post : posts){
				if (post.getType() == 2) {
					PatientStatus patientStatus = patientService.getPatientStatusById(post.getPatientStatusID());
					if ((patientStatus != null) && (patientStatus.getImageURL() != null)){
						post.setImageURL(patientStatus.getImageURL());
					}
				}
				if (post.getImageURL() != null){
					
					ProcessImageURL processImageURL = new ProcessImageURL();
					post.setImageURL(processImageURL.processImageURL(post.getImageURL()));
				}
			}
			getPostsResponse.setResult(1);
			getPostsResponse.setResultDesp("返回成功");
			getPostsResponse.setContent(posts);
		} catch (Exception e) {
			e.printStackTrace();
			getPostsResponse.setResult(-1);
			getPostsResponse.setResultDesp("数据库连接错误");
		}
//    	return posts;
		return getPostsResponse;
    }
    
	@RequestMapping(value = "/tags/count", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" })
	@ResponseBody
	public GetPostsByTagsCountResponse getPostsByTagsCount(@RequestBody GetPostsByTagsRequest request) {
		GetPostsByTagsCountResponse getPostsByTagsCountRequest = new GetPostsByTagsCountResponse();
		try {
			request.setBeginIndex(request.getCount() * request.getPage());
			int postCount = 0;
			if ((request.getTags() == null) || (request.getTags().size() == 0)) {
				postCount = postService.getAllBroadcastCount(request);
			} else {
				postCount = postService.getPostsByTagsCount(request);
			}
			getPostsByTagsCountRequest.setResult(1);
			getPostsByTagsCountRequest.setResultDesp("返回成功");
			ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
			contentIntEapsulate.setCount(postCount);
			getPostsByTagsCountRequest.setContent(contentIntEapsulate);
		} catch (Exception e) {
			e.printStackTrace();
			getPostsByTagsCountRequest.setResult(-1);
			getPostsByTagsCountRequest.setResultDesp("数据库连接错误");
		}
		return getPostsByTagsCountRequest;
	}
	
    @RequestMapping(value = "/mentionedpost/unreadcount", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public GetCountResponse getUnreadMentionedPostCountByUsername(@RequestBody InputUsernameRequest inputUsernameRequest){
    	GetCountResponse getCountResponse = new GetCountResponse();
    	try{
    		ContentIntEapsulate contentIntEapsulate = new ContentIntEapsulate();
    		contentIntEapsulate.setCount(postService.getUnreadMentionedPostCountByUsername(inputUsernameRequest.getUsername()));
    		getCountResponse.setContent(contentIntEapsulate);
    		getCountResponse.setResult(1);
    		getCountResponse.setResultDesp("返回成功");
    	}catch(Exception e){
    		e.printStackTrace();
    		getCountResponse.setResult(-1);
    		getCountResponse.setResultDesp("数据库连接错误");
    	}
		return getCountResponse;

	}
}
