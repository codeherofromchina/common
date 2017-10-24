package com.erui.boss.web.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.erui.comm.ExcelReader;
import com.erui.comm.FileUtils;
import com.erui.report.service.CategoryQualityService;
import com.erui.report.service.CreditExtensionService;
import com.erui.report.service.HrCountService;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.MarketerCountService;
import com.erui.report.service.MemberService;
import com.erui.report.service.OrderCountService;
import com.erui.report.service.OrderEntryCountService;
import com.erui.report.service.OrderOutboundCountService;
import com.erui.report.service.ProcurementCountService;
import com.erui.report.service.RequestCreditService;
import com.erui.report.service.StorageOrganiCountService;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Controller
@RequestMapping("/report/excel")
public class ExcelController {
	private final static Logger logger = LoggerFactory.getLogger(ExcelController.class);
	@Autowired
	private CategoryQualityService categoryQualityService;
	@Autowired
	private CreditExtensionService creditExtensionService;
	@Autowired
	private StorageOrganiCountService storageOrganiCountService;
	@Autowired
	private HrCountService hrCountService;
	@Autowired
	private InquiryCountService inquiryCountService;
	@Autowired
	private MarketerCountService marketerCountService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderCountService orderCountService;
	@Autowired
	private OrderEntryCountService orderEntryCountService;
	@Autowired
	private OrderOutboundCountService orderOutboundCountService;
	@Autowired
	private ProcurementCountService procurementCountService;
	@Autowired
	private RequestCreditService requestCreditService;
	@Autowired
	private SupplyChainService supplyChainService;

	/**
	 * 导入excel数据
	 * @param request
	 * @param file	具体文件
	 * @param type	参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateExcel(HttpServletRequest request,
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "type", required = true) Integer type) {

		
		Map<String, Object> result = new HashMap<>();
		String contentType = file.getContentType();
		String name = file.getName();
		String originalFilename = file.getOriginalFilename();
		long size = file.getSize();
		logger.warn("上传文件[表单名称:{},文件名称:{},文件类型:{},文件大小:{}byte]",
				new Object[] { name, originalFilename, contentType, String.valueOf(size) });

		// 判断文件类型
		if (!(FileUtils.isExcelContentType(contentType) && FileUtils.isExcelSuffixFile(originalFilename))) {
			result.put("success", false);
			result.put("desc", "文件类型错误");
			return result;
		}

		// 判断上传的业务文件类型
		ExcelUploadTypeEnum typeEnum = ExcelUploadTypeEnum.getByType(type);
		if (typeEnum == null) {
			result.put("success", false);
			result.put("desc", "业务文件类型错误");
			return result;
		}

		ExcelReader excelReader = new ExcelReader();
		try {
			// 读取excel所有的数据
			List<String[]> excelContent = excelReader.readExcel(file.getInputStream());
			// 判断数据和标题的正确性
			int dataRowSize = excelContent.size();
			if (dataRowSize < 1) {
				result.put("success", false);
				result.put("desc", "Excel内容为空");
				return result;
			}
			if (!typeEnum.verifyTitleData(excelContent.get(0))) {
				result.put("success", false);
				result.put("desc", "Excel头验证失败");
				return result;
			}
			if (dataRowSize == 1) {
				result.put("success", false);
				result.put("desc", "Excel数据为空");
				return result;
			}

			ImportDataResponse importDataResponse = importData(typeEnum, excelContent.subList(1, dataRowSize),false);

			result.put("success", true);
			result.put("response", importDataResponse);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			logger.error("excel文件读取内容失败[fileName:{},error:{}]", originalFilename, e.getMessage());
			result.put("success", false);
			result.put("desc", "Excel读取失败");
		}

		return result;
	}

	/**
	 * 选择具体使用什么业务类导入数据
	 * 
	 * @param typeEnum
	 * @param datas
	 * @param testOnly	true:只做数据测试 false:导入数据库
	 */
	private ImportDataResponse importData(ExcelUploadTypeEnum typeEnum, List<String[]> datas,boolean testOnly) {
		ImportDataResponse response = null;
		switch (typeEnum) {
		case CATEGORY_QUALITY:
			logger.info("导入品控数据");
			response = categoryQualityService.importData(datas,testOnly);
			break;
		case CREDIT_EXTENSION:
			logger.info("导入授信数据");
			response = creditExtensionService.importData(datas,testOnly);
			break;
		case STORAGE_ORGANI_COUNT:
			logger.info("导入仓储物流-事业部数据");
			response = storageOrganiCountService.importData(datas,testOnly);
			break;
		case HR_COUNT:
			logger.info("导入人力资源数据");
			response = hrCountService.importData(datas,testOnly);
			break;
		case INQUIRY_COUNT:
			logger.info("导入客户中心-询单数据");
			response = inquiryCountService.importData(datas,testOnly);
			break;
		case MARKETER_COUNT:
			logger.info("导入市场人员数据");
			response = marketerCountService.importData(datas,testOnly);
			break;
		case MEMBER:
			logger.info("导入运营数据");
			response = memberService.importData(datas,testOnly);
			break;
		case ORDER_COUNT:
			logger.info("导入客户中心-订单数据");
			response = orderCountService.importData(datas,testOnly);
			break;
		case ORDER_ENTRY_COUNT:
			logger.info("导入仓储物流-订单入库数据");
			response = orderEntryCountService.importData(datas,testOnly);
			break;
		case ORDER_OUTBOUND_COUNT:
			logger.info("导入仓储物流-订单出库数据");
			response = orderOutboundCountService.importData(datas,testOnly);
			break;
		case PROCUREMENT_COUNT:
			logger.info("导入采购数据");
			response = procurementCountService.importData(datas,testOnly);
			break;
		case REQUEST_CREDIT:
			logger.info("导入应收账款数据");
			response = requestCreditService.importData(datas,testOnly);
			break;
		case SUPPLY_CHAIN:
			logger.info("导入供应链数据");
			response = supplyChainService.importData(datas,testOnly);
			break;
		default:
			response = new ImportDataResponse();
			response.setDone(true);
		}
		return response;
	}

}
