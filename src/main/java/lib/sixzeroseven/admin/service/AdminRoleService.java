package lib.sixzeroseven.admin.service;

import java.util.List;

import lib.sixzeroseven.admin.po.AdminRoleKey;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午2:51:17
 */
public interface AdminRoleService {

	public List<AdminRoleKey> getRoleList(AdminRoleKey adminRole);

	public int deleteByAdminId(String uid);

	public int save(AdminRoleKey adminRole);

	public int deleteByRoleId(String id);

}
