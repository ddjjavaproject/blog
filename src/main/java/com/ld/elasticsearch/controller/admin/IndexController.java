package com.ld.elasticsearch.controller.admin;

import com.ld.elasticsearch.common.Function;
import com.ld.elasticsearch.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired private RedisUtil redisUtil;

    @GetMapping("/admin")
    public String adminIndex(){
        //查询权限
        return "admin_index";
    }
    @GetMapping("/login")
    public String login(){
        return "admin_login";
    }

    /**
     * 控制台
     * @return
     */
    @GetMapping("/console")
    public String console(){
        return "console";
    }
}
