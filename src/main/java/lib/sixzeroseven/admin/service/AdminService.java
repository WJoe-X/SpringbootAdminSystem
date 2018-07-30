package lib.sixzeroseven.admin.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import lib.sixzeroseven.admin.po.Admin;
import lib.sixzeroseven.admin.po.AdminExample;
import lib.sixzeroseven.admin.po.valid.ListVaild;


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

	public Integer deleteById(String id);

	/**
	 * 创建新用户
	 * @param admin
	 * @return
	 */
	public Integer save(Admin admin);

	public Integer updatePwd(String uid, String newPassword);

	/**
	 * 更新用户资料
	 * @param updateAdmin
	 * @return
	 */
	public Integer updateById(Admin updateAdmin);

	public Integer getCountByUsername(String username);
	
	
}
