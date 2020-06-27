package com.zwx.service;

import com.zwx.pojo.Admin;

/**
 * Created by limi on 2017/10/15.
 */
public interface AdminService {

    Admin checkAdmin(String adminname, String password);
}
