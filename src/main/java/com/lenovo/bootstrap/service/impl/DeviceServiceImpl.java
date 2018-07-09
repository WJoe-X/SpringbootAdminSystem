package com.lenovo.bootstrap.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.mapper.DeviceMapper;
import com.lenovo.bootstrap.po.Admin;
import com.lenovo.bootstrap.po.AdminExample;
import com.lenovo.bootstrap.po.Device;
import com.lenovo.bootstrap.po.DeviceExample;
import com.lenovo.bootstrap.po.Role;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.DeviceService;
import com.lenovo.bootstrap.util.CamelCaseUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 2018年7月9日 下午2:53:46
 */

@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@Autowired
	DeviceMapper deviceMapper;

	@Override
	public PageInfo<Device> getDevice(ListVaild listVaild) {
		if (listVaild == null) {
			return null;
		}
		DeviceExample example = new DeviceExample();
		example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort() + " " + listVaild.getOrder()));
		PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
		List<Device> list = this.deviceMapper.selectByExample(example);
		return new PageInfo<>(list);

	}

	@Override
	@Transactional
	public int saveDevice(Device device) {
	
		device.setCreatedAt(new Date());
		device.setUpdatedAt(device.getCreatedAt());
		return this.deviceMapper.insert(device);
	}

	@Override
	public int deleteDevice(int id) {
		if (id <= 0) {
			return 0;
		}
		return this.deviceMapper.deleteByPrimaryKey(id);

	}

}
