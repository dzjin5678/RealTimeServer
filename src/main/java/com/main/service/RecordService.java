package com.main.service;

import java.util.List;

import com.main.domain.Record;

public interface RecordService {
	
	/**
	 * 更新生产记录的开始情况
	 * @param contract_id
	 * @param station
	 * @param worker
	 * @param begin_datetime
	 * @param state
	 * @return
	 */
	public int updateRecordBegin(String contract_id,String station,String worker,String begin_datetime,String state);
	
	/**
	 * 更新生产记录的完成情况
	 * @param worker
	 * @param station
	 * @param contract_id
	 * @param end_datetime
	 * @param state
	 * @param problem
	 * @return
	 */
	public int updateRecordEnd(String worker,String station,String contract_id,String end_datetime,String state,String problem);
	
	/**
	 * 查询工人的生产记录
	 * @param worker
	 * @return
	 */
	public List<Record> getRecordList(String worker);
	
	/**
	 * 查询工人单月生产记录
	 * @param worker
	 * @return
	 */
	public List<Record> getOneMonthRecordList(String worker,String year,String month);

}
