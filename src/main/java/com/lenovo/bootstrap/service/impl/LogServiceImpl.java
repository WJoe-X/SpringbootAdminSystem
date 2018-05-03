package com.lenovo.bootstrap.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.mapper.LogMapper;
import com.lenovo.bootstrap.po.Log;
import com.lenovo.bootstrap.service.LogService;
import com.lenovo.bootstrap.util.UuidUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午3:56:04
 */

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logmapper;

	@Override
	public int saveLoginLog(String username, String ip, String contextPath) {
		Log log = new Log();
		log.setLogId(UuidUtil.getUUID());
		log.setLogUser(username);
		log.setLogTime(new Date());
		log.setLogIp(ip);
		log.setLogAction(contextPath);
		return this.logmapper.insert(log);
	}

	@Override
	public PageInfo<Log> getPageList(Integer pageNumber, Integer pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<Log> list= this.logmapper.selectByExample(null);
	    return new PageInfo<>(list);
	}

}
