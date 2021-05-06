package com.ld.elasticsearch.service.impl;

import com.ld.elasticsearch.entity.mysql.MUser;
import com.ld.elasticsearch.repository.mysql.MUserRepository;
import com.ld.elasticsearch.service.UserService;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private MUserRepository mUserRepository;
    @Override
    public Integer save(MUser user) {
        MUser save = mUserRepository.save(user);
        return save.getId();
    }

    @Override
    public MUser findById(Integer id) {
        Optional<MUser> user = mUserRepository.findById(id);
        log.info("userServiceImpl-findById-是否查到数据：" + user.isPresent());
        if(user.isPresent())
            return user.get();
        return new MUser();
    }

    @Override
    public MUser findByNameAndPwd(String name, String pwd) {
        MUser user = mUserRepository.findByNameAndPwd(name, pwd);
        return user;
    }

    @Override
    public Boolean checkNameUnique(MUser user) {
        MUser userData;
        if (user.getId() != null) {
            userData = mUserRepository.findByNameAndIdIsNot(user.getName(), user.getId());
        } else {
            userData = mUserRepository.findByName(user.getName());
        }
        if(userData != null){
            return true;
        }
        return false;
    }

    @Override
    public Page<MUser> list(Example<MUser> of,Pageable pageable) {
        return mUserRepository.findAll(of,pageable);
    }


    @Override
    public void delete(List<Integer> idList) {
        idList.forEach(id->{
            mUserRepository.deleteById(id);
        });
    }
}
