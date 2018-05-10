package com.erui.boss.web.power;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.power.service.AuthorityService;
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
     *
     * @param params
     *      {""userId:"","url":""}
     * @return
     */
    @RequestMapping("validated")
    public Result<Object> validated(@RequestBody Map<String,String> params) {
        String url = params.get("url");
        Integer userId = Integer.parseInt(params.get("userId"));

        if (url != null && userId > 0){
            if ( authorityService.validate(userId,url) ) {
                return new Result<>(true);
            }
        }

        return new Result<>(ResultStatusEnum.LACK_OF_AUTHORITY);
    }
}
