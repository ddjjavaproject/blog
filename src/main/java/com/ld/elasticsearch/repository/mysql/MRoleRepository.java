package com.ld.elasticsearch.repository.mysql;

import com.ld.elasticsearch.entity.mysql.MRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MRoleRepository extends JpaRepository<MRole, Integer> {
    MRole findByName(String name);
    MRole findByNameAndIdIsNot(String name,Integer id);
}
