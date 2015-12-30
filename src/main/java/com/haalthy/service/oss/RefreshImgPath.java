package com.haalthy.service.oss;

import com.haalthy.service.openservice.PatientService;
import com.haalthy.service.openservice.PostService;
import com.haalthy.service.openservice.UserService;

/**
 * Created by Ken on 2015-12-29.
 */
public class RefreshImgPath {
    public int refreshImg(String functionType,String modifyType,String id,String filePath)
    {
        if("user".equals(functionType.toLowerCase()))
        {
            return refreshUser(modifyType,id,filePath);
        }
        if("post".equals(functionType.toLowerCase()))
        {
            return refreshPost(modifyType,id,filePath);
        }
        if("patient".equals(functionType.toLowerCase()))
        {
            return refreshPatient(modifyType,id,filePath);
        }
        return 1;
    }

    private int refreshUser(String modifyType,String id,String filePath)
    {
        UserService userService = new UserService();
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

    private int refreshPost(String modifyType,String id,String filePath)
    {
        PostService postService = new PostService();
        if("update".equals(modifyType.toLowerCase()))
        {
            return postService.updatePostImg(id,filePath);
        }

        if("append".equals(modifyType.toLowerCase()))
        {
            return postService.appendPostImg(id,filePath);
        }
        return 1;
    }

    private int refreshPatient(String modifyType,String id,String filePath)
    {
        PatientService patientService = new PatientService();
        if("update".equals(modifyType.toLowerCase()))
        {
            return patientService.updatePatientImg(id,filePath);
        }

        if("append".equals(modifyType.toLowerCase()))
        {
            return patientService.appendPatientImg(id,filePath);
        }
        return 1;
    }
}
