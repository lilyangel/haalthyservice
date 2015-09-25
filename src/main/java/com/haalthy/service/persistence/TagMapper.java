package com.haalthy.service.persistence;

import java.util.List;

import com.haalthy.service.domain.Tag;


public interface TagMapper {
	List<Tag> getTagList();
}
