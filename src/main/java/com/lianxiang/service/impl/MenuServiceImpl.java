package com.lianxiang.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lianxiang.mapper.MenuMapper;
import com.lianxiang.po.Menu;
import com.lianxiang.po.MenuExample;
import com.lianxiang.service.MenuService;

/**
 * Description:菜单业务接口
 * 
 * @author WJoe
 * @time 下午3:43:40
 */

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public Set<String> getAllMenuCode() {
		Set<String> set = new HashSet<>();
		List<Menu> list = this.menuMapper.selectByExample(null);

		for (Menu menu : list) {
			set.add(menu.getMenuCode());
		}
		return set;
	}

	@Override
	public Set<String> findMenuCodeByUserId(String uid) {
		return this.menuMapper.findMenuCodeByUserId(uid);
	}

	@Override
	public Integer getCount(MenuExample example) {
		Long count = menuMapper.countByExample(example);
		return count.intValue();
	}

	@Override
	public List<Menu> findAll() {
		List<Menu> list = this.menuMapper.selectByExample(null);
		return list;
	}

	@Override
	public List<Menu> findByAdminId(String uid) {
		return this.menuMapper.selectMenuByAdminId(uid);
	}

}
