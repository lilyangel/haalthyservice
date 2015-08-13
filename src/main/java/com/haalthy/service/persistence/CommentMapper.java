package com.haalthy.service.persistence;
import java.util.List;

import com.haalthy.service.domain.Comment;

public interface CommentMapper {
	List<Comment> getCommentsByPostId(int postID);
	
	int addComment(Comment comment);
	
	int inactiveComment(Comment comment);
}