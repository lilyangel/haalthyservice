package com.haalthy.service.controller.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.domain.Post;
import com.haalthy.service.domain.PostTag;
import com.haalthy.service.openservice.PostService;
import com.haalthy.service.controller.Interface.AddPostRequest;
import com.haalthy.service.controller.Interface.AddUpdatePostResponse;

@Controller
@RequestMapping("/open/post")
public class PostController {
	@Autowired
	private transient PostService postService;
	
    @RequestMapping(value = "/{postid}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Post getPostById(@PathVariable int postid){
    	return postService.getPostById(postid);
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Post> getPostById(@RequestBody ArrayList<String> tagname){
    	System.out.println(tagname);
    	return postService.getPostsByTagnames(tagname);
    }
}
