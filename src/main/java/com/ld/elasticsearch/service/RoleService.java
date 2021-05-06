package com.ld.elasticsearch.service;

import com.ld.elasticsearch.entity.mysql.MRole;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    Integer save(MRole role);
    MRole findById(Integer id);
    //查询name的唯一性 存在返回 true
    Boolean checkNameUnique(MRole role);
    void delete(List<Integer> idList);
    Page<MRole> list(Example<MRole> of,Pageable pageable);
    List<MRole> list();
}
