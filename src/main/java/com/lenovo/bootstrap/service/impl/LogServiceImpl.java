package com.lenovo.bootstrap.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.mapper.LogMapper;
import com.lenovo.bootstrap.po.Log;
import com.lenovo.bootstrap.po.LogExample;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.LogService;
import com.lenovo.bootstrap.util.CamelCaseUtil;
import com.lenovo.bootstrap.util.UuidUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午3:56:04
 */

@Service
public class LogServiceImpl implements LogService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(LogServiceImpl.class);

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
	public PageInfo<Log> getPageList(ListVaild listVaild) {
		LogExample example = new LogExample();
		LOGGER.info("listVaild : sort : {} , order  : {}" ,listVaild.getSort() ,listVaild.getOrder());
		example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort()+" "+listVaild.getOrder()));
		PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
		List<Log> list= this.logmapper.selectByExample(example);
	    return new PageInfo<>(list);
	}

}
