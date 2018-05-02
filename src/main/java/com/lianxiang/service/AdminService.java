package com.lianxiang.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lianxiang.po.Admin;
import com.lianxiang.po.AdminExample;


/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:16:02
 */
public interface AdminService {
	public PageInfo<Admin> getAllList(Integer pageNum,Integer pageSize);

	public Integer getCount(AdminExample example);

	public Admin getById(String id);

	public Admin findByUsername(String username);

	public int deleteById(String id);

	/**
	 * 创建新用户
	 * @param admin
	 * @return
	 */
	public int save(Admin admin);

	public void saveByExample(Admin admin);

	public List<Admin> getPageList(Admin admin);

	public int updatePwd(String uid, String newPassword);

	/**
	 * 更新用户资料
	 * @param updateAdmin
	 * @return
	 */
	public int updateById(Admin updateAdmin);
	
	
}
