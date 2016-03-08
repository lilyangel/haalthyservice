package com.haalthy.service.openservice;

import com.haalthy.service.persistence.EmailLoggerMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ken on 2016-03-07.
 */

@Service
public class EmailLoggerService {

    protected Logger logger=Logger.getLogger(this.getClass());

    @Autowired
    EmailLoggerMapper emailLoggerMapper;

    public String insertNewEmail(String email) throws Exception
    {
        String strResult = null;
        try {
            strResult = emailLoggerMapper.insertNewEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            strResult = "-1";
        }
        return strResult;
    }
}
