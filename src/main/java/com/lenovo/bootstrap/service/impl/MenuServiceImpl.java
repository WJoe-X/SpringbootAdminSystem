package com.lenovo.bootstrap.service.impl;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.bootstrap.mapper.MenuMapper;
import com.lenovo.bootstrap.po.Menu;
import com.lenovo.bootstrap.po.MenuExample;
import com.lenovo.bootstrap.service.MenuService;
import com.lenovo.bootstrap.util.UuidUtil;
import com.lenovo.bootstrap.vo.MenuVo;

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

	@Override
	public List<Menu> getChildMenuList(List<Menu> list, String parentId) {
		MenuExample example = new MenuExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		example.setOrderByClause("listorder asc, created_at desc");
		List<Menu> ll = this.menuMapper.selectByExample(example);
		for (Menu menu : ll) {
			list.add(menu);
			this.getChildMenuList(list, menu.getMenuId());
		}

		return list;
	}

	@Override
	public Menu getByMenuId(String menuId) {
		return this.menuMapper.selectByPrimaryKey(menuId);
	}

	@Override
	@Transactional
	public int save(Menu menu) {
		if (StringUtils.isEmpty(menu.getMenuId())) {
			String Id = UuidUtil.getUUID();
			menu.setMenuId(Id);
			return this.menuMapper.insert(menu);
		} else {
			return this.menuMapper.updateByPrimaryKeySelective(menu);
		}
	}

	@Override
	@Transactional
	public int updateByMenuId(Menu menu) {
		return this.menuMapper.updateByPrimaryKey(menu);
	}

	@Override
	public int deleteByMenuId(String menuId) {
		return this.menuMapper.deleteByPrimaryKey(menuId);
	}

	@Override
	public List<Menu> selectMenuByRoleId(String roleId) {
		return menuMapper.selectMenuByRoleId(roleId);

	}

	@Override
	@Transactional
	public Integer saveMenu(@Valid Menu menu) {
		if (StringUtils.isEmpty(menu.getMenuId())) {
			String Id = UuidUtil.getUUID();
			menu.setMenuId(Id);
			this.menuMapper.insert(menu);
		} else {
			this.menuMapper.updateByPrimaryKeySelective(menu);
		}
		if (!menu.getParentId().equals("0")) {
			// 更新父类总数
			MenuExample example = new MenuExample();
			example.createCriteria().andParentIdEqualTo(menu.getParentId());
			Integer parentCount = this.getCount(example);
			Menu parentMenu = this.getByMenuId(menu.getParentId());
			parentMenu.setChildNum(parentCount);
			return this.save(parentMenu);
		}
		return null;
	}
}
