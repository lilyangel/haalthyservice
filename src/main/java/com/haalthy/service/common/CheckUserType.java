package com.haalthy.service.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUserType {
	public int checkUserType(String id) {
		String emailCheck = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern emailRegex = Pattern.compile(emailCheck);
		
        Pattern phoneRegex = Pattern.compile("1([\\d]{10})");
		
		Matcher emailMatcher = emailRegex.matcher(id);
		Matcher phoneMatcher = phoneRegex.matcher(id);
		
		if (emailMatcher.matches()) {
			return 0;

		} else if (phoneMatcher.matches()){
			return 1;
		} else 
			return -1;
	}
}
