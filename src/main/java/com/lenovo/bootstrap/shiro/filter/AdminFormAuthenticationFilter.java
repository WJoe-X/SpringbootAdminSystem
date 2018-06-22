package com.lenovo.bootstrap.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:53:36
 */
public class AdminFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	public void setLoginUrl(String loginUrl) {
		super.setLoginUrl("/console/login");
	}

	@Override
	public void setSuccessUrl(String successUrl) {
		super.setSuccessUrl("/console/index");
	}

}
