package com.haalthy.service.controller.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haalthy.service.domain.Tag;
import com.haalthy.service.domain.TagType;
import com.haalthy.service.openservice.TagService;

@Controller
@RequestMapping("/open/tag")
public class TagContoller {
	@Autowired
	private transient TagService tagService;
	
    @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public List<TagType> getTagList(){
    	List<Tag> tags = tagService.getTagList();
    	List<TagType> tagTypes = new ArrayList();
    	Iterator<Tag> tagIterator = tags.iterator();
    	List<String> tagTypeNameList = new ArrayList();
    	while(tagIterator.hasNext()){
    		Tag tag = tagIterator.next();
    		TagType tagType = new TagType();
    		tagType.setTypeName(tag.getTypeName());
    		tagType.setTypeRank(tag.getTypeRank());
//    		int tagTypeIndex = tagTypeNameList.indexOf(tag.getTypeName());
//    		System.out.println(tagTypeIndex);
//    		if(tagTypeIndex>0){
////    			tagTypes.get(tagTypeIndex).getTags().add(tag);
//    		}else{
//    			tagTypes.add(tagType);
//    			tagTypeNameList.add(tag.getTypeName());
////    			tagTypes.get(tagTypes.size()-1).getTags().add(tag);
//    		}
    		if(!tagTypeNameList.contains(tag.getTypeName())){
    			tagTypes.add(tagType);
    			tagTypeNameList.add(tag.getTypeName());
    		}
    	}
    	tagIterator = tags.iterator();
    	while(tagIterator.hasNext()){
    		Tag tag = tagIterator.next();
    		int tagTypeIndex = tagTypeNameList.indexOf(tag.getTypeName());
    		
			List<Tag> tagsInType = new ArrayList();
			if (tagTypes.get(tagTypeIndex).getTags() != null) {
				Iterator<Tag> tagsInTypeItr = tagTypes.get(tagTypeIndex).getTags().iterator();
				while (tagsInTypeItr.hasNext()) {
					tagsInType.add(tagsInTypeItr.next());
				}
			}
			tagsInType.add(tag);
    		tagTypes.get(tagTypeIndex).setTags(tagsInType);
//    		tagsInType.add(tag);
    	}
    	return tagTypes;
    }
}
