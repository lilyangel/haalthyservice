package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.controller.Interface.GetFeedsRequest;
import com.haalthy.service.controller.Interface.GetPostsByTagsRequest;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostAndUser;
import com.haalthy.service.domain.PostTag;

public interface PostMapper {
	Post getPostById(int postID);
	
	int addPost(Post post);
	
	int addPostTag(List<PostTag> postTag);
	
	int inactivePost(Post post);
	
//	List<Post> getPostsByTagnames(ArrayList<Tag> tag);
	List<Post> getPostsByTags(GetPostsByTagsRequest request);
	
	int increasePostCountComment(int postID);
	
	List<Post> getFeeds(GetFeedsRequest request);
	
	List<Post> getPosts(GetFeedsRequest request);

}
