package com.erui.boss.web.power;

import com.erui.boss.web.util.Result;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("power/authority")
public class AuthorityController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);


    /**
     * @param params {""userId:"","url":""}
     * @return
     */
    @RequestMapping("validated")
    public Result<Object> validated(@RequestBody Map<String, String> params) {
        String url = params.get("url");
        int userId = 0;
        String userIdStr = params.get("userId");
        if (StringUtils.isNumeric(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        // 记录日志
        logger.info("{}\t{}",userId,url);

        // 直接放行所有的权限
        return new Result<>();
        /** 先放开所有权限
         if (StringUtils.isNotBlank(url) && userId > 0) {
         if (authorityService.validate(userId, url)) {
         return new Result<>();
         } else {
         return new Result<>(ResultStatusEnum.LACK_OF_AUTHORITY);
         }
         }

         return new Result<>(ResultStatusEnum.PARAM_ERROR);
         **/

    }
}
