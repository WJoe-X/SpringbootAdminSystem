package com.lianxiang.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lianxiang.mapper.AdminRoleMapper;
import com.lianxiang.po.AdminRoleExample;
import com.lianxiang.po.AdminRoleKey;
import com.lianxiang.service.AdminRoleService;

/**
*Description:
*@author WJoe
*@time 下午2:51:37
*/

@Service
public class AdminRoleServiceImpl  implements AdminRoleService{
	
	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Override
	@Cacheable(cacheNames = "AdminRoleService-getRoleList")
	public List<AdminRoleKey> getRoleList(AdminRoleKey adminRole) {
		AdminRoleExample example =new AdminRoleExample();
		if (StringUtils.isNotEmpty(adminRole.getAdminId())) {
			example.createCriteria().andAdminIdEqualTo(adminRole.getAdminId());
		}
		if (StringUtils.isNotEmpty(adminRole.getRoleId())) {
			example.createCriteria().andRoleIdEqualTo(adminRole.getRoleId());
		}
		
		return this.adminRoleMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public int deleteByAdminId(String uid) {
		AdminRoleExample example =new AdminRoleExample();
		if (StringUtils.isNotEmpty(uid)) {
			example.createCriteria().andAdminIdEqualTo(uid);
			int count =this.adminRoleMapper.deleteByExample(example);
			return count;
		}
		 return 0;
	}

	@Override
	@Transactional
	public int save(AdminRoleKey adminRole) {
		if (StringUtils.isNotEmpty(adminRole.getAdminId())&&StringUtils.isNotEmpty(adminRole.getRoleId())) {
		return	this.adminRoleMapper.insert(adminRole);
		}
		
		return 0;
	}

}
