package com.zwx.service;

import com.zwx.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface UserService {

    User saveUser(User user);

    User checkUser(String email,String password);

    User checkEmailorname(String email,String username);

    int updateTime(String email, Date time);

    Page<User> listUser(Pageable pageable);

    void deleteUser(Long id);

    int setbackground(Long id,String url);

    int setavatar(Long id,String url);

    User getUserById(Long id);

    Long countUser();
}
