package com.zwx.service;

import com.zwx.dao.UserRepository;
import com.zwx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public int setbackground(Long id,String url) {
        return userRepository.setbackground(id,url);
    }

    @Override
    public Page<User> listUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public int updateTime(String email, Date time) {
        return userRepository.updateTime(email,time);
    }

    @Override
    public User checkEmailorname(String email, String username) {
        return userRepository.findByEmailOrUsername(email,username);
    }

    @Override
    public int setavatar(Long id, String url) {
        return userRepository.setavatar(id,url);
    }

    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    public User saveUser(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userRepository.save(user);
    }
}
