package com.lianxiang.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*Description:自定义Token
*@author WJoe
*@time 上午11:26:37
*/
public class CustomerAuthenticationToken extends UsernamePasswordToken {

	
	 /**
	 * @date 2018年4月25日
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerAuthenticationToken.class);

	    private String captcha;
	    /**
	     * 用来区分前后台登录的标记
	     */
	    private String loginType;
	    /**
	     * 用来区分登录用户的渠道
	     */
	    private String loginForm;

	    public CustomerAuthenticationToken(String username, String password, boolean rememberMe) {
	        super(username, password, rememberMe);
	    }

	    public String getCaptcha() {
	        return captcha;
	    }

	    public void setCaptcha(String captcha) {
	        this.captcha = captcha;
	    }

	    public String getLoginType() {
	        return loginType;
	    }

	    public void setLoginType(String loginType) {
	        this.loginType = loginType;
	    }

	    public String getLoginForm() {
	        return loginForm;
	    }

	    public void setLoginForm(String loginForm) {
	        this.loginForm = loginForm;
	    }
}
