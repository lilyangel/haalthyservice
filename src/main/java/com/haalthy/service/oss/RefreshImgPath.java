package com.haalthy.service.oss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.PostService;
import com.haalthy.service.openservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ken on 2015-12-29.
 */
@Service
public class RefreshImgPath {
	@Autowired
	private transient UserService userService;
	
	@Autowired
	private transient PostService postService;
	
	@Autowired
	private transient PatientService patientService;
	
    public int refreshImg(String functionType,String modifyType,String id,String filePath, int index)
    {
        if("user".equals(functionType.toLowerCase()))
        {
            return refreshUser(modifyType,id,filePath);
        }
        if("post".equals(functionType.toLowerCase()))
        {
            return refreshPost(modifyType,id,filePath, index);
        }
        if("patient".equals(functionType.toLowerCase()))
        {
            return refreshPatient(modifyType,id,filePath, index);
        }
        return 1;
    }

    private int refreshUser(String modifyType,String id,String filePath)
    {
       if("update".equals(modifyType.toLowerCase()))
        {
            return userService.updateUserPhoto(id,filePath);
        }

        if("append".equals(modifyType.toLowerCase()))
        {
            return userService.appendUserPhoto(id,filePath);
        }

        return 1;
    }

    private int refreshPost(String modifyType,String id,String filePath, int index)
    {
        if("update".equals(modifyType.toLowerCase()))
        {
        	if (index > -1){
        		filePath += String.valueOf(index) + ",";
        	}
            return postService.updatePostImg(id,filePath);
        }

        if("append".equals(modifyType.toLowerCase()))
        {
        	if (index > -1){
        		filePath += String.valueOf(index) + ",";
        	}
        	return postService.appendPostImg(id,filePath);
        }
        return 1;
    }

    private int refreshPatient(String modifyType,String id,String filePath, int index)
    {
        if("update".equals(modifyType.toLowerCase()))
        {
        	if (index > -1){
        		filePath += String.valueOf(index) + ",";
        	}
        	return patientService.updatePatientImg(id,filePath);
        }

        if("append".equals(modifyType.toLowerCase()))
        {
        	if (index > -1){
        		filePath += String.valueOf(index) + ",";
        	}
        	return patientService.appendPatientImg(id,filePath);
        }
        return 1;
    }
}
