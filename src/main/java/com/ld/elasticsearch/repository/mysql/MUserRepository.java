package com.ld.elasticsearch.repository.mysql;

import com.ld.elasticsearch.entity.mysql.MUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MUserRepository extends JpaRepository<MUser, Integer> {
    MUser findByNameAndPwd(String name,String pwd);
    MUser findByName(String name);
    MUser findByNameAndIdIsNot(String name,Integer id);
}
