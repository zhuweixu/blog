package com.zwx.dao;

import com.zwx.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByAdminnameAndPassword(String adminname, String password);
}
