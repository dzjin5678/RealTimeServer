package com.main.service;

import java.util.List;

import com.main.domain.Station;

public interface StationService {
	
	/**
	 * 插入工序
	 * @param id
	 * @param station
	 * @return
	 */
	public int insertStation(String id,String station);
	
	/**
	 * 根据工序名称查询工序
	 * @param station
	 * @return
	 */
	public List<Station> getStationByStation(String station);
	
	/**
	 * 查询工序
	 * @return
	 */
	public List<Station> queryStationList();
	
	/**
	 * 获取工序总数
	 * @return
	 */
	public int getStationNumber();
	
	/**
	 * 删除工序
	 * @param id
	 * @return
	 */
	public int deleteStation(String id);
	
	/**
	 * 更新工序
	 * @param id
	 * @param station
	 * @return
	 */
	public int updateStation(String id,String station);

}
