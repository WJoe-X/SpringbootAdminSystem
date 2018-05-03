package com.lenovo.bootstrap.service;

import java.util.List;

import com.lenovo.bootstrap.po.AdminRoleKey;

/**
*Description:
*@author WJoe
*@time 下午2:51:17
*/
public interface AdminRoleService {

	List<AdminRoleKey> getRoleList(AdminRoleKey adminRole);

	int deleteByAdminId(String uid);

	int save(AdminRoleKey adminRole);

}
