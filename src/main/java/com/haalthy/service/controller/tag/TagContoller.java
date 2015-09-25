package com.haalthy.service.controller.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.domain.Tag;
import com.haalthy.service.openservice.TagService;

@Controller
@RequestMapping("/open/tag")
public class TagContoller {
	@Autowired
	private transient TagService tagService;
	
    @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<Tag> getTagList(){
    	
    	return tagService.getTagList();
    }
}
