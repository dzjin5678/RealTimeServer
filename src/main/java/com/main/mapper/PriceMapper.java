package com.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.stereotype.Component;

import com.main.domain.Price;
/**
 * 工价数据操作类
 * @author dzjin
 *
 */
@Component
@Mapper
public interface PriceMapper {
	
	/**
	 * 插入工位工价
	 * @param contract_id
	 * @param station
	 * @param price
	 * @return
	 */
	@Insert("insert into price(contract_id,station,price) values(#{contract_id},#{station},#{price})")
	public int insertPrice(
			@Param("contract_id")String contract_id, 
			@Param("station")String station,
			@Param("price")String price);
	
	/**
	 * 根据合同ID查询工价表
	 * @param contract_id
	 * @return
	 */
	@Select("select * from price,station where contract_id=#{contract_id} and "
			+ "price.station=station.station order by station.id asc")
	public List<Price> queryPriceList(@Param("contract_id") String contract_id);
	
	@Select("select price.price from price,contract where "
			+ "contract.contract=#{contract} and "
			+ "price.contract_id=contract.id and "
			+ "price.station=#{station}")
	public double queryPrice(@Param("contract") String contract , @Param("station")String station);
	
	/**
	 * 根据合同ID和特定工位更新工价
	 * @param contract_id
	 * @param station
	 * @param price
	 * @return
	 */
	@Update("update price set price=#{price} where contract_id=#{contract_id} and station=#{station}")
	public int updatePrice(
			@Param("contract_id")String contract_id, 
			@Param("station")String station,
			@Param("price")String price);
	
	/**
	 * 查询工价不为0的工位
	 * @param contract_id
	 * @return
	 */
	@Select("select * from price,station where contract_id=#{contract_id} and price!=0 "
			+ "and price.station=station.station order by station.id asc")
	public List<Price> queryNotNullPriceList(@Param("contract_id") String contract_id);
	
}
