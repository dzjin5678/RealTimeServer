package com.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.main.domain.Record;

@Component
@Mapper
public interface RecordMapper {

	/**
	 * 根据工单ID、工位以及工人姓名更新生产记录的完成情况
	 * @param worker
	 * @param station
	 * @param contract_id
	 * @param end_datetime
	 * @param state
	 * @param problem
	 * @return
	 */
	@Update("update Record set problem=#{problem}, end_datetime=#{end_datetime}, state=#{state} "
			+ "where contract_id=#{contract_id} and station=#{station} and worker=#{worker}")
	public int updateRecordEnd(@Param("worker") String worker,
			@Param("station") String station,
			@Param("contract_id") String contract_id,
			@Param("end_datetime") String end_datetime,
			@Param("state") String state,
			@Param("problem") String problem);
	
	/**
	 * 根据工单ID、工位以及工人姓名更新生产记录的开始情况
	 * @param contract_id
	 * @param station
	 * @param worker
	 * @param begin_datetime
	 * @param state
	 * @return
	 */
	@Update("update Record set begin_datetime=#{begin_datetime}, state=#{state} "
			+ "where contract_id=#{contract_id} and station=#{station} and worker=#{worker}")
	public int updateRecordBegin(
			@Param("contract_id") String contract_id,
			@Param("station") String station,
			@Param("worker") String worker,
			@Param("begin_datetime") String begin_datetime,
			@Param("state") String state);
		
	/**
	 * 根据工人姓名查询工人生产记录
	 * @param worker
	 * @return
	 */
	@Select("select * from Record,Contract where Contract.id=Record.contract_id and worker=#{worker}")
	public List<Record> getRecordList(@Param("worker") String worker);
	
	/**
	 * 查询工人单月生产记录
	 * @param worker
	 * @param year
	 * @param month
	 * @return
	 */
	@Select("select * from Record where worker=#{worker} and YEAR(end_datetime)=#{year} and MONTH(end_datetime)=#{month}")
	public List<Record> getOneMonthRecordList(
			@Param("worker") String worker,
			@Param("year") String year,
			@Param("month") String month);
	

}
