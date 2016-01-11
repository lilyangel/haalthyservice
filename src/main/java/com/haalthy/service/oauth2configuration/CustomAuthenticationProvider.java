package com.haalthy.service.oauth2configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.haalthy.service.openservice.UserService;
import com.haalthy.service.domain.User;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private transient UserService userService;
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		password = decodePassword(password);
		User user = userService.getUserByUsername(name);
		if (user == null) {
			System.out.println("user is null");
			user = userService.getUserByEmail(name);
			if (user == null) {
				return null;
			}else{
				System.out.println(user.getUsername());
			}
		}
//		User user = userService.getUserByEmail(name);
		if (user == null) {
			return null;
    	}
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	if(passwordEncoder.matches(password, user.getPassword()) == false){
    		return null;
    	}else{
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), password, grantedAuths);
            return auth;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    private String decodePassword(String password){
    	String[] codeUnits = password.split("a");
    	String passwordDecode = "";
    	for(int i = 0; i< codeUnits.length; i++){
    		if(!codeUnits[i].equals("")){
        		int intCode = Integer.valueOf(codeUnits[i]).intValue(); 
        		char a = (char)intCode;
        		passwordDecode += a;
        	}
    	}
    	return passwordDecode;
    }
    
}