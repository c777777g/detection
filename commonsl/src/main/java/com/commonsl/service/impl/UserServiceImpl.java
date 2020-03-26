package com.commonsl.service.impl;

import com.commonsl.dao.UserDao;
import com.commonsl.model.User;
import com.commonsl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<Integer, User> implements UserService {
	
	@Autowired
	private UserDao dao;

}