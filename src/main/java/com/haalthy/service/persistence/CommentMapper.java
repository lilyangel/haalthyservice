package com.haalthy.service.persistence;
import java.util.List;

import com.haalthy.service.controller.Interface.IntRequest;
import com.haalthy.service.domain.Comment;

public interface CommentMapper {
	List<Comment> getCommentsByPostId(IntRequest postID);
	
	int addComment(Comment comment);
	
	int inactiveComment(Comment comment);
	
	int getUnreadCommentsCount(String username);
	
	List<Comment> getCommentsByUsername(String username);
	
	int markCommentsAsReadByUsername(String username);
}
