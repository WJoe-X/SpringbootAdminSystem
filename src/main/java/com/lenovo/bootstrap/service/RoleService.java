package com.lenovo.bootstrap.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.po.Role;
import com.lenovo.bootstrap.po.RoleExample;
import com.lenovo.bootstrap.po.valid.ListVaild;

/**
*Description:
*@author WJoe
*@time 下午9:54:27
*/
public interface RoleService {

	
	public Integer getCount(RoleExample example);

	public List<Role> findRoleListByAdminId(String uid);

	/**
	 * 查找所以可用的角色
	 * @return
	 */
	public List<Role> getFromAll();

	public PageInfo<Role> getPageList(ListVaild listVaild);

	public Role findById(String roleId);

	public int save( Role role);

	public int deleteById(String roleId);

}
