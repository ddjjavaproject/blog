package com.ld.elasticsearch.controller;

import com.ld.elasticsearch.entity.mysql.MysqlBlog;
import com.ld.elasticsearch.repository.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private MysqlBlogRepository mysqlBlogRepository;
    @RequestMapping("/")
    public String index(){
        List<MysqlBlog> blogList = this.mysqlBlogRepository.findAll();
        System.out.println(blogList.size());
        return "index.html";
    }
}
