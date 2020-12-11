package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
/**
 * @Author Mohammad Rajaul Islam
 * @Since v.1.0
 */
@Service
public class UserService implements UserMapper {
    @Resource
    private UserMapper usersMapper;
    @Autowired
    private HashService hashService;

    @Override
    public List<User> findAll() {
        return usersMapper.findAll();
    }

    @Override
    public User findById(long id) {
        return usersMapper.findById(id);
    }

    @Override
    public User findByUserName(String username) {
        return usersMapper.findByUserName(username);
    }

    @Override
    public int deleteById(long id) {
        return usersMapper.deleteById(id);
    }

    @Override
    public int insert(User users) {

        users.setUserid(usersMapper.findAll().size()+1);
        users.setSalt(UUID.randomUUID().toString());
        users.setPassword(hashService.getHashedValue(users.getPassword(), users.getSalt()));
        return usersMapper.insert(users);
    }

    @Override
    public int update(User users) {
        return usersMapper.update(users);
    }
}
