package com.ld.elasticsearch.repository.mysql;

import com.ld.elasticsearch.entity.mysql.MRoleFunction;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MRoleFunctionRepository extends JpaRepository<MRoleFunction,Integer> {
    List<MRoleFunction> findByRoleIdIn(List<Integer> roleIdList);
}
