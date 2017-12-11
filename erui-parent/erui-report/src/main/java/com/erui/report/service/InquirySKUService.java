package com.erui.report.service;

import java.util.Date;

public interface InquirySKUService {

    int selectSKUCountByTime(Date startDate,Date endDate);

}