package com.lenovo.bootstrap.controller;

import java.util.ArrayList;
import java.util.List;

import org.mockito.internal.invocation.RealMethod.FromBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lenovo.bootstrap.service.PolicyService;
import com.lenovo.bootstrap.util.ReturnUtil;
import com.lenovo.bootstrap.vo.PolicyPropertyVo;

import net.sf.jsqlparser.statement.create.table.Index;

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
	public List<PolicyPropertyVo> list() {
		List<PolicyPropertyVo> list = new ArrayList<>();
		try {
			list = this.policyService.getPolicyProperty();
			return list;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	@PostMapping()
	@ResponseBody
	public ModelMap save(String json){
		try {
			LOGGER.info("-----------策略     {}--", json);
			 //String str = "{'name':'75','shoppingCartItemList':[{'id':'407','num':'10'}]}";  
			Boolean boo =this.policyService.savePolicyToJson(json);
			if (boo) {
				return ReturnUtil.Success("添加策略成功");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());// TODO: handle exception
		}
		return ReturnUtil.Error("添加策略失败");
	}
}
