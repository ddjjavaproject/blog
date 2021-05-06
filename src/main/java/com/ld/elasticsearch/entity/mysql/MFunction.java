package com.ld.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;

/**
 * 权限相关表
 */
@Data
@Entity
@Table(name = "auth_function")
public class MFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer parentId;
    private String url;
    private Integer sortnum;
    private Integer isMenu;
}