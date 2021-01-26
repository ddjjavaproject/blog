package com.ld.elasticsearch;

import com.ld.elasticsearch.entity.es.EsBlog;
import com.ld.elasticsearch.repository.es.EsBlogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    EsBlogRepository esBlogRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void testEs(){
        System.out.println("个数："+esBlogRepository.count());
        Iterable<EsBlog> all = esBlogRepository.findAll();
        Iterator<EsBlog> iterator = all.iterator();
        EsBlog next = iterator.next();
        System.out.println("----------"+next.getTitle());
    }
}
