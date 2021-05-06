package com.ld.elasticsearch.service;

import com.ld.elasticsearch.entity.mysql.MUserRole;

import java.util.List;

/**
 * 用户角色关联service
 */
public interface UserRoleService {

    MUserRole save(MUserRole mUserRole);

    void delete(Integer userId);

    List<MUserRole> findRoleByUserId(Integer userId);
}
