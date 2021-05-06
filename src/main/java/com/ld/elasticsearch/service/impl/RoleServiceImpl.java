package com.ld.elasticsearch.service.impl;

import com.ld.elasticsearch.entity.mysql.MRole;
import com.ld.elasticsearch.entity.mysql.MUser;
import com.ld.elasticsearch.repository.mysql.MRoleRepository;
import com.ld.elasticsearch.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private MRoleRepository mRoleRepository;
    @Override
    public Integer save(MRole role) {
        MRole save = mRoleRepository.save(role);
        return save.getId();
    }

    @Override
    public MRole findById(Integer id) {
        Optional<MRole> role = mRoleRepository.findById(id);
        log.info("roleServiceImpl-findById-是否查到数据：" + role.isPresent());
        if(role.isPresent())
            return role.get();
        return new MRole();
    }

    @Override
    public Boolean checkNameUnique(MRole role) {
        MRole roleData;
        if (role.getId() != null) {
            roleData = mRoleRepository.findByNameAndIdIsNot(role.getName(), role.getId());
        } else {
            roleData = mRoleRepository.findByName(role.getName());
        }
        if(roleData != null){
            return true;
        }
        return false;
    }

    @Override
    public Page<MRole> list(Example<MRole> of,Pageable pageable) {
        return mRoleRepository.findAll(of,pageable);
    }

    @Override
    public List<MRole> list() {
        return mRoleRepository.findAll();
    }


    @Override
    public void delete(List<Integer> idList) {
        idList.forEach(id->{
            mRoleRepository.deleteById(id);
        });
    }
}
