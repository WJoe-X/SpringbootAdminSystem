package com.lenovo.bootstrap.shiro.realm;

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

import com.lenovo.bootstrap.po.Member;
import com.lenovo.bootstrap.service.MemberService;

/**
 * Description:前台身份校验核心类
 * 
 * @author WJoe
 * @time 下午3:14:56
 */
public class CustomShiroRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomShiroRealm.class);

	@Autowired
	private MemberService memberService;

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		
		LOGGER.info("token: {} 进入验证" , username);
		// 通过username从数据库中查找 User对象，如果找到，没找到.
		Member userInfo = memberService.findByUsername(username);
		
		if (userInfo == null) {
			throw new UnknownAccountException();
		}
		if ("0".equals(userInfo.getState().toString())) {
			throw new LockedAccountException(); // 帐号锁定
		}
		LOGGER.info("userinfo: {} 进入验证" , userInfo.getAccount());
		// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo, // 用户
				userInfo.getPassword(), // 密码
				ByteSource.Util.bytes(userInfo.getSalt()), // salt=username+salt
				getName()); // realm name
		return authenticationInfo;
	}

	/**
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调.
	 *
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		Member member = (Member) principals.getPrimaryPrincipal();
		String username = member.getAccount();
		Member user = memberService.findByUsername(username);
		if (null != user) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// info.addRoles(user.get);
			// TODO
		}
		return null;
	}

}
