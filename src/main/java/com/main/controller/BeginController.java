package com.main.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.domain.Contract;
import com.main.domain.Record;
import com.main.service.ContractService;
import com.main.service.DispatchService;
import com.main.service.RecordService;

@Controller
@RequestMapping("/begin")
public class BeginController {
	
	@Autowired
	ContractService contractService;
	@Autowired
	DispatchService dispatchService;
	@Autowired
	RecordService recordService;
	
	/**
	 * 检查工单号是否存在,派工是否匹配，若成匹配成功，则更新工单的当前状态，插入生产记录表中
	 * @param request
	 * @param response
	 * @throws ParseException 
	 */
	@RequestMapping(value="/check" , method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> check(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		Map<String, String> result=new HashMap<String,String>();
		
		String contractStr=request.getParameter("contract");
		String station=request.getParameter("station");
		//工序开始时间
		String dateTime=request.getParameter("dateTime");
		String worker=request.getParameter("worker");
		
		System.out.println("**************************************************************************************");
		System.out.println("GET 请求 /begin/check");
		System.out.println("contract:"+contractStr);
		System.out.println("station:"+station);
		System.out.println("dateTime:"+dateTime);
		System.out.println("worker:"+worker);
		System.out.println("**************************************************************************************");
		
		Contract contract = contractService.getContractByContract(contractStr);
		//未找到合同号对应的工单
		if(contract == null){
			result.put("result", "0");
		}else if(!contract.getCurrent_state().equals("待产") && !contract.getCurrent_state().equals(station)){
			//工单当前工位不为空,也就是正在进行别的操作
			result.put("result", "2");
		}else{//查询派工表
			
			List<Record> records=dispatchService.queryDispatchByContractIdStationWorker(String.valueOf(contract.getId()),station, worker);
			if(records.size()==1){
				//更新工单当前状态
				contractService.updateContractCurrentState(contractStr, station);
				//插入生产记录
				recordService.updateRecordBegin(String.valueOf(contract.getId()), station, worker, dateTime, "在产");
				
				result.put("result", "1");
			}else{
				result.put("result", "0");
			}
		}
		
		return result;
	}
	

}
