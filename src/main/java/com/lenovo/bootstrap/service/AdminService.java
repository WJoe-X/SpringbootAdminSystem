package com.lenovo.bootstrap.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.po.Admin;
import com.lenovo.bootstrap.po.AdminExample;
import com.lenovo.bootstrap.po.valid.ListVaild;


/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:16:02
 */
public interface AdminService {
	public PageInfo<Admin> getAllList(ListVaild listVaild);

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

	public Integer getCountByUsername(String username);
	
	
}
