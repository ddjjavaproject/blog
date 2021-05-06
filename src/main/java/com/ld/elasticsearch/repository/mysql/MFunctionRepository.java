package com.ld.elasticsearch.repository.mysql;

import com.ld.elasticsearch.entity.mysql.MFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MFunctionRepository extends JpaRepository<MFunction, Integer> {
    @Query("select mf from MFunction mf where mf.name like concat('%', :name, '%') ")
    List<MFunction> queryFunction(@Param("name") String name);

    List<MFunction> findAllByNameAndParentId(String name, Integer parentId);
    List<MFunction> findAllByNameAndParentIdAndIdIsNot(String name, Integer parentId,Integer id);
    List<MFunction> findAllByParentId(Integer parentId);
    List<MFunction> findByIdIn(List<Integer> functionIdsList);
}
