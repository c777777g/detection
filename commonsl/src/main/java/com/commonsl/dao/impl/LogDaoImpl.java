package com.commonsl.dao.impl;

import com.commonsl.dao.LogDao;
import com.commonsl.model.Log;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl extends BaseDaoImpl<Integer, Log> implements LogDao {

}