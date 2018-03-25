package com.main.service;

import java.util.List;

import com.main.domain.User;

public interface UserService {
	
	/**
	 * 根据手机号查询用户信息
	 * @param phone
	 * @return
	 */
	public User getUserByPhone(String phone);
	
	public User getUserByUserName(String user);
	
	/**
	 * 查询所有的用户
	 * @return
	 */
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
	public int addUser(String user,String password,String phone,String mac,
			String receive,String open,String price,String dispatch,String worker,String admin);
	
	/**
	 * 更新用户信息
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
	public int updateUser(String id,String user,String password,String phone,String mac,
			String receive,String open,String price,String dispatch,String worker,String admin);
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int deleteUser(String id);
}
