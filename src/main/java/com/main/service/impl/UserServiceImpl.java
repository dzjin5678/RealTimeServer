package com.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.domain.User;
import com.main.mapper.UserMapper;
import com.main.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public User getUserByPhone(String phone) {
		return userMapper.getUserByPhone(phone);
	}

	@Override
	public List<User> queryUserList() {
		return userMapper.queryUserList();
	}

	@Override
	public int addUser(String user, String password, String phone, String mac, String receive, String open,
			String price, String dispatch, String worker, String admin) {
		return userMapper.addUser(user, password, phone, mac, receive, open, price, dispatch, worker, admin);
	}

	@Override
	public int updateUser(String id, String user, String password, String phone, String mac, String receive,
			String open, String price, String dispatch, String worker, String admin) {
		return userMapper.updateUser(id, user, password, phone, mac, receive, open, price, dispatch, worker, admin);
	}

	@Override
	public int deleteUser(String id) {
		return userMapper.deleteUser(id);
	}

	@Override
	public User getUserByUserName(String user) {
		return userMapper.getUserByUserName(user);
	}

}
