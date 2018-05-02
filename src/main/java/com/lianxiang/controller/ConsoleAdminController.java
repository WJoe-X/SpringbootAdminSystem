package com.lianxiang.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lianxiang.po.Admin;
import com.lianxiang.po.AdminExample;
import com.lianxiang.po.AdminRoleKey;
import com.lianxiang.po.Role;
import com.lianxiang.service.AdminRoleService;
import com.lianxiang.service.AdminService;
import com.lianxiang.service.RoleService;
import com.lianxiang.util.PasswordUtil;
import com.lianxiang.util.ReturnUtil;
import com.lianxiang.util.UuidUtil;

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
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        //dateFormat.setLenient(false);
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

	 }  

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model) {
		return "console/admin/index";
	}

	/**
	 * 管理員更新信息
	 * 
	 * @param admin
	 * @param model
	 * @return
	 */
	// @RequiresPermissions("admin:edit")
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
	// @RequiresPermissions("admin:index")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap list(@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		ModelMap map = new ModelMap();
		PageInfo<Admin> pageInfo = adminService.getAllList(pageNumber, pageSize);
		for (Admin list : pageInfo.getList()) {
			List<Role> rolelist = roleService.findRoleListByAdminId(list.getUid());
			LOGGER.info(" findRoleListByAdminId : " + list.getCreatedAt());
			list.setRoleList(rolelist);
		}
		map.put("pageInfo", pageInfo);
		LOGGER.info(" pageInfo : " + pageInfo.toString());
		return ReturnUtil.Success("加载成功", map, null);
	}

	@Transactional
	// @RequiresPermissions("admin:save")
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(@Valid Admin admin, BindingResult result) {
		try {
			LOGGER.info("ADMIN CREATEAT : " + admin.getCreatedAt() + "  updateat : " + admin.getUpdatedAt());
			if (result.hasErrors()) {
				for (ObjectError er : result.getAllErrors())
					return ReturnUtil.Error(er.getDefaultMessage(), null, null);
			}
			if (StringUtils.isEmpty(admin.getUid())) {
				AdminExample example = new AdminExample();
				example.createCriteria().andUsernameEqualTo(admin.getUsername());
				Integer userCount = adminService.getCount(example);
				if (userCount > 0) {
					return ReturnUtil.Error("用户名已存在", null, null);
				}		
				adminService.save(admin);
			} else {
				Admin updateAdmin = adminService.getById(admin.getUid());
				if (!"null".equals(updateAdmin)) {
					if (!StringUtils.isEmpty(admin.getPassword())) {
						String password = PasswordUtil.createAdminPwd(admin.getPassword(),
								updateAdmin.getUsername() + updateAdmin.getSalt());
						updateAdmin.setPassword(password);
					} else {
						updateAdmin.setPassword(updateAdmin.getPassword());
					}
					
					adminService.updateById(updateAdmin);
				} else {
					return ReturnUtil.Error("操作失败", null, null);
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

	@RequiresPermissions("admin:editpwd")
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