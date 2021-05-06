package com.ld.elasticsearch.service;

import com.ld.elasticsearch.entity.mysql.MUser;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Integer save(MUser user);
    MUser findById(Integer id);
    MUser findByNameAndPwd(String name,String pwd);
    //查询name的唯一性 存在返回 true
    Boolean checkNameUnique(MUser user);
    void delete(List<Integer> idList);
    Page<MUser> list(Example<MUser> of,Pageable pageable);
}
