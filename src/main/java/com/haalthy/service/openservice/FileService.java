package com.haalthy.service.openservice;


import org.apache.log4j.Logger;

import com.haalthy.service.common.ConfigLoader;

import java.io.*;

public class FileService{
    private static Logger logger = Logger.getLogger(ConfigLoader.class);

	public int saveLogFile(String filePath, StringBuffer buffer){
        try {

            File newFile = new File(filePath);  
            if (!newFile.exists()){ 
            	newFile.createNewFile();
            }
            if (newFile.exists()) {// 创建成功，则写入文件内容  
                PrintWriter p = new PrintWriter(new FileOutputStream(newFile  
                        .getAbsolutePath(), true));  
                p.write("\n" + buffer.toString());  
                p.close();
                return 1;
            } else {  
                logger.error("create frontend crash file error");
                return -1;
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return -2;
        }  
		
	}
}
