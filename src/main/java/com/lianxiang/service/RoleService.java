package com.lianxiang.service;

import java.util.List;

import com.lianxiang.po.Role;
import com.lianxiang.po.RoleExample;

/**
*Description:
*@author WJoe
*@time 下午9:54:27
*/
public interface RoleService {

	Integer getCount(RoleExample example);

	List<Role> findRoleListByAdminId(String uid);

	/**
	 * 查找所以可用的角色
	 * @return
	 */
	List<Role> getFromAll();

}
