package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.controller.Interface.GetFeedsRequest;
import com.haalthy.service.controller.Interface.GetPostsByTagsRequest;
import com.haalthy.service.domain.Comment;
import com.haalthy.service.domain.Mention;
import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostAndUser;
import com.haalthy.service.domain.PostTag;
import org.apache.ibatis.annotations.Param;

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
	
	List<Post> getPostsByUsername(String username);
	
	List<Comment> getCommentsByUsername(String username);
	
	int addMention(List<Mention> mention);
	
	List<Post> getAllBroadcast(GetPostsByTagsRequest request);
	
	int getUpdatedPostCount(GetFeedsRequest request);
	
	int getPostsByTagsCount(GetPostsByTagsRequest request);
	
	int getAllBroadcastCount(GetPostsByTagsRequest request);
	
	int getUnreadMentionedPostCountByUsername(String username);
	
	List<Post> getMentionedPostsByUsername(GetFeedsRequest request);
	
	int markMentionedPostAsRead(String username);


	int updatePostImg(@Param(value = "postID") int postId, @Param(value = "filename") String fileName);
	int appendPostImg(@Param(value = "postID") int postId,@Param(value = "filename") String fileName);
}