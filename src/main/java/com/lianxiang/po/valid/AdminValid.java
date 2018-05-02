package com.lianxiang.po.valid;

import javax.validation.constraints.NotEmpty;

/**
 * Description:用于后台登录字段校验
 * 
 * @author WJoe
 * @time 下午2:51:28
 */
public class AdminValid {

	@NotEmpty(message = "用户名不能为空")
	private String username;

	@NotEmpty(message = "密码不能为空")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
