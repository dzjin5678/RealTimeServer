package com.main.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.main.domain.Record;

public interface DispatchService {

	/**
	 * 根据合同ID查询派工列表
	 * @param contract_id
	 * @return
	 */
	public List<Record> queryDispatchListByContractId(String contract_id);
	
	
	public List<Record> queryDispatchListPerStation(String contract , String station);
	
	/**
	 * 查询在产工序记录
	 * @param station
	 * @return
	 */
	public List<Record> queryCurrentRecordListByStation(String station);

	/**
	 * 查询最近完成工序的生产记录
	 * @param station
	 * @param begin_datetime
	 * @param end_datetime
	 * @return
	 */
	public List<Record> queryLastRecordListByStation(
			String station , 
			String begin_datetime,
			String end_datetime);
	
	
	/**
	 * 根据合同、完工时间降序查询
	 * @param contract_id
	 * @return
	 */
	public List<Record> queryDispatchListByContractOrderByEndDatetime(String contract);
	
	/**
	 * 根据合同ID,工位以及工人姓名查询生产记录
	 * @param contract_id
	 * @param station
	 * @param worker
	 * @return
	 */
	public List<Record> queryDispatchByContractIdStationWorker(String contract_id,String station,String worker);
	
	/**
	 * 增加派工
	 * @param contract_id
	 * @param station
	 * @param superviser
	 * @param worker
	 * @param price
	 * @param state
	 * @return
	 */
	public int insertDispatch(String contract_id,String station,String superviser,String worker,String price,String state);
	
	/**
	 * 更新派工单
	 * @param id
	 * @param station
	 * @param superviser
	 * @param worker
	 * @param price
	 * @return
	 */
	public int updateDispatch(String id,String station,String superviser,String worker,String price);
	
	/**
	 * 点赞
	 * @param id
	 * @return
	 */
	public int updateDispatchLove(String id);
	
	/**
	 * 删除派单
	 * @param id
	 * @return
	 */
	public int deleteDispatchs(String id);
}
