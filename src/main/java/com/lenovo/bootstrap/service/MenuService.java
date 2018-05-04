package com.lenovo.bootstrap.service;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.lenovo.bootstrap.po.Menu;
import com.lenovo.bootstrap.po.MenuExample;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午3:43:24
 */
public interface MenuService {

	public Set<String> getAllMenuCode();

	public Set<String> findMenuCodeByUserId(String uid);

	/**
	 * 获取所有menu列表
	 * 
	 * @return
	 */

	public List<Menu> findAll();

	public List<Menu> findByAdminId(String uid);

	public Integer getCount(MenuExample example);

	/**
	 * 获取所有子菜单列表
	 * 
	 * @param list
	 * @param parentId
	 * @return
	 */
	public List<Menu> getChildMenuList(List<Menu> list, String parentId);

	/**
	 * 通过MenuId获取Menu信息
	 * 
	 * @param menuId
	 * @return
	 */
	public Menu getByMenuId(String menuId);

	public int save(Menu menu);

	public int updateByMenuId(Menu menu);

	public int deleteByMenuId(String menuId);

	public List<Menu> selectMenuByRoleId(String roleId);


}
