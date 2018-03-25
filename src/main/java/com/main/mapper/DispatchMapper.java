package com.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.stereotype.Component;

import com.main.domain.Record;
/**
 * 派工数据操作类
 * @author dzjin
 *
 */
@Component
@Mapper
public interface DispatchMapper {

	/**
	 * 根据合同ID查询派工情况
	 * @param contract_id
	 * @return
	 */
	@Select("select * from record where contract_id=#{contract_id}")
	public List<Record> queryDispatchListByContractId(@Param("contract_id") String contract_id);
	
	@Select("select record.* from record,contract where "
			+ "contract.contract=#{contract} "
			+ "and contract.id=record.contract_id "
			+ "and record.station=#{station}")
	public List<Record> queryDispatchListPerStation(@Param("contract") String contract , @Param("station")String station);
	
	/**
	 * 根据当前工位查询
	 * @param station
	 * @return
	 */
	@Select("select * from record,contract where station=#{station} and begin_datetime is not NULL and end_datetime is NULL"
			+ " and contract.id=record.contract_id")
	public List<Record> queryCurrentRecordListByStation(@Param("station")String station);
	
	/**
	 * 根据当前
	 * @param station
	 * @return
	 */
	@Select("select * from record,contract where station=#{station} and end_datetime between #{begin_datetime} and #{end_datetime}"
			+ " and contract.id=record.contract_id and end_datetime=(select max(end_datetime) from record where contract.id=record.contract_id)")
	public List<Record> queryLastRecordListByStation(@Param("station")String station , @Param("begin_datetime")String begin_datetime,
			@Param("end_datetime")String end_datetime);
	
	/**
	 * 根据合同ID查询派工情况，完成时间不为空，并且按照完成时间降序排列
	 * @param contract_id
	 * @return
	 */
	@Select("select record.*,Contract.contract from record,Contract where Contract.id=Record.contract_id and "
			+ "Contract.contract=#{contract} and end_datetime is not NULL order by end_datetime desc;")
	public List<Record> queryDispatchListByContractOrderByEndDatetime(@Param("contract") String contract);
	
	
	
	/**
	 * 根据合同ID，工位以及工人姓名查询派工情况
	 * @param contract_id
	 * @param station
	 * @param worker
	 * @return
	 */
	@Select("select * from record where contract_id=#{contract_id} and station=#{station} and worker=#{worker}")
	public List<Record> queryDispatchByContractIdStationWorker(
			@Param("contract_id")String contract_id,
			@Param("station")String station,
			@Param("worker")String worker);
	
	/**
	 * 插入派工记录
	 * @param contract_id
	 * @param station
	 * @param worker
	 * @param superviser
	 * @param price
	 * @param state
	 * @return
	 */
	@Insert("insert into record(contract_id,station,superviser,worker,price,state) "
			+ "values(#{contract_id},#{station},#{superviser},#{worker},#{price},#{state})")
	public int insertDispatch(
			@Param("contract_id")String contract_id,
			@Param("station")String station,
			@Param("superviser")String superviser,
			@Param("worker")String worker,
			@Param("price")String price,
			@Param("state")String state);
	
	/**
	 * 根据ID更新派工记录
	 * @param id
	 * @param station
	 * @param superviser
	 * @param worker
	 * @param price
	 * @return
	 */
	@Update("update record set station=#{station},superviser=#{superviser},worker=#{worker},price=#{price} where id=#{id}")
	public int updateDispatch(
			@Param("id")String id,
			@Param("station")String station,
			@Param("superviser")String superviser,
			@Param("worker")String worker,
			@Param("price")String price);
	
	/**
	 * 点赞
	 * @param id
	 * @return
	 */
	@Update("update record set love='love' where id=#{id}")
	public int updateDispatchLove(@Param("id")String id);
	
	/**
	 * 根据ID删除派工记录
	 * @param id
	 * @return
	 */
	@Delete("delete from record where id=#{id}")
	public int deleteDispatchs(@Param("id")String id);
	
}
