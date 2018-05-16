package com.lenovo.bootstrap.controller;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lenovo.bootstrap.enums.LoginEnum;
import com.lenovo.bootstrap.po.Member;
import com.lenovo.bootstrap.po.valid.MemberValid;
import com.lenovo.bootstrap.service.MemberService;
import com.lenovo.bootstrap.shiro.CustomerAuthenticationToken;
import com.lenovo.bootstrap.util.PasswordUtil;
import com.lenovo.bootstrap.util.ReturnUtil;
import com.lenovo.bootstrap.util.UuidUtil;

/**
 * Description:用户接口
 * 
 * @author WJoe
 * @time 下午8:58:29
 */

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("/index")
	public String index(Model model) {
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection principals = subject.getPrincipals();
		Member member = (Member) principals.getPrimaryPrincipal();
		String account = member.getAccount();
		model.addAttribute("account", account);
		return "member/home";
	}

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		try {
			Boolean isAuth = SecurityUtils.getSubject().isAuthenticated();
			if (isAuth) {
				return "redirect:/member/index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/login";
	}

	/**
	 * 处理用户登录操作
	 *
	 * @param validMember
	 * @param bindingResult
	 * @param redirectAttributes
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelMap doLogin(@Valid MemberValid validMember, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return ReturnUtil.Error("用户名或密码为空", null, null);
		}
		String username = validMember.getAccount();
		CustomerAuthenticationToken token = new CustomerAuthenticationToken(username, validMember.getPassword(), false);
		token.setLoginType(LoginEnum.CUSTOMER.toString());
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			LOGGER.info("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			redirectAttributes.addFlashAttribute("message", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			redirectAttributes.addFlashAttribute("message", "密码不正确");
		} catch (LockedAccountException lae) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			redirectAttributes.addFlashAttribute("message", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			LOGGER.info("前台用户[" + username + "]登录认证通过");
			return ReturnUtil.Success("登录成功");
		} else {
			token.clear();
			return ReturnUtil.Error("登录失败", null, null);
		}
	}

	/**
	 * 注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String reg() {
		try {
			Boolean isAuth = SecurityUtils.getSubject().isAuthenticated();
			if (isAuth) {
				return "redirect:/member/index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/reg";
	}

	/**
	 * 前端用户注册
	 *
	 * @param member
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public ModelMap doReg(@Valid Member member, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ReturnUtil.Error("用户名或密码为空", null, null);
		}
		LOGGER.info(member.getAccount() + "用户注册验证第一步");
		try {

			Integer userCount = memberService.getCountByAccount(member.getAccount());
			if (userCount > 0) {
				return ReturnUtil.Error("用户名已存在", null, null);
			}
			if (StringUtils.isEmpty(member.getPassword())) {
				return ReturnUtil.Error("密码不能为空", null, null);
			}
			LOGGER.info(member.getAccount() + "用户可以注册");
			memberService.save(member);
			LOGGER.info(member.getAccount() + "用户注册成功");
			return ReturnUtil.Success("操作成功", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}

	/**
	 * 退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		try {

			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "redirect:/member/login";

	}

}
