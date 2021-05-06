package com.ld.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_role")
/**
 * 角色表
 */
public class MRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
