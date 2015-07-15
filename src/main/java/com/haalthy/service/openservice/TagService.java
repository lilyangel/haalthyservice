package com.haalthy.service.openservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haalthy.service.domain.Tag;
import com.haalthy.service.persistence.TagMapper;

public class TagService {
	@Autowired
	private TagMapper tagMapper;
	
	public List<Tag> getTagList(){
		return tagMapper.getTagList();
	}

}
