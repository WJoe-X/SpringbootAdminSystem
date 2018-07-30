package lib.sixzeroseven.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lib.sixzeroseven.admin.mapper.AdminMapper;
import lib.sixzeroseven.admin.mapper.RoleMapper;
import lib.sixzeroseven.admin.po.Admin;
import lib.sixzeroseven.admin.po.AdminExample;
import lib.sixzeroseven.admin.po.Role;
import lib.sixzeroseven.admin.po.valid.ListVaild;
import lib.sixzeroseven.admin.service.AdminService;
import lib.sixzeroseven.admin.util.CamelCaseUtil;
import lib.sixzeroseven.admin.util.PasswordUtil;
import lib.sixzeroseven.admin.util.UuidUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:16:36
 */

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	// @Cacheable(cacheNames = "AdminService-getAllList")
	@Override
	public PageInfo<Admin> getAllList(ListVaild listVaild) {
		AdminExample example = new AdminExample();
		LOGGER.info("从数据库查询 adminAllList ");

		example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort() + " " + listVaild.getOrder()));
		PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
		List<Admin> list = this.adminMapper.selectByExample(example);
		for (Admin admin : list) {
			List<Role> rolelist = roleMapper.selectRoleListByAdminId(admin.getUid());
			admin.setRoleList(rolelist);
		}
		
		return new PageInfo<>(list);
	}

	@Override
	// @Cacheable(cacheNames = "AdminService-getCount")
	public Integer getCount(AdminExample example) {
		Long count = this.adminMapper.countByExample(example);

		return count.intValue();

	}

	@Override
	public Admin getById(String id) {
		return this.adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public Admin findByUsername(String username) {
		AdminExample example = new AdminExample();
		example.or().andUsernameEqualTo(username);
		List<Admin> list = adminMapper.selectByExample(example);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	@Override
	@Transactional
	public Integer deleteById(String id) {
		return this.adminMapper.deleteByPrimaryKey(id);

	}

	@Override
	@Transactional
	public Integer save(Admin admin) {
		if (StringUtils.isEmpty(admin.getPassword())) {
			return 0;
		}
		String Id = UuidUtil.getUUID();
		admin.setUid(Id);
		String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
		admin.setSalt(salt);
		String password = PasswordUtil.createAdminPwd(admin.getPassword(), admin.getUsername() + admin.getSalt());
		admin.setPassword(password);
		admin.setCreatedAt(new Date());
		admin.setUpdatedAt(new Date());
		return this.adminMapper.insert(admin);

	}


	@Override
	@Transactional
	public Integer updatePwd(String uid, String password) {
		Admin admin = new Admin();
		if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(password)) {
			admin = adminMapper.selectByPrimaryKey(uid);
		}
		if (null != admin) {
			String newPassword = PasswordUtil.createAdminPwd(password, admin.getUsername() + admin.getSalt());
			admin.setPassword(newPassword);
			return this.adminMapper.updateByPrimaryKeySelective(admin);

		}
		return 0;
	}

	@Override
	@Transactional
	public Integer updateById(Admin updateAdmin) {
		updateAdmin.setUpdatedAt(new Date());
		return this.adminMapper.updateByPrimaryKey(updateAdmin);
	}

	@Override
	public Integer getCountByUsername(String username) {
		AdminExample example = new AdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		Long s = this.adminMapper.countByExample(example);

		return s.intValue();
	}

}
