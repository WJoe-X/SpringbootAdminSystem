package lib.sixzeroseven.admin.vo;

import java.util.List;

import lib.sixzeroseven.admin.po.Admin;
import lib.sixzeroseven.admin.po.Menu;
import lib.sixzeroseven.admin.po.Role;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午1:53:13
 */
public class MenuVo  {

	private Menu menu;
	private List<Menu> children;

	private List<Role> roleList;

	private List<Admin> adminList;

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "MenuDto [menu=" + menu + ", children=" + children + ", roleList=" + roleList + ", adminList="
				+ adminList + "]";
	}

		

}
