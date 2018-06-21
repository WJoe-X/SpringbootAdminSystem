package com.lenovo.bootstrap.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.po.Picture;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.PictureService;
import com.lenovo.bootstrap.util.ReturnUtil;

/**
 * Description:图片管理
 * 
 * @author WJoe
 * @time 2018年5月14日 上午10:35:43
 */

@Controller
@RequestMapping("console/picture")
public class PictureController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PictureController.class);

	@Autowired
	PictureService pictureService;

	/**
	 * 返回视图
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		return "console/picture/index";
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap list(@Valid ListVaild listVaild, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		ModelMap map = new ModelMap();
		PageInfo<Picture> pageInfo = this.pictureService.getAllList(listVaild);
		map.put("pageInfo", pageInfo);
		LOGGER.info("picture list pageInfo : {} ", pageInfo.toString());
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequestMapping(value = "/from", method = { RequestMethod.GET })
	public String from(Picture picture, Model model) {
		if (!StringUtils.isEmpty(picture.getId())) {
			picture = this.pictureService.findById(picture.getId());
			model.addAttribute("picture", picture);
			return "/console/picture/modify";
		}
		model.addAttribute("picture", picture);
		return "/console/picture/upload";
	}

	/**
	 * 修改图片
	 * 
	 * @param file
	 * @param id
	 * @return
	 */
	@PostMapping("/modify")
	@ResponseBody
	public ModelMap modify(@RequestParam("image") MultipartFile file, @RequestParam("id") Integer id) {
		try {
			pictureService.modify(id, StringUtils.cleanPath(file.getOriginalFilename()));
			pictureService.store(file);
			return ReturnUtil.Success("修改成功", null, "/console/picture/index");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());// TODO: handle exception
		}
		return ReturnUtil.Error("修改失败", null, null);

	}

	/**
	 * 上传图片
	 * 
	 * @param file
	 * @param title
	 * @param username
	 * @return
	 */
	@PostMapping("/upload")
	@ResponseBody
	public ModelMap addPic(@RequestParam("image") MultipartFile file, @RequestParam("title") String title,
			@SessionAttribute("username") String username) {
		try {
			Picture picture = new Picture();
			picture.setOriginPicUrl(StringUtils.cleanPath(file.getOriginalFilename()));
			picture.setTitle(title);
			picture.setUploaderName(username);
			// 保存到数据库
			pictureService.save(picture);
			// 保存到文件夹
			pictureService.store(file);
			return ReturnUtil.Success("上传成功", null, "/console/picture/index");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());// TODO: handle exception
		}

		return ReturnUtil.Error("上传失败", null, null);

	}

	@GetMapping("/file/{name}")
	public ResponseEntity<Resource> serveFile(@PathVariable("name") String filename) {

		Resource file = this.pictureService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ModelMap delete(@PathVariable("id") Integer id) {
		try {
			Picture picture = this.pictureService.findById(id);
			this.pictureService.deleteFromDB(id);
			boolean boo = this.pictureService.deleteFromFile(picture.getOriginPicUrl());
			if (boo) {
				return ReturnUtil.Success("删除成功", null, "/console/index");
			} else {
				return ReturnUtil.Success("图片不存在", null, "/console/picture/index");
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return ReturnUtil.Error("删除失败", null, "/console/picture/index");
	}

	/**
	 * 图片详情页
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public ModelAndView getDetail(@PathVariable("id") Integer id, Model model) {

		Picture picture = this.pictureService.findById(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("picture", picture);
		mv.setViewName("/console/picture/detail");
		return mv;
	}

}
