package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.controller.Interface.post.GetFeedsRequest;
import com.haalthy.service.controller.Interface.post.GetPostsByTagsRequest;
import com.haalthy.service.domain.Comment;
import com.haalthy.service.domain.Mention;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostAndUser;
import com.haalthy.service.domain.PostTag;
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
	
	public List<Post> getPosts(GetFeedsRequest request){
		return postMapper.getPosts(request);
	}
	
	public 	List<Post> getPostsByUsername(String username){
		return postMapper.getPostsByUsername(username);
	}
	
	public List<Comment> getCommentsByUsername(String username){
		return postMapper.getCommentsByUsername(username);
	}
	
	public int addMention(List<Mention> mention){
		return postMapper.addMention(mention);
	}
	
	public List<Post> getAllBroadcast(GetPostsByTagsRequest request){
		return postMapper.getAllBroadcast(request);
	}
	
	public int getUpdatedPostCount(GetFeedsRequest request){
		return postMapper.getUpdatedPostCount(request);
	}
	
	public int getPostsByTagsCount(GetPostsByTagsRequest request){
		return postMapper.getPostsByTagsCount(request);
	}
	
	public 	int getAllBroadcastCount(GetPostsByTagsRequest request){
		return postMapper.getAllBroadcastCount(request);
	}
	public int getUnreadMentionedPostCountByUsername(String username){
		return postMapper.getUnreadMentionedPostCountByUsername(username);
	}
	
	public List<Post> getMentionedPostsByUsername(GetFeedsRequest request){
		return postMapper.getMentionedPostsByUsername(request);
	}
	
	public int markMentionedPostAsRead(String username){
		return postMapper.markMentionedPostAsRead(username);
	}
}
