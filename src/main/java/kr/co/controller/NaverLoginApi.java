package kr.co.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.scribejava.core.builder.api.DefaultApi20;

import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

@Controller

public class NaverLoginApi extends DefaultApi20{
	
	protected NaverLoginApi(){    
		
	}
	
	private static class InstanceHolder{        
		private static final NaverLoginApi INSTANCE = new NaverLoginApi();   
	}

	public static NaverLoginApi instance(){       
		return InstanceHolder.INSTANCE;    
	}
	
	@Override    
	public String getAccessTokenEndpoint() {        
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";    
	} 
	
	@Override    
	protected String getAuthorizationBaseUrl() {        
		return "https://nid.naver.com/oauth2.0/authorize";    
		}
	
}