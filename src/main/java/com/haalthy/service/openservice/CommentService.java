package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.controller.Interface.IntRequest;
import com.haalthy.service.controller.Interface.UnreadCommentRequest;
import com.haalthy.service.domain.Comment;
import com.haalthy.service.persistence.CommentMapper;

public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	
	public List<Comment> getCommentsByPostId(IntRequest postID){
		return commentMapper.getCommentsByPostId(postID);
	}
	
	public int addComment(Comment comment){
		return commentMapper.addComment(comment);
	}
	
	public int inactiveComment(Comment comment){
		return commentMapper.inactiveComment(comment);
	}
	
	public int getUnreadCommentsCount(String username){
		return commentMapper.getUnreadCommentsCount(username);
	}
	
	public List<Comment> getCommentsByUsername(UnreadCommentRequest unreadCommentReqest){
		return commentMapper.getCommentsByUsername(unreadCommentReqest);
	}
	
	public int markCommentsAsReadByUsername(String username){
		return commentMapper.markCommentsAsReadByUsername(username);
	}
}
