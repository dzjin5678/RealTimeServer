package com.main.controller;

import java.text.ParseException;
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
import com.main.domain.Station;
import com.main.service.ContractService;
import com.main.service.PriceService;
import com.main.service.StationService;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/contract")
public class ContractController {
	
	@Autowired
	ContractService contractService;
	@Autowired
	StationService stationService;
	@Autowired
	PriceService priceService;
	
	@RequestMapping("")
	public String Contract(){
		return "contract.html";
	}
	
	/**
	 * 查询所有工单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/queryContractList" , method=RequestMethod.POST)
	public void queryContractList(HttpServletRequest request,HttpServletResponse response){
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /contract/queryContractList");
		System.out.println("**************************************************************************************");
		
		List<Contract> contractList=contractService.queryContractList();
		PageInfo<Contract> pageInfo=new PageInfo<Contract>(contractList);
		
		JSONObject result=new JSONObject();
		result.put("rows", contractList);
		result.put("total", pageInfo.getPages());
		result.put("records", pageInfo.getTotal());
		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	/**
	 * 保存工单
	 * @param request
	 * @param response
	 * @throws ParseException 
	 */
	@RequestMapping(value="/saveContract" , method=RequestMethod.POST)
	public void saveContract(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		
		JSONObject result=new JSONObject();
		String contract = request.getParameter("contract");
		contract.trim();
		String client = request.getParameter("client");
		client.trim();
		String delivery_date = request.getParameter("delivery_date");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /contract/saveContract");
		System.out.println("contract:"+contract);
		System.out.println("client:"+client);
		System.out.println("delivery_date:"+delivery_date);
		System.out.println("**************************************************************************************");
		
		if(contract == null || contract.equals("")){
			result.put("message", "合同号不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(client == null || client.equals("")){
			result.put("message", "客户名不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(delivery_date == null || delivery_date.equals("")){
			result.put("message", "交货日期不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(contractService.getContractByContract(contract)!=null){
			result.put("message", "该工单号已经存在！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
				
		Contract CONTRACT=new Contract();
		CONTRACT.setContract(contract);
		CONTRACT.setClient(client);
		CONTRACT.setDelivery_date(delivery_date);
		
		try{
			int num=contractService.addContract(CONTRACT);
			if(num == 1){
				
				CONTRACT.setId(contractService.getContractByContract(contract).getId());
				List<Station> stations = stationService.queryStationList();
				for(Station station : stations){
					priceService.insertPrice(String.valueOf(CONTRACT.getId()), station.getStation(), "0");
				}
				result.put("message", "添加工单成功！");
				result.put("flag", true);
			}else{
				result.put("message", "添加工单失败！");
				result.put("flag", false);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("message", "添加工单失败！");
			result.put("flag", false);
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
		
		
	}
	
	/**
	 * 更新工单
	 * @param request
	 * @param response
	 * @throws ParseException 
	 */
	@RequestMapping(value="/updateContract" , method=RequestMethod.POST)
	public void updateContract(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		JSONObject result=new JSONObject();
		String contractStr = request.getParameter("contract");
		contractStr.trim();
		String client = request.getParameter("client");
		client.trim();
		String delivery_date = request.getParameter("delivery_date");
		String tag = request.getParameter("tag");
		String id = request.getParameter("id");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /contract/updateContract");
		System.out.println("id:"+id);
		System.out.println("contractStr:"+contractStr);
		System.out.println("client:"+client);
		System.out.println("tag:"+tag);
		System.out.println("delivery_date:"+delivery_date);
		System.out.println("**************************************************************************************");
		
		if(contractStr == null || contractStr.equals("")){
			result.put("message", "合同号不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(client == null || client.equals("")){
			result.put("message", "客户名不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(delivery_date == null || delivery_date.equals("")){
			result.put("message", "交货日期不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
		if(tag == null || tag.equals("")){
			result.put("message", "标记不能为空！");
			result.put("flag", false);
			ServletUtil.createSuccessResponse(200, result, response);
			return;
		}
				
		Contract contract=new Contract();
		contract.setContract(contractStr);
		contract.setClient(client);
		contract.setDelivery_date(delivery_date);
		contract.setTag(tag);
		contract.setId(Integer.valueOf(id));
		
		try{
			int num=contractService.updateContract(contract);
			if(num == 1){
				result.put("message", "更新工单成功！");
				result.put("flag", true);
			}else{
				result.put("message", "更新工单失败！");
				result.put("flag", false);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("message", "更新工单失败！");
			result.put("flag", false);
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	/**
	 * 删除工单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteContracts" , method=RequestMethod.POST)
	public void deleteContracts(HttpServletRequest request , HttpServletResponse response){
		String contracts=request.getParameter("ids");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST 请求 /contract/deleteContracts");
		System.out.println("contracts:"+contracts);
		System.out.println("**************************************************************************************");
		
		String[] contractArray=contracts.split(",");
		int index=0;
		for(String contract : contractArray){
			index=contractService.deleteById(contract);
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/searchUrgency" , method=RequestMethod.POST)
	public void searchUrgency(HttpServletRequest request ,HttpServletResponse response){
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /contract/searchUrgency");
		System.out.println("**************************************************************************************");
		JSONObject result=new JSONObject();
		
		result.put("message1", "加急");
		result.put("message2", "正常");
		result.put("message3", "正常");
		result.put("message4", "加急");
		result.put("message5", "正常");
		result.put("message6", "正常");
		result.put("message7", "加急");
		result.put("message8", "正常");
		result.put("message9", "正常");
		result.put("message10", "紧急");
		result.put("message11", "正常");
		result.put("message12", "正常");
		result.put("message13", "加急");
		result.put("message14", "正常");
		result.put("message15", "加急");
		result.put("message16", "正常");
		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}

}
