package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ExcelReader;
import com.erui.comm.FileUtil;
import com.erui.order.service.OrderService;
import com.erui.order.util.excel.ExcelUploadTypeEnum;
import com.erui.order.util.excel.ImportDataResponse;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:SHIGS
 * @Description订单导入
 * @Date Created in 16:01 2018/6/13
 * @modified By
 */
@RestController
@RequestMapping("order/import")
public class ImportController {
    private final static Logger logger = LoggerFactory.getLogger(ImportController.class);
    @Autowired
    private OrderService orderService;

    /**
     * 导入excel数据
     *
     * @param request
     * @param file    具体文件
     * @param type    参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Object importExcel(HttpServletRequest request,
                              @RequestParam(value = "file", required = true) MultipartFile file,
                              @RequestParam(value = "type", required = true) Integer type){

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        String name = file.getName();
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
        // 经过上面初步判断后，保存文件到本地 和fastDFS
        String realPath = request.getSession().getServletContext().getRealPath(EXCEL_DATA_PATH);
       // String[] groups = null;
      /*  try {
            byte[] bytes = file.getBytes();
            groups = FastDFSUtil.uploadFile("group1", bytes, ".xlsx", null);
        } catch (IOException | MyException e1) {
            logger.debug("异常:" + e1.getMessage(), e1);
            return new Result<Object>(ResultStatusEnum.EXCEL_SAVE_FAIL);
        }*/
        ExcelReader excelReader = new ExcelReader();
        try {
            // 读取excel所有的数据
            List<String[]> excelContent = excelReader.readExcel(file.getInputStream());
            // 判断数据和标题的正确性
            int dataRowSize = excelContent.size();
            if (dataRowSize == 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (dataRowSize < 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (!typeEnum.verifyTitleData(excelContent.get(0))) {
                return new Result<Object>(ResultStatusEnum.EXCEL_HEAD_VERIFY_FAIL);
            }
            ImportDataResponse importDataResponse = importData(typeEnum, excelContent.subList(1, dataRowSize), true);
            if (!importDataResponse.getDone()) {
                return new Result<Object>(ResultStatusEnum.EXCEL_DATA_REPEAT).setData(importDataResponse);
            }
            // 整理结果集并返回
            Result<Object> result = new Result<Object>();
            Map<String, Object> data = new HashMap<>();
           // data.put("tmpFileName", groups[1]);
            data.put("type", typeEnum.getType());
            data.put("response", importDataResponse);
            result.setData(data);
            return result;
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            logger.debug("异常:" + e.getMessage(), e);
            return new Result<Object>(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
        }
    }
/*    *//**
     * 导入excel数据
     *
     * @param request
     * @param file    具体文件
     * @param type    参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
     * @return
     *//*
    @RequestMapping(value = "/importOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object importOrderExcel(HttpServletRequest request,
                              @RequestParam(value = "file", required = true) MultipartFile file,
                              @RequestParam(value = "type", required = true) Integer type){

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        String name = file.getName();
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
        // 经过上面初步判断后，保存文件到本地 和fastDFS
        String realPath = request.getSession().getServletContext().getRealPath(EXCEL_DATA_PATH);
        // String[] groups = null;
      *//*  try {
            byte[] bytes = file.getBytes();
            groups = FastDFSUtil.uploadFile("group1", bytes, ".xlsx", null);
        } catch (IOException | MyException e1) {
            logger.debug("异常:" + e1.getMessage(), e1);
            return new Result<Object>(ResultStatusEnum.EXCEL_SAVE_FAIL);
        }*//*
        ExcelReader excelReader = new ExcelReader();
        try {
            // 读取excel所有的数据
            List<String[]> excelContent = excelReader.readExcel(file.getInputStream());
            // 判断数据和标题的正确性
            int dataRowSize = excelContent.size();
            if (dataRowSize == 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (dataRowSize < 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (!typeEnum.verifyTitleData(excelContent.get(0))) {
                return new Result<Object>(ResultStatusEnum.EXCEL_HEAD_VERIFY_FAIL);
            }
            ImportDataResponse importDataResponse = importData(typeEnum, excelContent.subList(1, dataRowSize), true);
            if (!importDataResponse.getDone()) {
                return new Result<Object>(ResultStatusEnum.EXCEL_DATA_REPEAT).setData(importDataResponse);
            }
            // 整理结果集并返回
            Result<Object> result = new Result<Object>();
            Map<String, Object> data = new HashMap<>();
            // data.put("tmpFileName", groups[1]);
            data.put("type", typeEnum.getType());
            data.put("response", importDataResponse);
            result.setData(data);
            return result;
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            logger.debug("异常:" + e.getMessage(), e);
            return new Result<Object>(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
        }
    }
    *//**
     * 导入excel数据
     *
     * @param request
     * @param file    具体文件
     * @param type    参考枚举类型com.erui.report.util.ExcelUploadTypeEnum中的值
     * @return
     *//*
    @RequestMapping(value = "/importProjrct", method = RequestMethod.POST)
    @ResponseBody
    public Object importProjectExcel(HttpServletRequest request,
                                   @RequestParam(value = "file", required = true) MultipartFile file,
                                   @RequestParam(value = "type", required = true) Integer type){

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        String name = file.getName();
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
        // 经过上面初步判断后，保存文件到本地 和fastDFS
        String realPath = request.getSession().getServletContext().getRealPath(EXCEL_DATA_PATH);
        // String[] groups = null;
      *//*  try {
            byte[] bytes = file.getBytes();
            groups = FastDFSUtil.uploadFile("group1", bytes, ".xlsx", null);
        } catch (IOException | MyException e1) {
            logger.debug("异常:" + e1.getMessage(), e1);
            return new Result<Object>(ResultStatusEnum.EXCEL_SAVE_FAIL);
        }*//*
        ExcelReader excelReader = new ExcelReader();
        try {
            // 读取excel所有的数据
            List<String[]> excelContent = excelReader.readExcel(file.getInputStream());
            // 判断数据和标题的正确性
            int dataRowSize = excelContent.size();
            if (dataRowSize == 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (dataRowSize < 1) {
                return new Result<Object>(ResultStatusEnum.EXCEL_CONTENT_EMPTY);
            }
            if (!typeEnum.verifyTitleData(excelContent.get(0))) {
                return new Result<Object>(ResultStatusEnum.EXCEL_HEAD_VERIFY_FAIL);
            }
            ImportDataResponse importDataResponse = importData(typeEnum, excelContent.subList(1, dataRowSize), true);
            if (!importDataResponse.getDone()) {
                return new Result<Object>(ResultStatusEnum.EXCEL_DATA_REPEAT).setData(importDataResponse);
            }
            // 整理结果集并返回
            Result<Object> result = new Result<Object>();
            Map<String, Object> data = new HashMap<>();
            // data.put("tmpFileName", groups[1]);
            data.put("type", typeEnum.getType());
            data.put("response", importDataResponse);
            result.setData(data);
            return result;
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            logger.debug("异常:" + e.getMessage(), e);
            return new Result<Object>(ResultStatusEnum.EXCEL_OPERATOR_FAIL);
        }
    }*/
    /**
     * 选择具体使用什么业务类导入数据
     *
     * @param
     * @param datas
     * @param testOnly true:只做数据测试 false:导入数据库
     */
    private ImportDataResponse importData(ExcelUploadTypeEnum typeEnum, List<String[]> datas, boolean testOnly) {
        logger.info("导入订单数据");
        ImportDataResponse response = null;
        switch (typeEnum) {
            case ORDER_MANAGE:
                logger.info("导入订单数据");
                response = orderService.importData(datas, testOnly);
                break;
            case ORDER_CHECK:
                logger.info("导入订单校验数据");
                response = orderService.importOrder(datas, testOnly);
                break;
            case PROJECT_MANAGE:
                logger.info("导入项目管理校验数据");
                response = orderService.importProjectData(datas, testOnly);
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
