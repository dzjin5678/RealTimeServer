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
@RequestMapping("/ok")
public class OKController {
	
	@Autowired
	ContractService contractService;
	@Autowired
	RecordService recordService;
	@Autowired
	DispatchService dispatchService;
	
	@RequestMapping(value="/check")
	@ResponseBody
	public Map<String, String> check(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> result = new HashMap<String,String>();
		String contractStr = request.getParameter("contract");
		String worker = request.getParameter("worker");
		
		System.out.println("**************************************************************************************");
		System.out.println("GET /ok/check");
		System.out.println("contract:"+contractStr);
		System.out.println("worker:"+worker);
		System.out.println("**************************************************************************************");
		
		Contract contract=contractService.getContractByContract(contractStr);
		
		if(contract == null){
			result.put("result", "0");
		}else{
			if(contract.getCurrent_state().equals("待产")){
				result.put("result", "2");
			}else{
				//匹配
				List<Record> records=dispatchService.queryDispatchByContractIdStationWorker(
						String.valueOf(contract.getId()),
						contract.getCurrent_state(), 
						worker);
				if(records.size()==1){
					System.out.println(records.get(0).toString());
					result.put("station", contract.getCurrent_state());
					result.put("result", "1");
				}else{
					result.put("result", "0");
				}

			}
		}
		
		return result;
	}
	
	/**
	 * 保存完成情况
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/save", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> save(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		
		Map<String, String> result=new HashMap<String,String>();
		
		String worker = request.getParameter("worker");
		String station = request.getParameter("station");
		String contractStr = request.getParameter("contract");
		String end_datetime = request.getParameter("end_datetime");
		String state = request.getParameter("state");
		String problem = request.getParameter("problem");
		
		System.out.println("**************************************************************************************");
		System.out.println("GET /ok/save");
		System.out.println("contract:"+contractStr);
		System.out.println("end_datetime:"+end_datetime);
		System.out.println("state:"+state);
		System.out.println("problem:"+problem);
		System.out.println("workerName:"+worker);
		System.out.println("station:"+station);
		System.out.println("**************************************************************************************");
		
		try{
			
			Contract contract = contractService.getContractByContract(contractStr);
			recordService.updateRecordEnd(worker, station, String.valueOf(contract.getId()), end_datetime, state, problem);
			contractService.updateContractCurrentState(contractStr, "待产");
			
			result.put("result", "3");
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", "5");
		}
		
		return result;
	}
	
	@RequestMapping(value="/love", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> love(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		
		String contract = request.getParameter("contract");
		System.out.println("**************************************************************************************");
		System.out.println("GET /ok/love");
		System.out.println("contract:"+contract);
		System.out.println("**************************************************************************************");
		Map<String, Object> result=new HashMap<String,Object>();
		
		List<Record> records = dispatchService.queryDispatchListByContractOrderByEndDatetime(contract);
		
		if(records.size()>1){
			Record record = records.get(1);
			dispatchService.updateDispatchLove(String.valueOf(record.getId()));
			result.put("result", "6");
		}else{
			//没有前序工序
			result.put("result", "7");
		}
		
		
		
		return result;
	}

}
