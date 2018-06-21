package com.lenovo.bootstrap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lenovo.bootstrap.enums.LoginEnum;
import com.lenovo.bootstrap.po.Admin;
import com.lenovo.bootstrap.po.Menu;
import com.lenovo.bootstrap.po.valid.AdminValid;
import com.lenovo.bootstrap.service.AdminService;
import com.lenovo.bootstrap.service.LogService;
import com.lenovo.bootstrap.service.MenuService;
import com.lenovo.bootstrap.service.PictureService;
import com.lenovo.bootstrap.service.RoleService;
import com.lenovo.bootstrap.shiro.CustomerAuthenticationToken;
import com.lenovo.bootstrap.util.IpUtil;
import com.lenovo.bootstrap.util.MenuTreeUtil;
import com.lenovo.bootstrap.util.ReturnUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午2:40:27
 */

@Controller
@RequestMapping(value = "console")
public class AdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private LogService logService;
	

	/**
	 * 后台登录界面
	 * 
	 * @return
	 */

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		try {
			Boolean isAuth = SecurityUtils.getSubject().isAuthenticated();
			if (isAuth) {
				return "console/index";
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage()); // TODO: handle exception
		}

		return "console/login";
	}

	/**
	 * 后台登录
	 * 
	 * RedirectAttributes 可以添加这个参数，使得重定向时可以添加一些临时属性
	 * 
	 * @param adminValid
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginPost(@Valid AdminValid adminValid, BindingResult result, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "redirect:console/login";
		}
		LOGGER.info(adminValid.getUsername() + "后台登录成功");
		String username = adminValid.getUsername();
		CustomerAuthenticationToken token = new CustomerAuthenticationToken(adminValid.getUsername(),
				adminValid.getPassword(), false);
		token.setLoginType(LoginEnum.ADMIN.toString());

		// 获取当前subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			if (currentUser.isAuthenticated()) {
				Session session = SecurityUtils.getSubject().getSession();
				session.setAttribute("username", adminValid.getUsername());

				// 登录日志记录
				String ip = IpUtil.getIpAddr(request);
				logService.saveLoginLog(username, ip, request.getContextPath());
				return "redirect:/console/index";
			}
		} catch (UnknownAccountException uae) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			redirectAttributes.addFlashAttribute("msg", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			redirectAttributes.addFlashAttribute("msg", "密码不正确");
		} catch (LockedAccountException lae) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			redirectAttributes.addFlashAttribute("msg", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			redirectAttributes.addFlashAttribute("msg", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			LOGGER.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("msg", "用户名或密码不正确");
		}
		token.clear();
		return "redirect:/console/login";

	}

	/**
	 * 后台登录成功返回的页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		List<Menu> treeGridList = this.getMenu(admin);
		model.addAttribute("admin", admin);
		model.addAttribute("menuLists", treeGridList);
		return "console/index";
	}

	@ResponseBody
	@RequestMapping(value = "/wapper", method = { RequestMethod.GET })
	public ModelMap wapper() {
		try {
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<Menu> treeGridList = this.getMenu(admin);
			ModelMap mp = new ModelMap();
			mp.put("admin", admin);
			mp.put("menuLists", treeGridList);
			return ReturnUtil.Success(null, mp, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error(null, null, null);
		}
	}

	@RequestMapping(value = "/main", method = { RequestMethod.GET })
	public String right(Model model) {
		model.addAllAttributes(this.getTotal());
		return "console/right";
	}

	@RequestMapping(value = "/main", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap main() {
		try {
			return ReturnUtil.Success(null, this.getTotal(), null);

		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error(null, null, null);
		}
	}

	/**
	 * 后台登出
	 * 
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttributes) {
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
		redirectAttributes.addFlashAttribute("message", "您已安全退出");

		return "redirect:/console/login";
	}

	@GetMapping("user/{uid}")
	public ModelAndView edtpwd(@PathVariable("uid") String uid,Model model) {
		Admin admin = this.adminService.getById(uid);
		/*Picture picture = this.pictureService.findById(29);*/
		ModelAndView mv = new ModelAndView();
		/*mv.addObject("picture", picture);*/
		
		mv.addObject("admin",admin);
		mv.setViewName("/console/admin/savepwd");
		return mv;
	}

	@RequestMapping("/403")
	public String unauthorizedRole() {
		LOGGER.info("------没有权限-------");
		return "403";
	}

	private List<Menu> getMenu(Admin admin) {
		List<Menu> menuLists = null;
		if (admin.getIsSystem() == 1) {
			menuLists = menuService.findAll();
		} else {
			menuLists = menuService.findByAdminId(admin.getUid());
		}
		MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuLists, null);
		return menuTreeUtil.buildTreeGrid();
	}

	private Map<String, Object> getTotal() {

		Integer adminCount = adminService.getCount(null);

		Integer roleCount = roleService.getCount(null);

		Integer menuCount = menuService.getCount(null);
		Map<String, Object> mp = new HashMap<>();
		mp.put("admin", adminCount);
		mp.put("role", roleCount);
		mp.put("menu", menuCount);
		return mp;
	}

}
