package com.lenovo.bootstrap.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
 * Description:日志管理
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

	/**
	 * 返回日志视图
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		return "console/log/index";
	}

	/**
	 * 日志列表
	 * 
	 * @param listVaild
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ModelMap list(@Valid ListVaild listVaild, BindingResult result, Log log) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}

		LOGGER.info("LogList pageNumber : {} pageSize : {} .", listVaild.getPageNumber(), listVaild.getPageSize());
		PageInfo<Log> pageInfo = new PageInfo<>();
		if (StringUtils.isEmpty(log.getLogUser())) {
			pageInfo = logService.getPageList(listVaild);
		} else {
			pageInfo = logService.getPageList(listVaild, log);
		}
		ModelMap map = new ModelMap();
		map.put("pageInfo", pageInfo);
		return ReturnUtil.Success("加载成功", map, null);
	}

	/**
	 * 返回日志统计视图
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/count", method = { RequestMethod.GET })
	public String countIndex(Model model) {
		return "console/log/count";
	}

	/**
	 * 日志统计图
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/countNumber", method = RequestMethod.GET)
	public Map<String, List<?>> count() {
		try {

			return this.logService.count();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());// TODO: handle exception
		}
		return null;

	}
}
