package com.ld.elasticsearch.repository.mysql;

import com.ld.elasticsearch.entity.mysql.MUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MUserRoleRepository extends JpaRepository<MUserRole,Integer> {
    List<MUserRole> findMUserRolesByUserId(Integer userId);
}
