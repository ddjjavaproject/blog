package com.ld.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth_role_function")
/**
 * 角色权限关联表
 */
public class MRoleFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer roleId;
    private Integer functionId;
    private Integer status;
}
