package lib.sixzeroseven.admin.po;

import java.util.Date;
import java.util.List;


import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import lib.sixzeroseven.admin.po.base.BasePo;

public class Menu extends BasePo {
	private String menuId;

	@NotEmpty(message = "菜单名称不能为空")
	private String menuName;

	private String menuType;

	@NotEmpty(message = "菜单URL不能为空")
	private String menuUrl;

	@NotEmpty(message = "菜单标识不能为空")
	private String menuCode;

	@NotEmpty(message = "父类ID不能为空")
	private String parentId;
	private String parentIds;

	private Integer childNum;

	private Integer listorder;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss ",timezone = "GMT+8")
	private Date createdAt;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss ",timezone = "GMT+8")
	private Date updatedAt;

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

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId == null ? null : menuId.trim();
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType == null ? null : menuType.trim();
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode == null ? null : menuCode.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds == null ? null : parentIds.trim();
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public Integer getListorder() {
		return listorder;
	}

	public void setListorder(Integer listorder) {
		this.listorder = listorder;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}