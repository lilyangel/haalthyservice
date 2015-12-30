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
import com.haalthy.service.controller.Interface.post.AddPostRequest;
import com.haalthy.service.controller.Interface.post.AddUpdatePostResponse;
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

    @RequestMapping(value = "/{postid}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public GetPostResponse getPostById(@PathVariable int postid){
    	GetPostResponse getPostResponse = new GetPostResponse();
		try {
			ImageService imageService = new ImageService();
			Post post = postService.getPostById(postid);
			if (post.getImage() != null) {
				post.setImage(imageService.scale(post.getImage(), 32, 32));
			}
//			if (post.getHasImage() != 0) {
//				List<byte[]> postImageList = new ArrayList();
//				int index = 1;
//				while (index <= post.getHasImage()) {
//					BufferedImage img = null;
//					String path = imageLocation + Integer.toString(post.getPostID()) + "." + index + ".small" + ".jpg";
//					File smallImageFile = new File(path);
//					if (smallImageFile.exists()) {
//						img = ImageIO.read(new File(path));
//						ByteArrayOutputStream baos = new ByteArrayOutputStream();
//						ImageIO.write(img, "jpg", baos);
//						byte[] bytes = baos.toByteArray();
//						postImageList.add(bytes);
//					}
//					index++;
//				}
//				post.setPostImageList(postImageList);
//			}
			getPostResponse.setPost(post);
			getPostResponse.setResult(1);
			getPostResponse.setResultDesp("返回成功");
		} catch (Exception e) {
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
				request.setCount(50);
			}
			if ((request.getTags() == null) || (request.getTags().size() == 0)) {
				posts = postService.getAllBroadcast(request);
			} else {
				posts = postService.getPostsByTags(request);
			}
			Iterator<Post> postItr = posts.iterator();
			ImageService imageService = new ImageService();
			while (postItr.hasNext()) {
				Post post = postItr.next();
				if (post.getImage() != null) {
					post.setImage(imageService.scale(post.getImage(), 32, 32));
				}
//				if (post.getHasImage() != 0) {
//					List<byte[]> postImageList = new ArrayList();
//					int index = 1;
//					while (index <= post.getHasImage()) {
//						BufferedImage img = null;
//						String path = imageLocation + Integer.toString(post.getPostID()) + "." + index + ".small"
//								+ ".jpg";
//						File smallImageFile = new File(path);
//						if (smallImageFile.exists()) {
//							img = ImageIO.read(new File(path));
//							ByteArrayOutputStream baos = new ByteArrayOutputStream();
//							ImageIO.write(img, "jpg", baos);
//							byte[] bytes = baos.toByteArray();
//							postImageList.add(bytes);
//						}
//						index++;
//					}
//					post.setPostImageList(postImageList);
//				}
			}
			getPostsResponse.setResult(1);
			getPostsResponse.setResultDesp("返回成功");
			getPostsResponse.setPosts(posts);
		} catch (Exception e) {
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
			int postCount = 0;
			if ((request.getTags() == null) || (request.getTags().size() == 0)) {
				postCount = postService.getAllBroadcastCount(request);

			} else {
				postCount = postService.getPostsByTagsCount(request);
			}
			getPostsByTagsCountRequest.setResult(1);
			getPostsByTagsCountRequest.setResultDesp("返回成功");
			getPostsByTagsCountRequest.setPostCount(postCount);
		} catch (Exception e) {
			getPostsByTagsCountRequest.setResult(-1);
			getPostsByTagsCountRequest.setResultDesp("数据库连接错误");
		}
		return getPostsByTagsCountRequest;
	}
}
