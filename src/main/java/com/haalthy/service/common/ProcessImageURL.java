package com.haalthy.service.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessImageURL {
	public String processImageURL(String imageURL){
		String[] imageLists = imageURL.split(",");
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (String imageStr : imageLists){
			String[] imageAndIndex = imageStr.split(";");
			if ((imageAndIndex.length > 1) && (imageAndIndex[1] != "") && (imageAndIndex[1].length() == 1)) {
				map.put(Integer.valueOf(imageAndIndex[1]), imageAndIndex[0]);
			}
		}
		
		if (map.size() == 0){
			return imageURL;
		}else{
			String newImageStr = "";
			map = sortMapByKey(map);
			for (String value : map.values()) {  
				  
				newImageStr += value + ";";  
			  
			}  
			return newImageStr;
		}
		
	}
	
	public Map<Integer, String> sortMapByKey(Map<Integer, String> oriMap) {  
	    if (oriMap == null || oriMap.isEmpty()) {  
	        return null;  
	    }  
	    Map<Integer, String> sortedMap = new TreeMap<Integer, String>(new Comparator<Integer>() {  
	        public int compare(Integer key1, Integer key2) {  
	            int intKey1 = 0, intKey2 = 0;  
	            try {  
	                intKey1 = key1.intValue();  
	                intKey2 = key2.intValue();  
	            } catch (Exception e) {  
	                intKey1 = 0;   
	                intKey2 = 0;  
	            }  
	            return intKey1 - intKey2;  
	        }});  
	    sortedMap.putAll(oriMap);  
	    return sortedMap;  
	}   
}
