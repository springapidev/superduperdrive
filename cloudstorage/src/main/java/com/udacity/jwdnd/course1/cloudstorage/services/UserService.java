package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements UsersMapper {
    @Resource
    private UsersMapper usersMapper;
    @Override
    public List<Users> findAll() {
        return usersMapper.findAll();
    }

    @Override
    public Users findById(long id) {
        return usersMapper.findById(id);
    }

    @Override
    public Users findByUserName(String username) {
        return usersMapper.findByUserName(username);
    }

    @Override
    public int deleteById(long id) {
        return usersMapper.deleteById(id);
    }

    @Override
    public int insert(Users users) {
        return usersMapper.insert(users);
    }

    @Override
    public int update(Users users) {
        return usersMapper.update(users);
    }
}
