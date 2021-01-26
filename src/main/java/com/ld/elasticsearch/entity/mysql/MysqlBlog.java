package com.ld.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "blog")
public class MysqlBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String author;
    private String title;
    @Column(columnDefinition = "text")
    private String content;
    private Date createTime;
    private Date updateTime;

}
