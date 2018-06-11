package com.erui.report.service;

import java.util.HashMap;
import java.util.List;


public interface SupplierOnshelfInfoService {

    void  insertSupplierOnshelfInfoList(String startTime,List<HashMap> onshelfInfoList ) throws Exception;
}
