package lib.sixzeroseven.admin.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;

import lib.sixzeroseven.admin.po.Log;
import lib.sixzeroseven.admin.po.valid.ListVaild;

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

	/**
	 * 获取当前页的登陆记录
	 * 
	 * @param listVaild
	 * @return
	 */
	public PageInfo<Log> getPageList(ListVaild listVaild);

	/**
	 * 统计每天的登录数
	 * 
	 * @return
	 */
	public Map<String, List<?>> count();

	/**
	 * 有查询条件的登录记录,目前是查询条件为登录用户
	 * 
	 * @param listVaild
	 * @param log
	 * @return
	 */
	public PageInfo<Log> getPageList(@Valid ListVaild listVaild, Log log);

}
