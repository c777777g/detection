package com.commonsl.dao.impl;

import com.commonsl.dao.UserDao;
import com.commonsl.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<Integer, User> implements UserDao {

}