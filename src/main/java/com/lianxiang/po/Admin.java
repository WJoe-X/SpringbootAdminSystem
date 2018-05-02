package com.lianxiang.po;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.lianxiang.po.base.BasePo;

public class Admin  extends BasePo{
    private String uid;

    private String username;

    private String password;

    private Byte state;

    private String salt;

    private Byte isSystem;
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    
    
    
    private String[] roleId;

  

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	private List<Role> roleList;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Byte getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Byte isSystem) {
        this.isSystem = isSystem;
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
    
    public String[] getRoleId() {
  		return roleId;
  	}

  	public void setRoleId(String[] roleId) {
  		this.roleId = roleId;
  	}

	@Override
	public String toString() {
		return "Admin [uid=" + uid + ", username=" + username + ", password=" + password + ", state=" + state
				+ ", salt=" + salt + ", isSystem=" + isSystem + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", roleId=" + Arrays.toString(roleId) + ", roleList=" + roleList + "]";
	}
  	
  	
  	
}