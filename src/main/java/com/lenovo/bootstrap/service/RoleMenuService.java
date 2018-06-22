package com.lenovo.bootstrap.service;

import java.util.List;

import com.lenovo.bootstrap.po.RoleMenuKey;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:20:42
 */
public interface RoleMenuService {

	/**
	 * 根据MenuId删除信息
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByMenuId(String menuId);

	

	public List<RoleMenuKey> findListByRoleId(String id);



	public int deleteByRoleId(String roleId);



	public int save(RoleMenuKey roleMenu);

}
