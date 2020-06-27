package com.zwx.service;

import com.zwx.dao.AdminRepository;
import com.zwx.pojo.Admin;
import com.zwx.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by limi on 2017/10/15.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin checkAdmin(String adminname, String password) {
        Admin admin = adminRepository.findByAdminnameAndPassword(adminname, MD5Utils.code(password));
        return admin;
    }
}
