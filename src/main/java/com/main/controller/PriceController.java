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
import com.main.domain.Price;
import com.main.domain.Record;
import com.main.service.ContractService;
import com.main.service.DispatchService;
import com.main.service.PriceService;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/price")
public class PriceController {
	
	@Autowired
	PriceService priceService;
	@Autowired
	ContractService contractService;
	@Autowired
	DispatchService dispatchService;

	@RequestMapping("")
	public String price(){
		return "price.html";
	}
	
	@RequestMapping(value="/queryPriceList" , method=RequestMethod.POST)
	public void queryPriceList(HttpServletRequest  request,HttpServletResponse response){
		String contract = request.getParameter("contract");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /price/queryPrceList");
		System.out.println("contract:"+contract);
		System.out.println("**************************************************************************************");
		
		if(contract!=null){
			Contract CONTRACT=contractService.getContractByContract(contract);
			if(CONTRACT!=null){
				
				List<Price> priceList=priceService.queryPriceList(String.valueOf(CONTRACT.getId()));
				PageInfo<Price> pageInfo=new PageInfo<Price>(priceList);
				JSONObject result=new JSONObject();
				
				result.put("rows", priceList);
				result.put("total", pageInfo.getPages());
				result.put("records", pageInfo.getTotal());
				
				ServletUtil.createSuccessResponse(200, result, response);
			}
		}
	}
	
	@RequestMapping(value="/queryNotNullPriceList" , method=RequestMethod.POST)
	public void queryNotNullPriceList(HttpServletRequest  request,HttpServletResponse response){
		String contractStr = request.getParameter("contract");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /price/queryNotNullPriceList");
		System.out.println("contract:"+contractStr);
		System.out.println("**************************************************************************************");
		
		if(contractStr!=null){
			Contract contract=contractService.getContractByContract(contractStr);
			if(contract!=null){
				
				List<Price> priceList=priceService.queryNotNullPriceList(String.valueOf(contract.getId()));
				JSONObject result=new JSONObject();
				for(Price price : priceList){
					result.put(price.getStation(), price.getPrice());
				}
				ServletUtil.createSuccessResponse(200, result, response);
			}
		}
	}
	
	@RequestMapping(value="/updatePrice" , method=RequestMethod.POST)
	public void updatePrice(HttpServletRequest  request,HttpServletResponse response){
		String contract_id=request.getParameter("contract_id");
		String station=request.getParameter("station");
		String price=request.getParameter("price");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /price/updatePrice");
		System.out.println("contract_id:"+contract_id);
		System.out.println("station:"+station);
		System.out.println("price:"+price);
		System.out.println("**************************************************************************************");
		
		JSONObject result=new JSONObject();
		
		int index=priceService.updatePrice(contract_id, station, price);
		if(index>0){
			result.put("message", "工价修改成功！");
			result.put("flag", true);
		}else{
			result.put("message", "工价修改失败！");
			result.put("flag", true);
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
		
		
	}
	
	@RequestMapping(value="/queryRestPrice" , method=RequestMethod.POST)
	public void queryRestPrice(HttpServletRequest  request,HttpServletResponse response){
		String contractStr = request.getParameter("contract");
		String station = request.getParameter("opt");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /price/queryRestPrice");
		System.out.println("contract:"+contractStr);
		System.out.println("station:"+station);
		System.out.println("**************************************************************************************");
		
		double restPrice = priceService.queryPrice(contractStr, station);
		
		List<Record>records = dispatchService.queryDispatchListPerStation(contractStr, station);
		double now = 0.0;
		for(Record record : records){
			now+=record.getPrice();
		}
		
		JSONObject result = new JSONObject();
		result.put("rest", restPrice-now);
		
		ServletUtil.createSuccessResponse(200, result, response);
		
	}
	
	
	
}
