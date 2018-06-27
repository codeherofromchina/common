package com.erui.boss.web.util;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.erui.comm.util.constant.Constant;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultExceptionHandler implements HandlerExceptionResolver, Ordered {
    private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ModelAndView mv = new ModelAndView();
         /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = null;
        if (ex instanceof MissingServletRequestParameterException || ex instanceof HttpMessageNotReadableException) {
            attributes = resultStatus2Map(ResultStatusEnum.MISS_PARAM_ERROR);
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            attributes = resultStatus2Map(ResultStatusEnum.PARAM_TYPE_ERROR);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            attributes = resultStatus2Map(ResultStatusEnum.MEDIA_TYPE_NOT_SUPPORT);
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            attributes = resultStatus2Map(ResultStatusEnum.REQUEST_METHOD_NOT_SUPPORT);
        } else if (ex instanceof SQLException || ex instanceof DataException || ex instanceof DataIntegrityViolationException){
            attributes = resultStatus2Map(ResultStatusEnum.SQLDATA_ERROR);
        }else if (ex instanceof MethodArgumentNotValidException) {
            attributes = resultStatus2Map(ResultStatusEnum.FAIL);
            MethodArgumentNotValidException ee = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = ee.getBindingResult();
            List<ObjectError> ls = bindingResult.getAllErrors();
            String msg = "";
            if (ls.size() > 0) {
                msg = ls.get(0).getDefaultMessage();
            }
            if (msg.contains(Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL)) {
                String[] split = msg.split(Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL);
                attributes.put("msg", split[0]);
                attributes.put("enMsg", split[1]);
            } else {
                attributes.put("msg", msg);
            }
        } else {
            // 其他错误
            attributes = resultStatus2Map(ResultStatusEnum.SERVER_ERROR);
        }
        view.setAttributesMap(attributes);
        mv.setView(view);
        logger.debug("异常:" + ex.getMessage(), ex);
        return mv;
    }

    @Override
    public int getOrder() {
        return 0;
    }


    private Map<String, Object> resultStatus2Map(ResultStatusEnum resultStatus) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", resultStatus.getCode());
        resultMap.put("msg", resultStatus.getMsg());
        resultMap.put("enMsg", resultStatus.getEnMsg());
        return resultMap;
    }
}
