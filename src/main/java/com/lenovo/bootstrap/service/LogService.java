package com.lenovo.bootstrap.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.po.Log;

/**
 * Description:日志记录
 * 
 * @author WJoe
 * @time 下午3:54:52
 */
public interface LogService {

	/**
	 * 记录登陆日志
	 * 
	 * @param username
	 * @param ip
	 * @param contextPath
	 * @return
	 */
	public int saveLoginLog(String username, String ip, String contextPath);
    
	
	public PageInfo<Log> getPageList(Integer pageNumber, Integer pageSize);

}
