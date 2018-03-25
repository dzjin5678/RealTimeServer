package com.main.controller;

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
import com.main.service.RecordService;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	RecordService recordService;
	@Autowired
	ContractService contractService;
	
	/**
	 * 查询生产记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/get" , method=RequestMethod.GET)
	@ResponseBody
	public Map<String, HashMap<String, String>> search(HttpServletRequest request,HttpServletResponse response){
		
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String searchMonth=year+"-"+month;
		String worker=request.getParameter("worker");
		
		System.out.println("**************************************************************************************");
		System.out.println("GET /search/get");
		System.out.println("searchMonth:"+searchMonth);
		System.out.println("worker:"+worker);
		System.out.println("**************************************************************************************");
		
		List<Record> records=recordService.getRecordList(worker);
		Map<String , HashMap<String, String>> result=new HashMap<String, HashMap<String,String>>();

		int i=0;
		for(Record record : records){
						
			if(null!=record.getEnd_datetime() && record.getEnd_datetime().contains(searchMonth)){
				HashMap<String, String>map=new HashMap<String,String>();
				Contract contract = contractService.getContractByContractId(String.valueOf(record.getContract_id()));
				map.put("contract", contract.getContract());
				map.put("station", record.getStation());
				map.put("endTime", record.getEnd_datetime());
				map.put("love", record.getLove());
				map.put("price", String.valueOf(record.getPrice()));
				result.put(String.valueOf(i), map);
				i++;
			}
			
		}
		return result;
	}

}
