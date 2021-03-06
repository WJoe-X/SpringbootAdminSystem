package lib.sixzeroseven.admin.controller;

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

import lib.sixzeroseven.admin.po.Menu;
import lib.sixzeroseven.admin.service.MenuService;
import lib.sixzeroseven.admin.service.RoleMenuService;
import lib.sixzeroseven.admin.util.MenuTreeUtil;
import lib.sixzeroseven.admin.util.ReturnUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:19:10
 */

@Controller
@RequestMapping(value = "console/menu")
public class MenuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleMenuService roleMenuService;

	@RequiresPermissions("menu:index")
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		List<Menu> Lists = menuService.getChildMenuList(new ArrayList<Menu>(), "0");
		model.addAttribute("menus", Lists);
		return "console/menu/index";
	}

	@RequiresPermissions("menu:index")
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap list() {
		ModelMap map = new ModelMap();
		List<Menu> List = menuService.findAll();
		MenuTreeUtil menuTreeUtil = new MenuTreeUtil(List, null);
		List<Menu> treeGridList = menuTreeUtil.buildTreeGrid();
		map.put("treeList", treeGridList);
		map.put("total", List.size());
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequiresPermissions("admin:edit")
	@RequestMapping(value = "/from", method = { RequestMethod.GET })
	public String add(Menu menu, Model model) {
		if (StringUtils.isEmpty(menu.getParentId())) {
			menu.setParentId("0");
		}
		if (!StringUtils.isEmpty(menu.getMenuId())) {
			menu = menuService.getByMenuId(menu.getMenuId());
			if (!"null".equals(menu)) {
				menu.setUpdatedAt(new Date());
			}
		} else {
			menu.setChildNum(0);
			menu.setListorder(0);
			menu.setMenuType("menu");
			menu.setCreatedAt(new Date());
			menu.setUpdatedAt(new Date());
		}
		model.addAttribute("menu", menu);
		return "console/menu/from";
	}

	/**
	 * 更新或者修改菜单
	 * @param menu
	 * @param result
	 * @return
	 */
	@RequiresPermissions("menu:save")
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(@Valid Menu menu, BindingResult result) {
		try {
			if (result.hasErrors()) {
				for (ObjectError er : result.getAllErrors())
					return ReturnUtil.Error(er.getDefaultMessage(), null, null);
			}

			this.menuService.saveOrUpdateMenu(menu);
			return ReturnUtil.Success("操作成功", null, "/console/menu/index");
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}

	@RequiresPermissions("menu:listorder")
	@RequestMapping(value = "/listorder", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap updateOrder(String id, Integer listorder) {
		if (StringUtils.isNotBlank(id)) {
			Menu menu = new Menu();
			menu.setListorder(listorder);
			menu.setMenuId(id);
			menuService.updateByMenuId(menu);
			return ReturnUtil.Success("Success", null, null);
		} else {
			return ReturnUtil.Error("Error", null, null);
		}
	}

	@ResponseBody
	@RequiresPermissions("menu:delete")
	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	public ModelMap delete(String[] ids) {
		try {
			if ("null".equals(ids) || "".equals(ids)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				for (String id : ids) {
					roleMenuService.deleteByMenuId(id);
					menuService.deleteByMenuId(id);
				}
				return ReturnUtil.Success("Success", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("Error", null, null);
		}
	}

}
