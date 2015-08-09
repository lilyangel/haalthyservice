package com.haalthy.service.openservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.controller.Interface.GetFeedsRequest;
import com.haalthy.service.controller.Interface.GetPostsByTagsRequest;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostTag;
import com.haalthy.service.domain.Tag;
import com.haalthy.service.persistence.PostMapper;

public class PostService {
	@Autowired
	private  PostMapper postMapper;
	
	public Post getPostById(int postID){
		return postMapper.getPostById(postID);
	}
	
	public int addPost(Post post){
		return postMapper.addPost(post);
	}
	
	public int addPostTag(List<PostTag> postTagList){
		return postMapper.addPostTag(postTagList);
	}
	
	public int inactivePost(Post post){
		return postMapper.inactivePost(post);
	}
	
//	public List<Post> getPostsByTagnames(ArrayList<Tag> tag){
//		return postMapper.getPostsByTagnames(tag);
//	}
	public List<Post> getPostsByTags(GetPostsByTagsRequest request){
		return postMapper.getPostsByTags(request);
	}
	
	public int increasePostCountComment(int postID){
		return postMapper.increasePostCountComment(postID);
	}
	
	public List<Post> getFeeds(GetFeedsRequest request){
		return postMapper.getFeeds(request);
	}
}
