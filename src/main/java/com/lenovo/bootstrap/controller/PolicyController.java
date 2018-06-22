package com.lenovo.bootstrap.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.service.PolicyService;
import com.lenovo.bootstrap.util.ReturnUtil;
import com.lenovo.bootstrap.vo.PolicyPropertyVo;

/**
 * Description:
 * 
 * @author WJoe
 * @time 2018年6月20日 上午10:55:54
 */

@Controller
@RequestMapping("console/policy")
public class PolicyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyController.class);
	@Autowired
	private PolicyService policyService;

	/**
	 * 跳转console/policy/index页面
	 * 
	 * @return
	 */
	@GetMapping("index")
	public String index() {
		return "console/policy/index";

	}

	/**
	 * 跳转console/policy/from,添加policy
	 * 
	 * @return
	 */
	@GetMapping("add")
	public String from() {

		return "console/policy/add";
	}

	/**
	 * 返回已有的policy列表
	 * 
	 * @return
	 */
	@GetMapping("list")
	@ResponseBody
	public PageInfo<PolicyPropertyVo> list() {
		List<PolicyPropertyVo> list = new ArrayList<>();
		try {
			list = this.policyService.getPolicyProperty();
			return new PageInfo<PolicyPropertyVo>(list);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 添加策略
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping()
	@ResponseBody
	public ModelMap save(@RequestBody String json) {
		try {
			LOGGER.info("-----------策略     {}--", json);
			// String str =
			// "{'name':'75','shoppingCartItemList':[{'id':'407','num':'10'}]}";
			Boolean boo = this.policyService.savePolicyToJson(json);
			if (boo) {
				return ReturnUtil.Success("添加策略成功");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return ReturnUtil.Error("添加策略失败");
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @param name
	 * @param req
	 * @return
	 */
	@GetMapping("m")
	public String toGetPolicy(String name, HttpServletRequest req) {
		LOGGER.info("--------选中的json文件名 : {}  ！",name);
		req.setAttribute("name", name);
		return "/console/policy/modify";

	}

	/**
	 * 根据名字读取相应的策略
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String getPolicy(@PathVariable("name") String name) {
		try {
			LOGGER.info("--------------name : {} ", name);
			String result = this.policyService.getPolicy(name);
			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "请求json文件失败";
	}

	/**
	 * 删除文件
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelMap deletePolicy(@PathVariable("name") String name) {
		try {
			LOGGER.info("--------------delete --name : {} ", name);
			Boolean boo = this.policyService.deletePolicy(name);
			if (boo) {
				return ReturnUtil.Success("删除成功", null, "/console/index");
			}
			return ReturnUtil.Success("文件不存在", null, "/console/policy/index");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());// TODO: handle exception
		}
		return ReturnUtil.Error("删除失败", null, "/console/picture/index");
	}

	/**
	 * 下载json文件 路径为   ip地址/console/policy/file/文件名
	 * @param filename
	 * @return
	 */
	@GetMapping("/file/{name}")
	public ResponseEntity<Resource> serveFile(@PathVariable("name") String filename) {

		Resource file = this.policyService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
}
