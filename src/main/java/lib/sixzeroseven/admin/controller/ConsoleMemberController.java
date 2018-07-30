package lib.sixzeroseven.admin.controller;

import javax.validation.Valid;

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

import lib.sixzeroseven.admin.po.Member;
import lib.sixzeroseven.admin.po.valid.ListVaild;
import lib.sixzeroseven.admin.service.MemberService;
import lib.sixzeroseven.admin.util.ReturnUtil;

/**
 * Description:后台会员管理
 * 
 * @author WJoe
 * @time 2018年5月7日 上午10:36:04
 */

@Controller
@RequestMapping(value = "console/member")
public class ConsoleMemberController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleMemberController.class);

	@Autowired
	private MemberService memberService;

	@RequiresPermissions("member:index")
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		return "console/member/index";
	}

	@RequiresPermissions("member:index")
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap list(@Valid ListVaild listValid, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		LOGGER.info(" member : list , pageNumber : {} , pageSize : {} , sort : {} , order {} ",
				listValid.getPageNumber(), listValid.getPageSize(), listValid.getSort(), listValid.getOrder());
		ModelMap map = new ModelMap();
		PageInfo<Member> pageInfo = memberService.findAllList(listValid);
		map.put("pageInfo", pageInfo);

		return ReturnUtil.Success("加载成功", map, null);
	}

}
