package com.ld.elasticsearch.repository.mysql;

import com.ld.elasticsearch.entity.mysql.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MysqlBlogRepository extends JpaRepository<MysqlBlog, Integer> {
    @Query("select b from MysqlBlog b order by b.createTime desc")
    List<MysqlBlog> queryAll();

    @Query("select b from MysqlBlog b where b.title like concat('%',:keyword,'%') or b.content like concat('%',:keyword,'%')")
    List<MysqlBlog> queryBlog(@Param("keyword") String keyword);
}
