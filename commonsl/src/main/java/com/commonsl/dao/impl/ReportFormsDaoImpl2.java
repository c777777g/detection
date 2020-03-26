package com.commonsl.dao.impl;

import com.commonsl.model.ReportForms;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("sqlSessionFactory")
public interface ReportFormsDaoImpl2 {

    public void saveAll(List<ReportForms> reportForms);

    public void batchUpdate(@Param("list") List<ReportForms> reportForms, @Param("startDate") String startDate, @Param("endDate") String endDate);

}
