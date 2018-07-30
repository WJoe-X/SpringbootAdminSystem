package lib.sixzeroseven.admin.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import lib.sixzeroseven.admin.shiro.filter.AdminFormAuthenticationFilter;
import lib.sixzeroseven.admin.shiro.realm.AdminShiroRealm;
import lib.sixzeroseven.admin.shiro.realm.CustomShiroRealm;

/**
 * Description:Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet
 * 来主控制一样。 是通过URL规则来进行过滤和权限校验，所以需要定义一系列关于URL的规则和访问权限。
 * 
 * @author WJoe
 * @time 下午3:04:13
 */

@Configuration
public class ShrioConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShrioConfig.class);

	/**
	 * 前台身份验证
	 * 
	 * @return
	 */
	@Bean(name = "customShiroRealm")
	public CustomShiroRealm customShiroRealm() {

		CustomShiroRealm customShiroRealm = new CustomShiroRealm();

		customShiroRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
		return customShiroRealm;
	}

	/**
	 * 后台身份验证
	 * 
	 * @return
	 */
	@Bean(name = "adminShiroRealm")
	public AdminShiroRealm adminShiroRealm() {

		AdminShiroRealm adminShiroRealm = new AdminShiroRealm();
		adminShiroRealm.setCredentialsMatcher(adminHashedCredentialsMatcher());
		return adminShiroRealm;

	}

	/**
	 * 用户授权信息Cache
	 */
	@Bean(name = "shiroCacheManager")
	@ConditionalOnMissingBean
	public CacheManager cacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	/**
	 * @see DefaultWebSessionManager
	 * @return
	 */
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 创建会话Cookie
		Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
		cookie.setName("WEBID");
		cookie.setHttpOnly(true);
		sessionManager.setSessionIdCookie(cookie);
		return sessionManager;

	}

	@Bean(name = "securityManager")
	@ConditionalOnMissingBean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager sm = new DefaultWebSecurityManager();

		Map<String, Object> authenticatorRealms = new HashMap<>();
		authenticatorRealms.put("adminShiroRealm", adminShiroRealm());
		authenticatorRealms.put("customShiroRealm", customShiroRealm());

		Collection<Realm> authorizerRealms = new ArrayList<Realm>();
		authorizerRealms.add(adminShiroRealm());
		authorizerRealms.add(customShiroRealm());

		CustomModularRealmAuthenticator customModularRealmAuthenticator = new CustomModularRealmAuthenticator();
		customModularRealmAuthenticator.setDefinedRealms(authenticatorRealms);
		customModularRealmAuthenticator.setAuthenticationStrategy(authenticationStrategy());

		sm.setAuthenticator(customModularRealmAuthenticator);
		sm.setRealms(authorizerRealms);

		sm.setCacheManager(cacheManager());
		sm.setSessionManager(defaultWebSessionManager());
		return sm;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@ConditionalOnMissingBean
	@Bean(name = "authorizationAttributeSourceAdvisor")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		LOGGER.debug("ShiroConfiguration.authorizationAttributeSourceAdvisor()");
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明
	 * 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 */
	@Bean(name = "shirFilter")
	public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
		LOGGER.debug("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 增加自定义过滤
		Map<String, Filter> filters = new HashMap<>();
		filters.put("admin", new AdminFormAuthenticationFilter());
		// filters.put("custom", new CustomFormAuthenticationFilter());
		// filters.put("logout", new CustomerLogoutFilter());
		shiroFilterFactoryBean.setFilters(filters);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		/**
		 * anon（匿名） org.apache.shiro.web.filter.authc.AnonymousFilter
		 * authc（身份验证）
		 * org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		 * authcBasic（http基本验证）
		 * org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
		 * logout（退出） org.apache.shiro.web.filter.authc.LogoutFilter
		 * noSessionCreation（不创建session）
		 * org.apache.shiro.web.filter.session.NoSessionCreationFilter
		 * perms(许可验证)
		 * org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
		 * port（端口验证） org.apache.shiro.web.filter.authz.PortFilter rest (rest方面)
		 * org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
		 * roles（权限验证）
		 * org.apache.shiro.web.filter.authz.RolesAuthorizationFilter ssl
		 * （ssl方面） org.apache.shiro.web.filter.authz.SslFilter member （用户方面）
		 * org.apache.shiro.web.filter.authc.UserFilter user
		 * 表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
		 */

		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**/login", "anon");
		filterChainDefinitionMap.put("/**/logout", "logout");
		filterChainDefinitionMap.put("/**/reg", "anon");
		// 配置记住我或认证通过可以访问的地址
		filterChainDefinitionMap.put("/console/policy/file/**", "anon");
		filterChainDefinitionMap.put("/console/**", "admin");
		// filterChainDefinitionMap.put("/member/**", "custom");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		// shiroFilterFactoryBean.setLoginUrl("/member/login");
		// 登录成功后要跳转的链接
		// shiroFilterFactoryBean.setSuccessUrl("/member/index");
		// 未授权界面;
		// shiroFilterFactoryBean.setUnauthorizedUrl("/console/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * Shiro默认提供了三种 AuthenticationStrategy 实现： AtLeastOneSuccessfulStrategy
	 * ：其中一个通过则成功。 FirstSuccessfulStrategy ：其中一个通过则成功，但只返回第一个通过的Realm提供的验证信息。
	 * AllSuccessfulStrategy ：凡是配置到应用中的Realm都必须全部通过。 authenticationStrategy
	 * 
	 * @return
	 */
	@Bean(name = "authenticationStrategy")
	public AuthenticationStrategy authenticationStrategy() {
		LOGGER.debug("ShiroConfiguration.authenticationStrategy()");
		return new FirstSuccessfulStrategy();
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; ）
	 * 
	 * @return
	 */
	@Bean(name = "adminHashedCredentialsMatcher")
	public HashedCredentialsMatcher adminHashedCredentialsMatcher() {
		LOGGER.debug("ShiroConfiguration.adminHashedCredentialsMatcher()");
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，当于
														// m比如散列两次，相d5(md5(""));
		return hashedCredentialsMatcher;
	}

	@Bean(name = "customHashedCredentialsMatcher")
	public HashedCredentialsMatcher customHashedCredentialsMatcher() {
		LOGGER.debug("ShiroConfiguration.customHashedCredentialsMatcher()");
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1);// 散列的次数，当于
														// m比如散列1次，相d5("");
		return hashedCredentialsMatcher;
	}

	/**
	 * 注入LifecycleBeanPostProcessor
	 * 
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		LOGGER.debug("ShiroConfiguration.lifecycleBeanPostProcessor()");
		return new LifecycleBeanPostProcessor();
	}

	@ConditionalOnMissingBean
	@Bean(name = "defaultAdvisorAutoProxyCreator")
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		LOGGER.debug("ShiroConfiguration.getDefaultAdvisorAutoProxyCreator()");
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
}
