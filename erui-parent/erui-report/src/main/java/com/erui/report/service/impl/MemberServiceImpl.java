package com.erui.report.service.impl;

import com.erui.report.dao.MemberMapper;
import com.erui.report.model.Member;
import com.erui.report.model.MemberExample;
import com.erui.report.service.MemberService;
import com.erui.report.util.ImportDataResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends BaseService<MemberMapper> implements MemberService {

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectByTime(Date startTime, Date endDate) {
		MemberExample example = new MemberExample();
		example.createCriteria().andInputDateBetween(startTime,endDate);
		return this.readMapper.selectByTime(example);
	}
	@Override
	public Map selectMemberByTime() {
		Map member = readMapper.selectMemberByTime();
		return member;
	}
}