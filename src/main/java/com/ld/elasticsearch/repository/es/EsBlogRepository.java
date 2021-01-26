package com.ld.elasticsearch.repository.es;

import com.ld.elasticsearch.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Integer> {
}
