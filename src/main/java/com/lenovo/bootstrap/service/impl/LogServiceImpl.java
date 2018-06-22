package com.lenovo.bootstrap.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.lenovo.bootstrap.vo.CountPerDayVo;


/**
 * Description:
 * 
 * @author WJoe
 * @time 下午3:56:04
 */

@Service
public class LogServiceImpl implements LogService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(LogServiceImpl.class);
	
	private final static String LOG_DATE = "dateTime";

	private final static String LOG_COUNT_NUMBER = "countNumber";

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

	@Override
	public  Map<String, List<?>> count() {
		Map<String, List<?>> map = new HashMap<>();
		
		List<CountPerDayVo> list =this.logmapper.countPerDay();
		
		List<String> datePerDay = new ArrayList<>();
		List<Integer> countNumber = new ArrayList<>();
		
		for (CountPerDayVo countPerDayVo : list) {
			datePerDay.add(countPerDayVo.getDateTime());
			countNumber.add(countPerDayVo.getCountNumber());
		}
		map.put(LOG_DATE, datePerDay);
		map.put(LOG_COUNT_NUMBER, countNumber);
		return map;
		
	}

	@Override
	public PageInfo<Log> getPageList(@Valid ListVaild listVaild, Log log) {
		LogExample example = new LogExample();
		example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort()+" "+listVaild.getOrder()));
		if(StringUtils.isNotEmpty(log.getLogUser()));
		example.createCriteria().andLogUserEqualTo(log.getLogUser());
		PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
		List<Log> list= this.logmapper.selectByExample(example);
	    return new PageInfo<>(list);
		
		
	}

}
