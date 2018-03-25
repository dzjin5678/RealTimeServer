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
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/dispatch")
public class DispatchController {
	
	@Autowired
	ContractService contractService;
	@Autowired
	DispatchService dispatchService;
	
	@RequestMapping("")
	public String dispatch(){
		return "dispatch.html";
	}
	
	@RequestMapping(value="/queryDispatchList" , method=RequestMethod.POST)
	public void queryDispatchList(HttpServletRequest request , HttpServletResponse response){
		
		String contractStr = request.getParameter("contract");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /dispatch/queryDispatchList");
		System.out.println("contractStr:"+contractStr);
		System.out.println("**************************************************************************************");
		
		JSONObject result = new JSONObject();
		if(contractStr != null){
			Contract contract = contractService.getContractByContract(contractStr);
			if(contract != null){
				List<Record> dispatchList = dispatchService.queryDispatchListByContractId(String.valueOf(contract.getId()));
				PageInfo<Record> pageInfo = new PageInfo<Record>(dispatchList);
				result.put("rows", dispatchList);
				result.put("total", pageInfo.getPages());
				result.put("records", pageInfo.getTotal());
			}
		}
		ServletUtil.createSuccessResponse(200, result, response);	
	}
	
	@RequestMapping(value="/saveDispatch" , method=RequestMethod.POST)
	public void saveDispatch(HttpServletRequest request , HttpServletResponse response){
		
		String contractStr = request.getParameter("contract");
		String station = request.getParameter("station");
		String worker = request.getParameter("worker");
		String price = request.getParameter("price");
		String superviser = request.getParameter("superviser");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /dispatch/saveDispatch");
		System.out.println("contractStr:"+contractStr);
		System.out.println("station:"+station);
		System.out.println("worker:"+worker);
		System.out.println("price:"+price);
		System.out.println("superviser:"+superviser);
		System.out.println("**************************************************************************************");
		
		JSONObject result = new JSONObject();

		if(contractStr == null || contractStr.equals("")){
			result.put("message", "合同号不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(station == null || station.equals("")){
			result.put("message", "工位不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(worker == null || worker.equals("")){
			result.put("message", "工人姓名不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(price == null || price.equals("")){
			result.put("message", "工价不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(superviser == null || superviser.equals("")){
			result.put("message", "操作主管不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}

		//判断参数是否含有空的
		Contract contract = contractService.getContractByContract(contractStr);
		if(contract != null){
			
			List<Record> records = dispatchService.queryDispatchByContractIdStationWorker(String.valueOf(contract.getId()), station, worker);
			if(records.size()==0){
				int index=dispatchService.insertDispatch(String.valueOf(contract.getId()), station, superviser, worker, price, "待产");
				if(index==1){
					result.put("flag", true);
					result.put("message", "增加派工单成功！");
				}else{
					result.put("flag", false);
					result.put("message", "增加派工单失败！");
				}
			}else{
				result.put("flag", false);
				result.put("message", "不可重复添加！");
			}
			
		}else{
			result.put("flag", false);
			result.put("message", "工单查询无效！");
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
	}
	
	@RequestMapping(value="/updateDispatch" , method=RequestMethod.POST)
	public void updateDispatch(HttpServletRequest request , HttpServletResponse response){
		
		String id = request.getParameter("id");
		String station = request.getParameter("station");
		String worker = request.getParameter("worker");
		String price = request.getParameter("price");
		String superviser = request.getParameter("superviser");
		
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /dispatch/updateDispatch");
		System.out.println("id:"+id);
		System.out.println("station:"+station);
		System.out.println("worker:"+worker);
		System.out.println("price:"+price);
		System.out.println("superviser:"+superviser);
		System.out.println("**************************************************************************************");
		
		JSONObject result = new JSONObject();

		if(station == null || station.equals("")){
			result.put("message", "工位不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(worker == null || worker.equals("")){
			result.put("message", "工人姓名不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(price == null || price.equals("")){
			result.put("message", "工价不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(superviser == null || superviser.equals("")){
			result.put("message", "操作主管不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}

		//判断参数是否含有空的
		int index=dispatchService.updateDispatch(String.valueOf(id), station, superviser, worker, price);
		if(index>0){
			result.put("flag", true);
			result.put("message", "更新派工单成功！");
		}else{
			result.put("flag", false);
			result.put("message", "更新派工单失败！");
		}

		ServletUtil.createSuccessResponse(200, result, response);
	
	}
	
	@RequestMapping(value="/deleteDispatchs" , method=RequestMethod.POST)
	public void deleteDispatchs(HttpServletRequest request , HttpServletResponse response){
		String dispatchs=request.getParameter("ids");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST 请求 /dispatch/deleteDispatchs");
		System.out.println("dispatchs:"+dispatchs);
		System.out.println("**************************************************************************************");
		
		String[] dispatchArray=dispatchs.split(",");
		int index=0;
		for(String dispatch : dispatchArray){
			index= dispatchService.deleteDispatchs(dispatch);
		}
		
		JSONObject result=new JSONObject();
		if(index>0){
			result.put("message", "删除成功!");
			result.put("flag", true);
		}else{
			result.put("message", "删除失败!");
			result.put("flag", false);
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
	}

}
