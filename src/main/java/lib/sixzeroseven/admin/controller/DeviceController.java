package lib.sixzeroseven.admin.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import lib.sixzeroseven.admin.po.Device;
import lib.sixzeroseven.admin.po.valid.ListVaild;
import lib.sixzeroseven.admin.service.DeviceService;
import lib.sixzeroseven.admin.util.ReturnUtil;

/**
 * Description: 设备管理
 * 
 * @author WJoe
 * @time 2018年7月9日 下午2:48:13
 */

@Controller
@RequestMapping("console/device")
public class DeviceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	DeviceService deviceService;

	@GetMapping(value = "list")
	@ResponseBody
	public ModelMap getList(@Valid ListVaild listVaild, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		PageInfo<Device> pageInfo = this.deviceService.getDevice(listVaild);
		LOGGER.info("-------设备总数 ： {} 个---------",pageInfo.getTotal());
		ModelMap map = new ModelMap();
		map.put("pageInfo", pageInfo);
		return ReturnUtil.Success("加载成功", map, null);
	}

	/**
	 * 添加设备
	 * @param device
	 * @param result
	 * @param map
	 * @return
	 */
	@PostMapping()
	@ResponseBody
	public ModelMap addDevice(@Valid Device device, BindingResult result,ModelMap map) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
		int n = this.deviceService.saveDevice(device);
		LOGGER.info("------添加  {} 设备成功---",device.getDeviceName());
		if (n>0) {
			return  ReturnUtil.Success("添加成功", null, null);
		}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());// TODO: handle exception
		}
		return ReturnUtil.Error("添加失败", null, null);
	}
	
	@RequestMapping(value="{id}" ,method= RequestMethod.DELETE)
	@ResponseBody
	public ModelMap deleteDevice(@PathVariable("id") int id){
		try {
		int n = 	this.deviceService.deleteDevice(id);
		if (n>0) {
			return   ReturnUtil.Success("删除成功", null, "/console/device/index");
		}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return ReturnUtil.Error("删除失败", null, null);
	}
}
