package com.ld.elasticsearch.service;

import com.ld.elasticsearch.entity.LayuiTree;
import com.ld.elasticsearch.entity.mysql.MFunction;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FunctionService {
    Object save(MFunction mFunction);
    List<MFunction> findByName(String name);
    MFunction findById(Integer id);
    void delete(List<Integer> idList);
    List<MFunction> findByNameAndParentId(String name, Integer parentId);
    List<MFunction> findByNameAndParentIdNotId(String name, Integer parentId, Integer id);
    //查询name的唯一性 存在返回 true
    Boolean checkNameUnique(MFunction mFunction);
    Page<MFunction> list(Example<MFunction> of, Pageable pageable);
    List<LayuiTree> selectFunctionTree();
    List<LayuiTree> selectFunctionTree(List<Integer> functionIdsList);
}
