package com.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.domain.User;
import com.main.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String login(){
		return "login.html";
	}
	
	/**
	 * 登录操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/toLogin" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> toLogin(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		
		String phone = request.getParameter("phone");
		phone.trim();
		String password = request.getParameter("password");
		password.trim();
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /login/toLogin");
		System.out.println("phone:"+phone);
		System.out.println("password:"+password);
		System.out.println("**************************************************************************************");
		
		Map<String, String> result=new HashMap<String,String>();
		User user=userService.getUserByPhone(phone);
		if(user!=null && user.getPassword().equals(password)){
			session.setAttribute("user", user);
			result.put("result", "1");
		}else{
			result.put("result", "0");
		}
		return result;
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value="/toLogout" , method=RequestMethod.POST)
	@ResponseBody
	public void toLogout(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		session.removeAttribute("user");
		System.out.println("POST /login/toLogout");
	}
	
	/**
	 * 安卓登录操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/toLoginAndroid" , method=RequestMethod.GET)
	@ResponseBody
	public Map<String,String> toLoginAndroid(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		
		String phone = request.getParameter("phone");
		phone.trim();
		String password = request.getParameter("password");
		password.trim();
		
		System.out.println("**************************************************************************************");
		System.out.println("GET /login/toLoginAndroid");
		System.out.println("phone:"+phone);
		System.out.println("password:"+password);
		System.out.println("**************************************************************************************");
		
		Map<String, String> result=new HashMap<String,String>();
		User user=userService.getUserByPhone(phone);
		if(user!=null && user.getPassword().equals(password)){
			session.setAttribute("user", user);
			result.put("result", "1");
			result.put("worker", user.getUser());
		}else{
			result.put("result", "0");
		}
		return result;
	}

}
