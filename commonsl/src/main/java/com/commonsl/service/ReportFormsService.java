package com.commonsl.service;

import com.commonsl.model.ReportForms;

import java.util.List;

public interface ReportFormsService extends BaseService<Integer, ReportForms> {

    public void saveAll(List<ReportForms> reportForms);

    public void batchUpdate(List<ReportForms> reportForms, String startDate, String endDate);

}