package lib.sixzeroseven.admin.service;

import java.util.List;
import java.util.Set;

import lib.sixzeroseven.admin.po.Menu;
import lib.sixzeroseven.admin.po.MenuExample;

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

	/**
	 * 保存menu信息
	 * 
	 * @param menu
	 * @return
	 */
	public int save(Menu menu);

	public int updateByMenuId(Menu menu);

	public int deleteByMenuId(String menuId);

	/**
	 * 根据角色id所拥有的菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Menu> findMenuByRoleId(String roleId);

	/**
	 * 根据是否存在menuId,添加新的菜单或者更新菜单
	 * 
	 * 
	 * @param menu
	 * @return
	 */
	public Integer saveOrUpdateMenu(Menu menu);

}
