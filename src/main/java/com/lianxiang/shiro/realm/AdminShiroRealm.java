package com.lianxiang.shiro.realm;
/**
*Description:
*@author WJoe
*@time 下午3:37:45
*/

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lianxiang.po.Admin;
import com.lianxiang.service.AdminService;
import com.lianxiang.service.MenuService;

public class AdminShiroRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminShiroRealm.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private MenuService menuService;

	/**
	 * 后台认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		Admin userInfo = adminService.findByUsername(username);
		if (null == userInfo) {
			throw new UnknownAccountException();
		}
		if ("0".equals(userInfo.getState().toString())) {
			throw new LockedAccountException(); // 账户被锁定
		}

		/*
		 * 获取权限信息:这里没有进行实现， 请自行根据UserInfo,Role,Permission进行实现；
		 * 获取之后可以在前端for循环显示所有链接;
		 */
		// userInfo.setPermissions(userService.findPermissions(member));
		// 账号判断;
		// 加密方式;
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(),
				ByteSource.Util.bytes(userInfo.getUsername() + userInfo.getSalt()), getName());// salt=username+salt

		return authenticationInfo;
	}

	/***
	 * 
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principals
     * @return
     */
	 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 /*
	        * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
	        * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
	        * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
	        * 缓存过期之后会再次执行。
	        */
		
		 SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	        Admin userInfo  = (Admin)principals.getPrimaryPrincipal();
	        Set<String> menus = null;
	        if(userInfo.getIsSystem() == 1) {
	        	//TODO
	            menus = menuService.getAllMenuCode();
	        }else{
	        	
	        	//TODO
	            menus = menuService.findMenuCodeByUserId(userInfo.getUid());
	        }
	        authorizationInfo.setStringPermissions(menus);
	        return authorizationInfo;
	}

}
