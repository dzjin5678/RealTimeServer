package com.main.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.main.domain.User;
import com.main.service.UserService;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@RequestMapping("")
	public String user(){
		return "user.html";
	}
	
	@RequestMapping(value="/url" , method=RequestMethod.POST)
	public void test(HttpServletRequest request , HttpServletResponse response){
		System.out.println("access_token"+request.getParameter("access_token"));
		System.out.println("app_id"+request.getParameter("app_id"));
	}
	
	@RequestMapping(value="/queryUserList" , method=RequestMethod.POST)
	public void queryUserList(HttpServletRequest request , HttpServletResponse response){
		System.out.println("**************************************************************************************");
		System.out.println("POST /user/queryUserList");
		System.out.println("**************************************************************************************");
		List<User> users = userService.queryUserList();
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		
		JSONObject result=new JSONObject();
		result.put("rows",users);
		result.put("total", pageInfo.getPages());
		result.put("records", pageInfo.getTotal());
		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	@RequestMapping(value="/queryUserNameList" , method=RequestMethod.POST)
	public void queryUserNameList(HttpServletRequest request , HttpServletResponse response){
		System.out.println("**************************************************************************************");
		System.out.println("POST /user/queryUserNameList");
		System.out.println("**************************************************************************************");
		
		List<User> userList=userService.queryUserList();
		JSONObject result=new JSONObject();
		for(User user : userList){
			result.put(user.getUser(), user.getUser());
			System.out.println(user().toString());
		}
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	@RequestMapping(value="/queryDispatchUserNameList" , method=RequestMethod.POST)
	public void queryDispatchUserNameList(HttpServletRequest request , HttpServletResponse response){
		System.out.println("**************************************************************************************");
		System.out.println("POST /user/queryDispatchUserNameList");
		System.out.println("**************************************************************************************");
		
		List<User> userList=userService.queryUserList();
		JSONObject result=new JSONObject();
		for(User user : userList){
			if(user.getDispatch().equals("允许")){
				result.put(user.getUser(), user.getUser());
			}
			
			System.out.println(user().toString());
		}
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	@RequestMapping(value="/addUser" , method=RequestMethod.POST)
	public void addUser(HttpServletRequest request , HttpServletResponse response){
		
		
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String mac = request.getParameter("mac");
		String receive = request.getParameter("receive");
		String open = request.getParameter("open");
		String price = request.getParameter("price");
		String dispatch = request.getParameter("dispatch");
		String worker = request.getParameter("worker");
		String admin = request.getParameter("admin");
		System.out.println("**************************************************************************************");
		System.out.println("POST /user/addUser");
		System.out.println("user:"+user);
		System.out.println("password:"+password);
		System.out.println("phone:"+phone);
		System.out.println("mac:"+mac);
		System.out.println("receive:"+receive);
		System.out.println("open:"+open);
		System.out.println("price:"+price);
		System.out.println("dispatch:"+dispatch);
		System.out.println("worker:"+worker);
		System.out.println("admin:"+admin);
		System.out.println("**************************************************************************************");
		
		JSONObject result=new JSONObject();
		
		if(userService.getUserByPhone(phone)==null && userService.getUserByUserName(user)==null){
			if(1==userService.addUser(user, password, phone, mac, receive, open, price, dispatch, worker, admin)){
				result.put("flag",true);
				result.put("message","添加用户成功！");
			}else{
				result.put("flag",false);
				result.put("message","添加用户失败！");
			}
		}else{
			result.put("flag",false);
			result.put("message","添加用户失败！");
		}

		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	@RequestMapping(value="/updateUser" , method=RequestMethod.POST)
	public void updateUser(HttpServletRequest request , HttpServletResponse response){
		
		String id = request.getParameter("id");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String mac = request.getParameter("mac");
		String receive = request.getParameter("receive");
		String open = request.getParameter("open");
		String price = request.getParameter("price");
		String dispatch = request.getParameter("dispatch");
		String worker = request.getParameter("worker");
		String admin = request.getParameter("admin");
		System.out.println("**************************************************************************************");
		System.out.println("POST /user/updateUser");
		System.out.println("id:"+id);
		System.out.println("user:"+user);
		System.out.println("password:"+password);
		System.out.println("phone:"+phone);
		System.out.println("mac:"+mac);
		System.out.println("receive:"+receive);
		System.out.println("open:"+open);
		System.out.println("price:"+price);
		System.out.println("dispatch:"+dispatch);
		System.out.println("worker:"+worker);
		System.out.println("admin:"+admin);
		System.out.println("**************************************************************************************");
		
		JSONObject result=new JSONObject();
		
		User userPhone=userService.getUserByPhone(phone);
		User userUserName=userService.getUserByUserName(user);
		
		System.out.println(userPhone);
		System.out.println(userUserName);
		
		
		if(userPhone!=null){
			if(userPhone.getId()==Integer.valueOf(id)){
				
				if(userUserName!=null){
					if(userUserName.getId()==Integer.valueOf(id)){
						if(1==userService.updateUser(id,user, password, phone, mac, receive, open, price, dispatch, worker, admin)){
							result.put("flag",true);
							result.put("message","更新用户成功！");
						}else{
							result.put("flag",false);
							result.put("message","更新用户失败！");
						}
					}else{
						result.put("flag",false);
						result.put("message","更新用户失败！");
					}
				}else{
					if(1==userService.updateUser(id,user, password, phone, mac, receive, open, price, dispatch, worker, admin)){
						result.put("flag",true);
						result.put("message","更新用户成功！");
					}else{
						result.put("flag",false);
						result.put("message","更新用户失败！");
					}
				}
				
			}else{
				result.put("flag",false);
				result.put("message","更新用户失败！");
			}

		}else{
			if(1==userService.updateUser(id,user, password, phone, mac, receive, open, price, dispatch, worker, admin)){
				result.put("flag",true);
				result.put("message","更新用户成功！");
			}else{
				result.put("flag",false);
				result.put("message","更新用户失败！");
			}
		}
		

		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	@RequestMapping(value="/deleteUsers" , method=RequestMethod.POST)
	public void deleteUsers(HttpServletRequest request , HttpServletResponse response){
		String ids = request.getParameter("ids");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /user/deleteUser");
		System.out.println("ids:"+ids);
		System.out.println("**************************************************************************************");
		
		String[] contractArray=ids.split(",");
		int index=0;
		for(String id : contractArray){
			index=userService.deleteUser(id);
		}
		
		JSONObject result=new JSONObject();
		if(index > 0){
			result.put("flag",true);
			result.put("message","删除用户成功！");
		}else{
			result.put("flag",false);
			result.put("message","删除用户失败！");
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
	}
	
	

}
