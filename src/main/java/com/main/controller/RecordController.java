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
import com.main.domain.Contract;
import com.main.domain.Record;
import com.main.service.ContractService;
import com.main.service.DispatchService;
import com.main.service.RecordService;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/record")
public class RecordController {
	
	@Autowired
	RecordService recordService;
	@Autowired
	ContractService contractService;
	@Autowired
	DispatchService dispatchService;
	
	@RequestMapping("")
	public String record(){
		return "record.html";
	}
	
	
	/**
	 * 查询生产记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/queryRecordList" , method=RequestMethod.POST)
	public void queryRecordList(HttpServletRequest request , HttpServletResponse response){
		String contractStr = request.getParameter("contract");	
		
		System.out.println("**************************************************************************************");
		System.out.println("POST 请求/record/queryRecordList");
		System.out.println("contract:"+contractStr);
		System.out.println("**************************************************************************************");
		
		JSONObject result = new JSONObject();
		
		Contract contract = contractService.getContractByContract(contractStr);
		if(contract != null){
			List<Record> recordList = dispatchService.queryDispatchListByContractId(String.valueOf(contract.getId()));
			PageInfo<Record> pageInfo = new PageInfo<Record>(recordList);
			result.put("rows", recordList);
			result.put("total", pageInfo.getPages());
			result.put("records", pageInfo.getTotal());
		}
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	/**
	 * 更新生产记录完工标记
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateRecordEnd" , method=RequestMethod.POST)
	public void updateRecordEnd(HttpServletRequest request , HttpServletResponse response){
		JSONObject result = new JSONObject();
		String worker = request.getParameter("worker");
		String station = request.getParameter("station");
		String contract_id = request.getParameter("contract_id");
		String end_datetime = request.getParameter("end_datetime");
		String state = request.getParameter("state");
		String problem = request.getParameter("problem");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST 请求/record/updateRecordEnd");
		System.out.println("worker:"+worker);
		System.out.println("station:"+station);
		System.out.println("contract_id:"+contract_id);
		System.out.println("end_datetime:"+end_datetime);
		System.out.println("state:"+state);
		System.out.println("problem:"+problem);		
		System.out.println("**************************************************************************************");

		int index = recordService.updateRecordEnd(worker, station, contract_id, end_datetime, state, problem);
		if(index > 0){
			result.put("message", "更新完工标记成功！");
			result.put("flag", true);
		}else{
			result.put("message", "更新完工标记失败！");
			result.put("flag", true);
		}
		ServletUtil.createSuccessResponse(200, result, response);
	}

}
