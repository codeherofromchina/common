package com.erui.report.service.impl;

import com.erui.report.dao.MemberMapper;
import com.erui.report.service.MemberService;
import com.erui.report.util.ImportDataResponse;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends BaseService<MemberMapper> implements MemberService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}
}