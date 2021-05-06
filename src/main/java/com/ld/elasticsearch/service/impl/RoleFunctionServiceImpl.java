package com.ld.elasticsearch.service.impl;

import com.ld.elasticsearch.entity.mysql.MRoleFunction;
import com.ld.elasticsearch.repository.mysql.MRoleFunctionRepository;
import com.ld.elasticsearch.service.RoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleFunctionServiceImpl implements RoleFunctionService {

    @Autowired
    private MRoleFunctionRepository mRoleFunctionRepository;
    @Override
    public MRoleFunction save(MRoleFunction MRoleFunction) {
        MRoleFunction save = mRoleFunctionRepository.save(MRoleFunction);
        return save;
    }

    @Override
    public void delete(Integer userId) {
        mRoleFunctionRepository.deleteById(userId);
    }
    @Override
    public List<MRoleFunction> findByRoleIdIn(List<Integer> roleIdList) {
        return mRoleFunctionRepository.findByRoleIdIn(roleIdList);
    }
}
