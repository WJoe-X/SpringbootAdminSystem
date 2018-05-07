package com.lenovo.bootstrap.po;

import java.io.Serializable;

public class AdminRoleKey implements Serializable{
    /**
	 * @date 2018年5月7日
	 */
	private static final long serialVersionUID = 1L;

	private String adminId;

    private String roleId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

	@Override
	public String toString() {
		return "AdminRoleKey [adminId=" + adminId + ", roleId=" + roleId + "]";
	}
    
    
    
}