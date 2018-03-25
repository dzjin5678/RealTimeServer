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
import com.main.domain.Station;
import com.main.service.StationService;
import com.main.tools.ServletUtil;

@Controller
@RequestMapping("/station")
public class StationController {
	
	@Autowired
	StationService stationService;
	
	@RequestMapping("")
	public String station(){
		return "station.html";
	}
	
	/**
	 * 查询工序
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/queryStationList" , method=RequestMethod.POST)
	public void queryStationList(HttpServletRequest request , HttpServletResponse response){
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /station/queryStationList");
		System.out.println("**************************************************************************************");
		
		List<Station> stationList=stationService.queryStationList();
		PageInfo<Station> pageInfo = new PageInfo<Station>(stationList);
		
		JSONObject result=new JSONObject();
		result.put("rows", stationList);
		result.put("total", pageInfo.getPages());
		result.put("records", pageInfo.getTotal());
		
		ServletUtil.createSuccessResponse(200, result, response);
	}
	
	/**
	 * 插入工序
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertStation" , method=RequestMethod.POST)
	public void insertStation(HttpServletRequest request , HttpServletResponse response){
		String station = request.getParameter("station");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /station/insertStation");
		System.out.println("station :"+station);
		System.out.println("**************************************************************************************");
		
		JSONObject result=new JSONObject();
		if(station!=null){
			List<Station> stations = stationService.getStationByStation(station);
			if(stations.size()>0){
				//返回工序已经存在信息
				result.put("message", station+"工序已经存在，请勿重复插入！");
				result.put("flag", false);
			}else{
				//插入工序并返回插入成功信息
				int total=stationService.getStationNumber();
				int index=stationService.insertStation(String.valueOf(total+1),station);
				if(index==1){
					result.put("flag", true);
					result.put("message", station+"工序已经成功插入！");
				}else{
					result.put("flag", false);
					result.put("message", station+"工序插入失败！");
				}

			}
		}else{
			result.put("message", "工序名称为空！");
		}
		
		ServletUtil.createSuccessResponse(200, result, response);
	}
	
	/**
	 * 删除工序
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteStations" , method=RequestMethod.POST)
	public void deleteStations(HttpServletRequest request , HttpServletResponse response){
		String ids = request.getParameter("ids");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /station/deleteStations");
		System.out.println("ids:"+ids);
		System.out.println("**************************************************************************************");
		
		String stationArray[]=ids.split(",");
		for(String id:stationArray){
			stationService.deleteStation(id);
		}
		
		JSONObject result = new JSONObject();
		result.put("flag", true);
		result.put("message", ids+"删除成功！");
		
		ServletUtil.createSuccessResponse(200, result, response);

	}
	
	/**
	 * 更新工序
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateStation" , method=RequestMethod.POST)
	public void updateStation(HttpServletRequest request , HttpServletResponse response){
		String id = request.getParameter("id");
		String station = request.getParameter("station");
		
		System.out.println("**************************************************************************************");
		System.out.println("POST /station/updateStation");
		System.out.println("id:"+id);
		System.out.println("station:"+station);
		System.out.println("**************************************************************************************");
		
		JSONObject result = new JSONObject();

		int index=0;
		try{
			index=stationService.updateStation(id,station);
			if(index>0){
				result.put("flag", true);
				result.put("message", "更新成功！");
			}else{
				result.put("flag", false);
				result.put("message", "更新失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("flag", false);
			result.put("message", "更新失败！");
		}
		
		ServletUtil.createSuccessResponse(200, result, response);

	}
	
}
