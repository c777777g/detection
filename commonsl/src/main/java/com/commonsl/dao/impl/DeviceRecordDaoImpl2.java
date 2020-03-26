package com.commonsl.dao.impl;

import com.commonsl.dao.DeviceRecordDao;
import com.commonsl.model.DeviceRecord;
import com.commonsl.model.ReportForms;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("sqlSessionFactory")
public interface DeviceRecordDaoImpl2 {

    public void batchSave(List<DeviceRecord> deviceRecords);

}