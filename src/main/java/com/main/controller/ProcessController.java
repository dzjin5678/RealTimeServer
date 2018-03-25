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
import com.main.domain.Record;
import com.main.service.ContractService;
import com.main.service.DispatchService;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/process")
public class ProcessController {
	
	@Autowired
	ContractService contractService;
	@Autowired
	DispatchService dispatchService;
	
	@RequestMapping("")
	public String process(){
		return "process.html";
	}
	
	/**
	 * 查询所有工单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/queryRecordList" , method=RequestMethod.POST)
	public void queryRecordList(HttpServletRequest request,HttpServletResponse response){
		String current_state=request.getParameter("currentState");
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST 请求 /process/queryContractList");
		System.out.println("currentState:"+current_state);
		System.out.println("beginDate:"+beginDate);
		System.out.println("endDate:"+endDate);
		System.out.println("**************************************************************************************");
		
		//在产工位
		List<Record> records=dispatchService.queryCurrentRecordListByStation(current_state);
		
		//查询最近完工的工位
		List<Record> records2 = dispatchService.queryLastRecordListByStation(current_state, beginDate, endDate);

		records.addAll(records2);
		
		PageInfo<Record> pageInfo=new PageInfo<Record>(records);
		
		JSONObject result=new JSONObject();
		result.put("rows", records);
		result.put("total", pageInfo.getPages());
		result.put("records", pageInfo.getTotal());
		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	

}
