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
import com.main.domain.Urgency;
import com.main.mapper.UrgencyMapper;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/urgency")
public class UrgencyController {
	
	@Autowired
	UrgencyMapper urgencyMapper;
	
	String gongWeiList[]={"接单","拆单","报价","开料","木作","贴皮","白磨","组装","排孔","底漆","油磨","修色","擦色","描绘","面漆","包装"};
	
	
	@RequestMapping("")
	public String urgency(){
		return "urgency.html";
	}
	
	@RequestMapping(value="/getUrgencyList" , method=RequestMethod.POST)
	public void getUrgencyList(HttpServletRequest request , HttpServletResponse response){
		
		System.out.println("**************************************************************************************");
		System.out.println("POST 请求 /urgency/getUrgencyList");
		System.out.println("**************************************************************************************");
		
		List<Urgency> urgencyList = urgencyMapper.getUrgencyList();
		for(int i=0;i<urgencyList.size();i++){
			urgencyList.get(i).setStation(gongWeiList[i]);
		}
		PageInfo<Urgency> pageInfo=new PageInfo<Urgency>(urgencyList);
		JSONObject result=new JSONObject();
		result.put("rows", urgencyList);
		result.put("total", pageInfo.getPages());
		result.put("records", pageInfo.getTotal());
		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	@RequestMapping(value="/updateUrgency" , method=RequestMethod.POST)
	public void updateUrgency(HttpServletRequest request,HttpServletResponse response){
		
		String id=request.getParameter("id");
		String a1=request.getParameter("a1");
		String a2=request.getParameter("a2");
		String a3=request.getParameter("a3");
		String a4=request.getParameter("a4");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /urgency/updateUrgency");
		System.out.println("id:"+id);
		System.out.println("a1:"+a1);
		System.out.println("a2:"+a2);
		System.out.println("a3:"+a3);
		System.out.println("a4:"+a4);
		System.out.println("**************************************************************************************");
		
		JSONObject result=new JSONObject();
		
		int index=urgencyMapper.updateUrgency(id, a1, a2, a3, a4);
		if(index>0){
			result.put("message", "更新急迫度成功！");
			result.put("flag", true);
		}else{
			result.put("message", "更新急迫度失败！");
			result.put("flag", true);
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
		
		
		
	}

}
