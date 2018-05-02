package com.lianxiang.service;

import java.util.List;

import com.lianxiang.po.AdminRoleKey;

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
