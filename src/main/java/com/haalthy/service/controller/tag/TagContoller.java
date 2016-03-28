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

import com.haalthy.service.controller.Interface.tag.GetTagTypesResponse;
import com.haalthy.service.controller.Interface.tag.GetTagsResponse;
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
	public GetTagTypesResponse getTagList() {
		GetTagTypesResponse getTagTypesResponse = new GetTagTypesResponse();
		try {
			List<Tag> tags = tagService.getTagList();
			List<TagType> tagTypes = new ArrayList();
			Iterator<Tag> tagIterator = tags.iterator();
			List<String> tagTypeNameList = new ArrayList();
			while (tagIterator.hasNext()) {
				Tag tag = tagIterator.next();
				TagType tagType = new TagType();
				tagType.setTypeName(tag.getTypeName());
				tagType.setTypeRank(tag.getTypeRank());
				if (!tagTypeNameList.contains(tag.getTypeName())) {
					tagTypes.add(tagType);
					tagTypeNameList.add(tag.getTypeName());
				}
			}
			tagIterator = tags.iterator();
			while (tagIterator.hasNext()) {
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
			}
			getTagTypesResponse.setContent(tagTypes);
			getTagTypesResponse.setResult(1);
			getTagTypesResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			e.printStackTrace();
			getTagTypesResponse.setResult(-1);
			getTagTypesResponse.setResultDesp("数据库连接错误");
		}
		return getTagTypesResponse;
	}
    
    @RequestMapping(value = "/toplist", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
	public GetTagsResponse getTopTagList() {
    	GetTagsResponse getTagsResponse = new GetTagsResponse();
		try {
			List<Tag> tags = tagService.getTagList();
			getTagsResponse.setContent(tags);
			getTagsResponse.setResult(1);
			getTagsResponse.setResultDesp("返回成功");
		} catch (Exception e) {
			e.printStackTrace();
			getTagsResponse.setResult(-1);
			getTagsResponse.setResultDesp("数据库连接错误");
		}
		return getTagsResponse;
    }

}
