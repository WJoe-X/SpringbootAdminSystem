package com.lenovo.bootstrap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.bootstrap.mapper.RoleMenuMapper;
import com.lenovo.bootstrap.po.RoleMenuExample;
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
	public int deleteByMenuId(String id) {
		RoleMenuExample example = new RoleMenuExample();
		example.createCriteria().andMenuIdEqualTo(id);
		return this.roleMenuMapper.deleteByExample(example);
	}

}
