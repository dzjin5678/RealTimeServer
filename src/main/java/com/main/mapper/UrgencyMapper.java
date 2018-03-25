package com.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.main.domain.Urgency;
/**
 * 紧迫度操作类
 * @author dzjin
 *
 */
@Component
@Mapper
public interface UrgencyMapper {
	
	/**
	 * 获取急迫度表
	 * @return
	 */
	@Select("select * from Urgency,Station where Urgency.id=Station.id order by Urgency.id asc")
	public List<Urgency> getUrgencyList();
	
	/**
	 * 更新急迫度
	 * @param id
	 * @param a1
	 * @param a2
	 * @param a3
	 * @param a4
	 * @return
	 */
	@Update("update Urgency set a1=#{a1},a2=#{a2},a3=#{a3},a4=#{a4} where id=#{id}")
	public int updateUrgency(
			@Param("id") String id,
			@Param("a1") String a1,
			@Param("a2") String a2,
			@Param("a3") String a3,
			@Param("a4") String a4);

}
