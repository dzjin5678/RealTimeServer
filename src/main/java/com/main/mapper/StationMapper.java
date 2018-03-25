package com.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.main.domain.Station;
/**
 * 工位数据操作类
 * @author dzjin
 *
 */
@Component
@Mapper
public interface StationMapper {
	
	/**
	 * 插入工位
	 * @param id
	 * @param station
	 * @return
	 */
	@Insert("insert into station(id,station) values(#{id},#{station})")
	public int insertStation(@Param("id")String id,@Param("station")String station);
	
	/**
	 * 根据工位名称查询工位的相关字段
	 * @param station
	 * @return
	 */
	@Select("select * from station where station=#{station}")
	public List<Station> getStationByStation(@Param("station")String station);
	
	/**
	 * 查询所有的工位
	 * @return
	 */
	@Select("select * from station")
	public List<Station> queryStationList();
	
	/**
	 * 查询工位的数量
	 * @return
	 */
	@Select("select count(*) from station")
	public int getStationNumber();
	
	/**
	 * 根据ID删除工位
	 * @param id
	 * @return
	 */
	@Delete("delete from station where id=#{id}")
	public int deleteStation(@Param("id")String id);
	
	/**
	 * 根据ID更新工位
	 * @param id
	 * @param station
	 * @return
	 */
	@Update("update station set station=#{station} where id=#{id}")
	public int updateStation(@Param("id")String id,@Param("station")String station);

}
