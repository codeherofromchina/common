package com.erui.report.service.impl;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.MemberMapper;
import com.erui.report.model.MarketerCount;
import com.erui.report.model.Member;
import com.erui.report.model.MemberExample;
import com.erui.report.service.MemberService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends BaseService<MemberMapper> implements MemberService {
	private final static Logger logger = LoggerFactory.getLogger(InquiryCountServiceImpl.class);

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		Member member = null;
		if (!testOnly) {
			writeMapper.truncateTable();
		}
		for (int index = 0; index < size; index++) {
			int cellIndex = index + 2;
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.MEMBER, response, cellIndex)) {
				continue;
			}
			member = new Member();
			try {
				member.setInputDate(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d HH:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "日期字段格式错误");
				continue;
			}

			if (strArr[1] != null) {
				try {
					member.setGeneralMemberCount(new BigDecimal(strArr[1]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "普通会员数量不是数字");
					continue;
				}
			}
			if (strArr[2] != null) {
				try {
					member.setGeneralMemberRebuy(new BigDecimal(strArr[2]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "普通会员二次购买数量不是数字");
					continue;
				}
			}
			if (strArr[3] != null) {
				try {
					member.setSeniorMemberCount(new BigDecimal(strArr[3]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "高级会员数量不是数字");
					continue;
				}
			}
			if (strArr[4] != null) {
				try {
					member.setSeniorMemberRebuy(new BigDecimal(strArr[4]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "高级会员二次购买数量不是数字");
					continue;
				}
			}
			if (strArr[5] != null) {
				try {
					member.setGoldMemberCount(new BigDecimal(strArr[5]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "黄金会员数量不是数字");
					continue;
				}
			}
			if (strArr[6] != null) {
				try {
					member.setGoldMemberRebuy(new BigDecimal(strArr[6]).intValue());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					response.incrFail();
					response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, "黄金会员二次购买数量不是数字");
					continue;
				}
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(member);
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.MEMBER.getTable(), cellIndex, e.getMessage());
				continue;
			}
			response.incrSuccess();

		}
		response.setDone(true);

		return response;

	}

	@Override
	public int selectByTime(Date startTime, Date endDate) {
		MemberExample example = new MemberExample();
		example.createCriteria().andInputDateBetween(startTime, endDate);
		return this.readMapper.selectByTime(example);
	}

	@Override
	public Map<String,Object> selectMemberByTime() {
		Map<String,Object> member = readMapper.selectMemberByTime();
		if (member == null){
			member = new HashMap<>();
			member.put("s1",0);
			member.put("s2",0);
			member.put("s3",0);
			member.put("s4",0);
			member.put("s5",0);
			member.put("s6",0);
		}
		//黄金会员 高级会员 一般会员
		BigDecimal goldMember = null;
		BigDecimal seniorMember = null;
		BigDecimal generalMember = null;
		if(!member.containsKey("s1") || !member.containsKey("s3") || !member.containsKey("s5")){
			member.put("s1",0);
			member.put("s3",0);
			member.put("s5",0);
		}else {
			goldMember = new BigDecimal(member.get("s5").toString());
			seniorMember = new BigDecimal(member.get("s3").toString());
			generalMember = new BigDecimal(member.get("s1").toString());
		}
		//黄金会员 高级会员 一般会员 复购率
		BigDecimal regoldMember = null;
		BigDecimal reseniorMember = null;
		BigDecimal regeneralMember = null;
		if (!member.containsKey("s2") || !member.containsKey("s4") || !member.containsKey("s6")){
			member = new HashMap<>();
			member.put("s2",0);
			member.put("s4",0);
			member.put("s6",0);
		}else {
			regoldMember = new BigDecimal(member.get("s6").toString());
			reseniorMember = new BigDecimal(member.get("s4").toString());
			regeneralMember = new BigDecimal(member.get("s2").toString());
		}
		double goldMemberRate = RateUtil.intChainRate(regoldMember.intValue(),goldMember.intValue());
		double seniorMemberRate = RateUtil.intChainRate(reseniorMember.intValue(),seniorMember.intValue());
		double generalMemberRate = RateUtil.intChainRate(regeneralMember.intValue(),generalMember.intValue());
		Map<String,Object> gold = new HashMap<>();
		Map<String,Object> senior = new HashMap<>();
		Map<String,Object> general = new HashMap<>();
		Map<String,Object> data = new HashMap<>();
		gold.put("count",goldMember);
		gold.put("rebuyRate",goldMemberRate);
		senior.put("count",seniorMember);
		senior.put("rebuyRate",seniorMemberRate);
		general.put("count",generalMember);
		general.put("rebuyRate",generalMemberRate);
		data.put("goldMember",gold);
		data.put("seniorMember",senior);
		data.put("generalMember",general);
		return data;
	}
	
	
	public static void main(String[] args) throws ParseException {
		Date date = DateUtil.parseString2Date("2017/9/09", "yyyy/M/d", "yyyy/M/d HH:mm:ss",
				DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR);
		
		System.out.println(DateUtil.formatDateToString(date, DateUtil.FULL_FORMAT_STR));
	}
}