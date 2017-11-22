package com.erui.boss.web.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ExcelReader;
import com.erui.comm.FileUtil;
import com.erui.comm.util.pinyin4j.Pinyin4j;
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

/**
 * 报表excel导入控制层
 *
 * @author wangxiaodan
 */
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
     * 下载模板
     *
     * @param response
     * @param type     模板类型，参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
     * @throws IOException
     */
    @RequestMapping(value = "/downtemp")
    @ResponseBody
    public Object downPhotoByStudentId(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(name = "type", required = true) Integer type) throws IOException {
        // 判断上传的业务文件类型
        ExcelUploadTypeEnum typeEnum = ExcelUploadTypeEnum.getByType(type);
        if (typeEnum == null) {
            return new Result<Object>(ResultStatusEnum.EXCEL_TYPE_NOT_SUPPORT);
        }

        // 获取模板文件内容
        String tempPath = request.getSession().getServletContext().getRealPath(EXCEL_TEMPLATE_PATH);
        String suffix = EXCEL_SUFFIX;
        File file = new File(tempPath, typeEnum.getTable() + suffix);
        if (!file.exists()) {
            suffix = EXCEL_SUFFIX02;
            file = new File(tempPath, typeEnum.getTable() + suffix);
        }
        byte[] data = FileUtils.readFileToByteArray(file);

        // 输出到客户端
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + Pinyin4j.convertChineseToPinyin(typeEnum.getTable()) + suffix + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();

        return null;
    }

    /**
     * 导入excel数据
     *
     * @param request
     * @param file    具体文件
     * @param type    参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateExcel(HttpServletRequest request,
                              @RequestParam(value = "file", required = true) MultipartFile file,
                              @RequestParam(value = "type", required = true) Integer type) {

        String contentType = file.getContentType();
        String name = file.getName();
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        logger.warn("上传文件[表单名称:{},文件名称:{},文件类型:{},文件大小:{}byte]",
                new Object[]{name, originalFilename, contentType, String.valueOf(size)});

        // 判断文件类型
        if (!(FileUtil.isExcelContentType(contentType) && FileUtil.isExcelSuffixFile(originalFilename))) {
            return new Result<Object>(ResultStatusEnum.EXCEL_CONTENTYPE_ERROR);
        }

        // 判断上传的业务文件类型
        ExcelUploadTypeEnum typeEnum = ExcelUploadTypeEnum.getByType(type);
        if (typeEnum == null) {
            return new Result<Object>(ResultStatusEnum.EXCEL_TYPE_NOT_SUPPORT);
        }

        // 经过上面初步判断后，保存文件到本地
        String realPath = request.getSession().getServletContext().getRealPath(EXCEL_DATA_PATH);
        File saveFile = null;
        try {
            saveFile = FileUtil.saveFile(file.getInputStream(), realPath, originalFilename);
        } catch (IOException e1) {
            logger.debug("异常:" + e1.getMessage(), e1);
            return new Result<Object>(ResultStatusEnum.EXCEL_SAVE_FAIL);
        }
        // 删除之前无用的文件 保存数据
        //int delFileNum = FileUtil.delBefore2HourFiles(realPath);
        //logger.info("删除无用excel文件数量：{}", delFileNum);

        ExcelReader excelReader = new ExcelReader();
        try {
            // 读取excel所有的数据
            List<String[]> excelContent = excelReader.readExcel(saveFile);
            // 判断数据和标题的正确性
            int dataRowSize = excelContent.size();
            if (dataRowSize < 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (!typeEnum.verifyTitleData(excelContent.get(0))) {
                return new Result<Object>(ResultStatusEnum.EXCEL_HEAD_VERIFY_FAIL);
            }
            if (dataRowSize == 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }

            ImportDataResponse importDataResponse = importData(typeEnum, excelContent.subList(1, dataRowSize), true);

            // 整理结果集并返回
            Result<Object> result = new Result<Object>();
            Map<String, Object> data = new HashMap<>();
            data.put("tmpFileName", saveFile.getName());
            data.put("type", typeEnum.getType());
            data.put("response", importDataResponse);
            result.setData(data);
            return result;
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            logger.debug("异常:" + e.getMessage(), e);
            return new Result<Object>(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
        }
    }

    /**
     * 导入excel数据
     *
     * @param request
     * @param fileName    具体文件
     * @param type    参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Object updateExcel(HttpServletRequest request,
                              @RequestParam(value = "fileName", required = true) String fileName,
                              @RequestParam(value = "type", required = true) Integer type) {
        Result<Object> result = new Result<Object>();


        // 判断上传的业务文件类型
        ExcelUploadTypeEnum typeEnum = ExcelUploadTypeEnum.getByType(type);
        if (typeEnum == null) {
            return result.setStatus(ResultStatusEnum.EXCEL_TYPE_NOT_SUPPORT);
        }
            String realPath = request.getSession().getServletContext().getRealPath(EXCEL_DATA_PATH);
        File file = new File(realPath, fileName);
        if (file.exists() && file.isFile()) {
            logger.info(String.format("导入数据到数据库{文件：%s,类型：%d}", fileName, type));
            System.out.println(String.format("导入数据到数据库{文件：%s,类型：%d}", fileName, type));
            ExcelReader excelReader = new ExcelReader();
            try {
                List<String[]> excelContent = excelReader.readExcel(file);
                if (!typeEnum.verifyTitleData(excelContent.get(0))) {
                    return result.setStatus(ResultStatusEnum.EXCEL_HEAD_VERIFY_FAIL);
                }
                ImportDataResponse importDataResponse = importData(typeEnum,
                        excelContent.subList(1, excelContent.size()), false);

                result.setData(importDataResponse);

                try {
                    // 删除数据导入成功的文件
                    FileUtils.forceDelete(file);
                } catch (IOException ex) {
                    logger.debug("异常:" + ex.getMessage(), ex);
                }
            } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
                logger.debug("异常:" + e.getMessage(), e);
                return result.setStatus(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
            }
        } else {
            return result.setStatus(ResultStatusEnum.EXCEL_FILE_NOT_EXIST);
        }

        return result;
    }

    /**
     * 选择具体使用什么业务类导入数据
     *
     * @param typeEnum
     * @param datas
     * @param testOnly true:只做数据测试 false:导入数据库
     */
    private ImportDataResponse importData(ExcelUploadTypeEnum typeEnum, List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = null;
        switch (typeEnum) {
            case CATEGORY_QUALITY:
                logger.info("导入品控数据");
                response = categoryQualityService.importData(datas, testOnly);
                break;
            case CREDIT_EXTENSION:
                logger.info("导入授信数据");
                response = creditExtensionService.importData(datas, testOnly);
                break;
            case STORAGE_ORGANI_COUNT:
                logger.info("导入仓储物流-事业部数据");
                response = storageOrganiCountService.importData(datas, testOnly);
                break;
            case HR_COUNT:
                logger.info("导入人力资源数据");
                response = hrCountService.importData(datas, testOnly);
                break;
            case INQUIRY_COUNT:
                logger.info("导入客户中心-询单数据");
                response = inquiryCountService.importData(datas, testOnly);
                break;
            case MARKETER_COUNT:
                logger.info("导入市场人员数据");
                response = marketerCountService.importData(datas, testOnly);
                break;
            case MEMBER:
                logger.info("导入运营数据");
                response = memberService.importData(datas, testOnly);
                break;
            case ORDER_COUNT:
                logger.info("导入客户中心-订单数据");
                response = orderCountService.importData(datas, testOnly);
                break;
            case ORDER_ENTRY_COUNT:
                logger.info("导入仓储物流-订单入库数据");
                response = orderEntryCountService.importData(datas, testOnly);
                break;
            case ORDER_OUTBOUND_COUNT:
                logger.info("导入仓储物流-订单出库数据");
                response = orderOutboundCountService.importData(datas, testOnly);
                break;
            case PROCUREMENT_COUNT:
                logger.info("导入采购数据");
                response = procurementCountService.importData(datas, testOnly);
                break;
            case REQUEST_CREDIT:
                logger.info("导入应收账款数据");
                response = requestCreditService.importData(datas, testOnly);
                break;
            case SUPPLY_CHAIN:
                logger.info("导入供应链数据");
                response = supplyChainService.importData(datas, testOnly);
                break;
            default:
                response = new ImportDataResponse();
                response.setDone(true);
        }
        return response;
    }

    private final static String EXCEL_DATA_PATH = "/WEB-INF/excel";
    private final static String EXCEL_TEMPLATE_PATH = "/WEB-INF/template/excel";
    private final static String EXCEL_SUFFIX = ".xlsx";
    private final static String EXCEL_SUFFIX02 = ".xls";

}
