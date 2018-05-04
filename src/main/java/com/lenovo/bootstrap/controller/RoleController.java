package com.lenovo.bootstrap.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.lenovo.bootstrap.po.Menu;
import com.lenovo.bootstrap.po.Role;
import com.lenovo.bootstrap.po.RoleMenuKey;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.AdminRoleService;
import com.lenovo.bootstrap.service.MenuService;
import com.lenovo.bootstrap.service.RoleMenuService;
import com.lenovo.bootstrap.service.RoleService;
import com.lenovo.bootstrap.util.MenuTreeUtil;
import com.lenovo.bootstrap.util.ReturnUtil;
import com.lenovo.bootstrap.util.UuidUtil;

/**
*Description:
*@author WJoe
*@time 2018年5月4日 下午4:23:29
*/

@Controller
@RequestMapping("console/role")
public class RoleController {
	
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	    @Autowired
	    private RoleService roleService;

	    @Autowired
	    private AdminRoleService adminRoleService;

	    @Autowired
	    private MenuService menuService;

	    @Autowired
	    private RoleMenuService roleMenuService;

	    @RequiresPermissions("role:index")
	    @RequestMapping(value = "/index", method = {RequestMethod.GET})
	    public String index(Model model) {
	        return "console/role/index";
	    }

	    @RequiresPermissions("role:index")
	    @RequestMapping(value = "/list", method = {RequestMethod.GET})
	    @ResponseBody
	    public ModelMap list(@Valid ListVaild listVaild,BindingResult result) {
	       
	        	if (result.hasErrors()) {
	    			for (ObjectError er : result.getAllErrors())
	    				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
	
			}
	    	
	    	ModelMap map = new ModelMap();
	        PageInfo<Role> pageInfo = roleService.getPageList(listVaild);
	        for (Role list : pageInfo.getList()) {
	            List<Menu> menuList = menuService.selectMenuByRoleId(list.getRoleId());
	            list.setMenuList(menuList);
	        }
	        map.put("pageInfo", pageInfo);
	        
	        return ReturnUtil.Success("加载成功", map, null);
	    }


	    @RequiresPermissions("role:edit")
	    @RequestMapping(value = "/from", method = {RequestMethod.GET})
	    public String from(Role role, Model model) {
	        if (!StringUtils.isEmpty(role.getRoleId())) {
	            role = roleService.findById(role.getRoleId());
	        }
	        model.addAttribute("role", role);
	        return "console/role/from";
	    }

	    @RequiresPermissions("role:save")
	    @RequestMapping(value = "/save", method = {RequestMethod.POST})
	    @ResponseBody
	    public ModelMap save(@Valid Role role, BindingResult result) {
	        if (result.hasErrors()) {
	            for (ObjectError er : result.getAllErrors()) return ReturnUtil.Error(er.getDefaultMessage(), null, null);
	        }
	        try {
	            roleService.save(role);
	            return ReturnUtil.Success("操作成功", null, "/console/role/index");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ReturnUtil.Error("操作失败", null, null);
	        }
	    }

	    @RequiresPermissions("role:delete")
	    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
	    @ResponseBody
	    public ModelMap delete(String[] ids) {
	        try {
	            if ("null".equals(ids) || "".equals(ids)) {
	                return ReturnUtil.Error("Error", null, null);
	            } else {
	                for (String roleId : ids) {
	                    adminRoleService.deleteByRoleId(roleId);
	                    roleService.deleteById(roleId);
	                }
	                return ReturnUtil.Success("操作成功", null, null);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ReturnUtil.Error("操作失败", null, null);
	        }
	    }

	    @RequestMapping(value = "/combobox", method = {RequestMethod.POST, RequestMethod.GET})
	    @ResponseBody
	    public ModelMap comboBox() {
	        ModelMap map = new ModelMap();
	        List<Role> roleList = roleService.getFromAll();
	        map.put("roleList", roleList);
	        return ReturnUtil.Success(null, map, null);
	    }

	    @RequestMapping(value = "/menutree", method = {RequestMethod.POST, RequestMethod.GET})
	    @ResponseBody
	    public ModelMap comboTree(String id) {
	        List<Menu> menuLists = menuService.findAll();
	        List<RoleMenuKey> roleMenuLists = roleMenuService.findListByRoleId(id);
	        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuLists, roleMenuLists);
	        List<Map<String, Object>> mapList = menuTreeUtil.buildTree();
	        return ReturnUtil.Success(null, mapList, null);
	    }

	    @RequiresPermissions("role:grant")
	    @RequestMapping(value = "/grant", method = {RequestMethod.POST})
	    @ResponseBody
	    public ModelMap grant(String roleId, String[] menuIds) {
	        try {
	            if (menuIds != null && StringUtils.isNotEmpty(roleId)) {
	                if (StringUtils.isNotEmpty(menuIds.toString())) {
	                    roleMenuService.deleteByRoleId(roleId);
	                    for (String menuId : menuIds) {
	                        RoleMenuKey roleMenu = new RoleMenuKey();
	                        roleMenu.setMenuId(menuId);
	                        roleMenu.setRoleId(roleId);
	                        roleMenuService.save(roleMenu);
	                    }
	                }
	            } else if (menuIds == null && StringUtils.isNotEmpty(roleId)) {
	                roleMenuService.deleteByRoleId(roleId);
	            }
	            return ReturnUtil.Success("操作成功", null, null);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ReturnUtil.Error("操作失败", null, null);
	        }
	    }

	    @RequiresPermissions("role:grant")
	    @RequestMapping(value = "/grant", method = {RequestMethod.GET})
	    public String grantForm(String roleId, Model model) {
	        model.addAttribute("roleId", roleId);
	        return "console/role/grant";
	    }

	    @RequestMapping(value = "/menulist", method = {RequestMethod.GET})
	    @ResponseBody
	    public ModelMap menulist(String id) {
	        ModelMap map = new ModelMap();
	        
	        List<RoleMenuKey> roleMenuLists = roleMenuService.findListByRoleId(id);
	        ArrayList<String> roleList = new ArrayList<>();
	        for (RoleMenuKey roleMenuList : roleMenuLists) {
	            roleList.add(roleMenuList.getMenuId());
	        }
	        map.put("id", id);
	        map.put("roleList", roleList);
	        return ReturnUtil.Success("操作成功", map, null);
	    }

}
