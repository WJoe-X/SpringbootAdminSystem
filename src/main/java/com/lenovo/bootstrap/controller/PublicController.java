package com.lenovo.bootstrap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description:访问某个页面前面+page
 * 
 * @author WJoe
 * @time 下午2:26:43
 */

@Controller
@RequestMapping("/")
public class PublicController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "/console/login";
	}

	/**
	 * 返回视图名
	 * 
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value = "page/{pageName}", method = RequestMethod.GET)
	public String pageTo(@PathVariable("pageName") String pageName) {
		LOGGER.info("---直接访问页面  ：    {}" ,pageName);
		return pageName;

	}

}
