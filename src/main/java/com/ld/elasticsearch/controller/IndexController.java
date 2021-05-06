package com.ld.elasticsearch.controller;

import com.ld.elasticsearch.entity.mysql.MysqlBlog;
import com.ld.elasticsearch.repository.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller("HomeIndexController")
public class IndexController {
    @Autowired
    private MysqlBlogRepository mysqlBlogRepository;
    @GetMapping("/")
    public String index(){
        List<MysqlBlog> blogList = this.mysqlBlogRepository.findAll();
        System.out.println(blogList.size());
        return "index";
    }
}
