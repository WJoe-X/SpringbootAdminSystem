package com.lenovo.bootstrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.bootstrap.mapper.RoleMenuMapper;
import com.lenovo.bootstrap.po.RoleMenuExample;
import com.lenovo.bootstrap.po.RoleMenuKey;
import com.lenovo.bootstrap.service.RoleMenuService;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午5:34:07
 */

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Override
	public int deleteByMenuId(String menuId) {
		RoleMenuExample example = new RoleMenuExample();
		example.createCriteria().andMenuIdEqualTo(menuId);
		return this.roleMenuMapper.deleteByExample(example);
	}

	@Override
	public List<RoleMenuKey> findListByRoleId(String id) {
		RoleMenuExample example = new RoleMenuExample();
		example.createCriteria().andRoleIdEqualTo(id);
		return this.roleMenuMapper.selectByExample(example);
		
	}

	@Override
	public int deleteByRoleId(String roleId) {
		RoleMenuExample example = new RoleMenuExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		return this.roleMenuMapper.deleteByExample(example);
		
		
	}

	@Override
	@Transactional
	public int save(RoleMenuKey roleMenu) {
		
		return this.roleMenuMapper.insert(roleMenu);
		
		
	}

	

}
