package com.zwx.dao;

import com.zwx.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmailAndPassword(String email,String password);

    User findByEmailOrUsername(String email,String username);

    @Transactional
    @Modifying
    @Query("update User s set s.updateTime=?2 where s.email=?1")
    int updateTime(String email, Date time);

    @Transactional
    @Modifying
    @Query("update User s set s.background=?2 where s.id=?1")
    int setbackground(Long id,String url);

    @Transactional
    @Modifying
    @Query("update User s set s.avatar=?2 where s.id=?1")
    int setavatar(Long id,String url);

}
