package com.erui.boss.web.power;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.power.service.AuthorityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("power/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

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

        if (StringUtils.isNotBlank(url) && userId > 0) {
            if (authorityService.validate(userId, url)) {
                return new Result<>();
            } else {
                return new Result<>(ResultStatusEnum.LACK_OF_AUTHORITY);
            }
        }

        return new Result<>(ResultStatusEnum.PARAM_ERROR);
    }
}
