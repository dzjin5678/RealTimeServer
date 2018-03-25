package com.main.controller;

import java.util.Iterator;
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
import com.main.service.RecordService;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/salary")
public class SalaryController {
	
	@Autowired
	RecordService recordService;
	
	@RequestMapping("")
	public String salary(){
		return "salary.html";
	}
	
	@RequestMapping(value="/queryWorkerSalaryList" , method=RequestMethod.POST)
	public void queryWorkerSalaryList(HttpServletRequest request , HttpServletResponse response){
		String workerName=request.getParameter("workerName");
		String date=request.getParameter("date");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /salary/queryWorkerSalaryList");
		System.out.println("workerName:"+workerName);
		System.out.println("date:"+date);
		System.out.println("**************************************************************************************");

		if(workerName == null || date == null){
			//请完善查询条件
		}else{
			//开始查询并返回数据
			List<Record> recordList = recordService.getRecordList(workerName);
			//特别注意不能直接使用for循环对集合进行修改，而是使用Interator，记住这个坑。
			Iterator<Record> iterator = recordList.iterator();  
		    while(iterator.hasNext()) {  
		        Record record = iterator.next();
		        //移除尚未完工的生产记录
		        if(record.getEnd_datetime()==null) {  
		            iterator.remove();  
		        }else{
		        	//移除不在当前月的生产记录
			        if(!record.getEnd_datetime().contains(date)){
			        	iterator.remove();
			       	}
		        }
		    }  
		    PageInfo<Record> pageInfo = new PageInfo<Record>(recordList);
			
			JSONObject result = new JSONObject();
			result.put("rows", recordList);
			result.put("total", pageInfo.getPages());
			result.put("records", pageInfo.getTotal());
			
			ServletUtil.createSuccessResponse(200, result, response);
		}
		
	}

}
