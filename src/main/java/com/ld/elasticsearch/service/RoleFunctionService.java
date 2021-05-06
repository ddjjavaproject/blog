package com.ld.elasticsearch.service;

import com.ld.elasticsearch.entity.mysql.MRoleFunction;
import com.ld.elasticsearch.entity.mysql.MUserRole;

import java.util.List;

/**
 * 角色权限关联service
 */
public interface RoleFunctionService {

    MRoleFunction save(MRoleFunction mRoleFunction);

    void delete(Integer roleId);

    List<MRoleFunction> findByRoleIdIn(List<Integer> roleIdList);
}
