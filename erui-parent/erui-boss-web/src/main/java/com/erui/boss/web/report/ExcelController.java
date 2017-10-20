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
import com.erui.report.service.ProcurementCountService;
import com.erui.report.service.RequestCreditService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	private final static Logger logger = LoggerFactory.getLogger(ExcelController.class);

	@Autowired
	private ProcurementCountService procurementCountService;
	// 注入应收账款业务类
	@Autowired
	private RequestCreditService requestCreditService;

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
			// TODO这里需要验证数据的正确性，稍后实现

			ImportDataResponse importDataResponse = importData(typeEnum, excelContent.subList(1, dataRowSize));
			
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
	 * @param typeEnum
	 * @param datas
	 */
	private ImportDataResponse importData(ExcelUploadTypeEnum typeEnum, List<String[]> datas) {
		ImportDataResponse response = null;
		switch (typeEnum) {
		case CATEGORY_QUALITY:
			break;
		case CREDIT_EXTENSION:
			break;
		case STORAGE_ORGANICOUNT:
			break;
		case HR_COUNT:
			break;
		case INQUIRY_COUNT:
			break;
		case MARKETER_COUNT:
			break;
		case MEMBER:
			break;
		case ORDER_COUNT:
			break;
		case ORDER_ENTRY_COUNT:
			break;
		case ORDER_OUTBOUND_COUNT:
			break;
		case PROCUREMENT_COUNT:
			logger.info("导入采购数据");
			response = procurementCountService.importData(datas);
			break;
		case REQUEST_CREDIT:
			response = requestCreditService.importData(datas);
			break;
		case SUPPLY_CHAIN:
			break;
		default:
			response = new ImportDataResponse();
		}
		return response;
	}

}
