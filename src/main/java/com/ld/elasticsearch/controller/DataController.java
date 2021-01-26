package com.ld.elasticsearch.controller;

import com.ld.elasticsearch.entity.es.EsBlog;
import com.ld.elasticsearch.entity.mysql.MysqlBlog;
import com.ld.elasticsearch.repository.es.EsBlogRepository;
import com.ld.elasticsearch.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    private EsBlogRepository esBlogRepository;
    @RequestMapping("/list")
    public Object list(){
        List<MysqlBlog> blogList = this.mysqlBlogRepository.queryAll();
        System.out.println(blogList.size());
        return blogList;
    }
    @PostMapping("/search")
    public Map search(@RequestBody Param param){
        HashMap<String, Object> map = new HashMap<>();
        String type = param.getType();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (type.equals("es")) {
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title",param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content",param.getKeyword()));
            String string = builder.toString();
            System.out.println("---:"+string);
            Page<EsBlog> search = (Page<EsBlog>) esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list",content);
        }else if(type.equals("mysql")){
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlog(param.getKeyword());
            map.put("list",mysqlBlogs);
        }else{
            map.put("list",new ArrayList<>());
        }
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        map.put("curation",totalTimeMillis);
        return map;
    }
    @GetMapping("/get/{id}")
    public Object getById(@PathVariable("id") Integer id){
        Optional<MysqlBlog> byId = mysqlBlogRepository.findById(id);
        MysqlBlog mysqlBlog = byId.get();
        return mysqlBlog;
    }
    @Data
    public static class Param{
        //mysql es
        private String type;
        private String keyword;
    }
}
