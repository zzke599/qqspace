package com.qqspace.service.admin;

import com.qqspace.bean.Admin;

import java.util.Map;

/**
 * @author Ww
 */
public interface AdminService {
    /**
     *  判断管理员登录
     */
    Admin adminLogin(Admin recode);

    Admin selectAdminById(int loginAdminId);

    Map<String,Object> selectStatisicsData();
}
