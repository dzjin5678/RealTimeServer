package com.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.main.domain.User;

@Component
@Mapper
public interface UserMapper {
	
	/**
	 * 根据手机号查询用户信息
	 * @param phone
	 * @return
	 */
	@Select("select * from user where phone=#{phone}")
	public User getUserByPhone(@Param("phone")String phone);
	
	@Select("select * from user where user=#{user}")
	public User getUserByUserName(@Param("user")String user);
	
	/**
	 * 查询所有的用户
	 * @return
	 */
	@Select("select * from user where user!='admin'")
	public List<User> queryUserList();
	
	/**
	 * 添加用户
	 * @param user
	 * @param password
	 * @param phone
	 * @param mac
	 * @param receive
	 * @param open
	 * @param price
	 * @param dispatch
	 * @param worker
	 * @param admin
	 * @return
	 */
	@Insert("insert into User(user,password,phone,mac,receive,open,price,dispatch,worker,admin) values("
			+ "#{user},#{password},#{phone},#{mac},#{receive},#{open},#{price},#{dispatch},#{worker},#{admin})")
	public int addUser(
			@Param("user")String user,
			@Param("password")String password,
			@Param("phone")String phone,
			@Param("mac")String mac,
			@Param("receive")String receive,
			@Param("open")String open,
			@Param("price")String price,
			@Param("dispatch")String dispatch,
			@Param("worker")String worker,
			@Param("admin")String admin);
	
	/**
	 * 更新用户
	 * @param id
	 * @param user
	 * @param password
	 * @param phone
	 * @param mac
	 * @param receive
	 * @param open
	 * @param price
	 * @param dispatch
	 * @param worker
	 * @param admin
	 * @return
	 */
	@Update("update User set user=#{user},password=#{password},phone=#{phone},mac=#{mac},receive=#{receive},"
			+ "open=#{open},price=#{price},dispatch=#{dispatch},worker=#{worker},admin=#{admin} where id=#{id}")
	public int updateUser(
			@Param("id")String id,
			@Param("user")String user,
			@Param("password")String password,
			@Param("phone")String phone,
			@Param("mac")String mac,
			@Param("receive")String receive,
			@Param("open")String open,
			@Param("price")String price,
			@Param("dispatch")String dispatch,
			@Param("worker")String worker,
			@Param("admin")String admin);
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@Delete("delete from user where id=#{id}")
	public int deleteUser(@Param("id")String id);

}
