package com.lenovo.bootstrap.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.aspect.TimeLog;
import com.lenovo.bootstrap.po.Admin;
import com.lenovo.bootstrap.po.AdminRoleKey;
import com.lenovo.bootstrap.po.Role;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.AdminRoleService;
import com.lenovo.bootstrap.service.AdminService;
import com.lenovo.bootstrap.service.RoleService;
import com.lenovo.bootstrap.util.PasswordUtil;
import com.lenovo.bootstrap.util.ReturnUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午2:45:02
 */

@Controller
@RequestMapping("console/admin")
public class ConsoleAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleAdminController.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRoleService adminRoleService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model) {
		return "console/admin/index";
	}

	/**
	 * 
	 * 
	 * @param admin
	 * @param model
	 * @return
	 */
	@RequiresPermissions("admin:edit")
	@RequestMapping(value = "/from", method = { RequestMethod.GET })
	public String from(Admin admin, Model model) {
		String checkRoleId = "";

		if (!StringUtils.isEmpty(admin.getUid())) {
			admin = adminService.getById(admin.getUid());
			if (!"null".equals(admin)) {
				AdminRoleKey adminRole = new AdminRoleKey();
				adminRole.setAdminId(admin.getUid());
				List<AdminRoleKey> adminRoleLists = adminRoleService.getRoleList(adminRole);

				admin.setUpdatedAt(new Date());
				ArrayList<String> checkRoleIds = new ArrayList<String>();
				for (AdminRoleKey adminRoleList : adminRoleLists) {
					checkRoleIds.add(adminRoleList.getRoleId());
				}
				checkRoleId = String.join(",", checkRoleIds);
			}
		} else {
			admin.setIsSystem((byte) 0);
		}
		model.addAttribute("checkRoleId", checkRoleId);
		model.addAttribute("roleLists", this.getRoleList());
		model.addAttribute("admin", admin);
		return "console/admin/from";
	}

	/**
	 * 管理员查询管理員信息列表
	 * 
	 * @param admin
	 * @return
	 */
	@TimeLog
	@RequiresPermissions("admin:index")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap list(ListVaild listVaild, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		ModelMap map = new ModelMap();
		PageInfo<Admin> pageInfo = adminService.getAllList(listVaild);
		map.put("pageInfo", pageInfo);
		LOGGER.info("admin list pageInfo : {} ", pageInfo.toString());
		return ReturnUtil.Success("加载成功", map, null);
	}

	/**
	 * 添加管理员,更新管理员信息
	 * 
	 * @param admin
	 * @param result
	 * @return
	 */
	@RequiresPermissions("admin:save")
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(@Valid Admin admin, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {

			// admin 不存在
			if (StringUtils.isEmpty(admin.getUid())) {

				Integer userCount = adminService.getCountByUsername(admin.getUsername());
				if (userCount > 0) {
					return ReturnUtil.Error("用户名已存在", null, null);
				}
				adminService.save(admin);
			} else {
				Admin updateAdmin = adminService.getById(admin.getUid());
				if (!"null".equals(updateAdmin)) {
					admin.setSalt(updateAdmin.getSalt());
					if (!StringUtils.isEmpty(admin.getPassword())) {
						String password = PasswordUtil.createAdminPwd(admin.getPassword(),
								updateAdmin.getUsername()+updateAdmin.getSalt());
						admin.setPassword(password);
					} else {
						admin.setPassword(updateAdmin.getPassword());
					}
					adminService.updateById(admin);

				}
			}
			adminRoleService.deleteByAdminId(admin.getUid());
			if (admin.getRoleId() != null) {
				for (String roleid : admin.getRoleId()) {
					AdminRoleKey adminRole = new AdminRoleKey();
					adminRole.setAdminId(admin.getUid());
					adminRole.setRoleId(roleid);
					adminRoleService.save(adminRole);
				}
			}
			return ReturnUtil.Success("操作成功", null, "/console/admin/index");
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	
	

	//@RequiresPermissions("admin:editpwd")
	@RequestMapping(value = "/savepwd", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap editPwd(String uid, String password) {
		try {
			if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(password)) {
				adminService.updatePwd(uid, password);
				return ReturnUtil.Success("操作成功", null, null);
			} else {
				return ReturnUtil.Error("参数错误，修改失败", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("修改失败", null, null);
		}
	}

	@RequiresPermissions("admin:delete")
	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap delete(String[] ids) {
		try {
			if (ids != null) {
				if (StringUtils.isNotBlank(ids.toString())) {
					for (String id : ids) {
						adminRoleService.deleteByAdminId(id);
						adminService.deleteById(id);
					}
				}
				return ReturnUtil.Success("删除成功", null, null);
			} else {
				return ReturnUtil.Error("删除失败", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("删除失败", null, null);
		}
	}

	private List<Role> getRoleList() {

		List<Role> roleList = roleService.getFromAll();
		return roleList;
	}

}
