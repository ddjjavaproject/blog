package com.ld.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_user_role")
/**
 * 用户角色关联表
 */
public class MUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private Integer status;
}
