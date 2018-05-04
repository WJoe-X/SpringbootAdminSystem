package com.lenovo.bootstrap.controller;

import javax.validation.Valid;

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
import com.lenovo.bootstrap.po.Log;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.LogService;
import com.lenovo.bootstrap.util.ReturnUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:02:04
 */

@Controller
@RequestMapping(value = "console/log")
public class LogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		return "console/log/index";
	}

	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ModelMap list(@Valid ListVaild listVaild,BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		ModelMap map = new ModelMap();
		LOGGER.info("LogList pageNumber : {} pageSize : {} ." ,listVaild.getPageNumber(),listVaild.getPageSize());
		PageInfo<Log> pageInfo = logService.getPageList(listVaild);
		map.put("pageInfo", pageInfo);
		return ReturnUtil.Success("加载成功", map, null);
	}
}
