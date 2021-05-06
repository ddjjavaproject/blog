package com.ld.elasticsearch.service.impl;

import com.ld.elasticsearch.entity.mysql.MUserRole;
import com.ld.elasticsearch.repository.mysql.MUserRoleRepository;
import com.ld.elasticsearch.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private MUserRoleRepository mUserRoleRepository;
    @Override
    public MUserRole save(MUserRole mUserRole) {
        MUserRole save = mUserRoleRepository.save(mUserRole);
        return save;
    }

    @Override
    public void delete(Integer userId) {
        mUserRoleRepository.deleteById(userId);
    }

    @Override
    public List<MUserRole> findRoleByUserId(Integer userId) {
        return mUserRoleRepository.findMUserRolesByUserId(userId);
    }
}
