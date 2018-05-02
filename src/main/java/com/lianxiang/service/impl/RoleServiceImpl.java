package com.lianxiang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lianxiang.mapper.RoleMapper;
import com.lianxiang.po.Role;
import com.lianxiang.po.RoleExample;
import com.lianxiang.service.RoleService;

/**
 * Description:
 * 
 * @author WJoe
 * @time 上午9:45:05
 */

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Integer getCount(RoleExample example) {
		Long count = this.roleMapper.countByExample(example);
		return count.intValue();
	}

	@Override
	public List<Role> findRoleListByAdminId(String uid) {
		return roleMapper.selectRoleListByAdminId(uid);
	}

	@Override
	public List<Role> getFromAll() {
		RoleExample example = new RoleExample();
		example.createCriteria().andEnableEqualTo((byte) 1);
		return this.roleMapper.selectByExample(example);
	}

}
