package com.haalthy.service.controller.Interface;

public class GetMentionedUsernameRequest {
	private String username;
	private String mentionedDisplayname;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMentionedDisplayname() {
		return mentionedDisplayname;
	}
	public void setMentionedDisplayname(String mentionedDisplayname) {
		this.mentionedDisplayname = mentionedDisplayname;
	}
}
