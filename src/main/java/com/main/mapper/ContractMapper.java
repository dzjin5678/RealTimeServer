package com.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.main.domain.Contract;

@Component
@Mapper
public interface ContractMapper {
	
	/**
	 * 根据合同号查询工单
	 * @param contract
	 * @return
	 */
	@Select("select * from Contract where contract=#{contract}")
	public Contract getContractByContract(@Param("contract") String contract);
	
	/**
	 * 根据合同ID查询工单
	 * @param id
	 * @return
	 */
	@Select("select * from Contract where id=#{id}")
	public Contract getContractByContractId(@Param("id") String id);
	
	/**
	 * 更新工单的当前工位
	 * @param contract
	 * @param current_state
	 * @return
	 */
	@Update("update Contract set current_state=#{current_state} where contract=#{contract}")
	public int updateContractCurrentState(
			@Param("contract") String contract , 
			@Param("current_state") String current_state);
	

	/**
	 * 更新工单信息
	 * @param id
	 * @param contract
	 * @param client
	 * @param delivery_date
	 * @param tag
	 * @return
	 */
	@Update("update Contract set contract=#{contract} , client=#{client} , "
			+ "delivery_date=#{delivery_date},tag=#{tag} where id=#{id}")
	public int updateContract(
			@Param("id") String id , 
			@Param("contract") String contract, 
			@Param("client") String client, 
			@Param("delivery_date") String delivery_date ,
			@Param("tag") String tag);
	
	/**
	 * 查询所有工单
	 * @return
	 */
	@Select("select * from Contract")
	public List<Contract> queryContractList();
	
	
	/**
	 * 根据当前工位和起始时间查询工单
	 * @param current_state
	 * @param begin_datetime
	 * @param end_datetime
	 * @return
	 */
	@Select("select * from Contract where current_state=#{current_state} "
			+ "and delivery_date between #{begin_datetime} and #{end_datetime}")
	public List<Contract> queryContractListByCondition(
			@Param("current_state")String current_state,
			@Param("begin_datetime")String begin_datetime,
			@Param("end_datetime")String end_datetime);
	

	/**
	 * 插入工单
	 * @param contract
	 * @param client
	 * @param delivery_date
	 * @return
	 */
	@Insert("insert into Contract(contract,client,delivery_date,urgency,tag,current_state) "
			+ "values(#{contract},#{client},#{delivery_date},'正常','未标记','待产')")
	public int addContract(@Param("contract") String contract, 
			@Param("client") String client, 
			@Param("delivery_date") String delivery_date);
	
	/**
	 * 根据合同ID删除工单
	 * @param id
	 * @return
	 */
	@Delete("delete from Contract where id=#{id}")
	public int deleteById(@Param("id") String id);

}
