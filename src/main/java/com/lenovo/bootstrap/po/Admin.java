package com.lenovo.bootstrap.po;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Admin   implements Serializable{
    /**
	 * @date 2018年5月4日
	 */
	private static final long serialVersionUID = 1L;

	private String uid;

	@NotEmpty
    private String username;
	@NotEmpty
    private String password;

    private Byte state;

    private String salt;
    
    private Byte isSystem;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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