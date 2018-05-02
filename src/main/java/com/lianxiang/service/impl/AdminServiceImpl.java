package com.lianxiang.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lianxiang.mapper.AdminMapper;
import com.lianxiang.po.Admin;
import com.lianxiang.po.AdminExample;
import com.lianxiang.service.AdminService;
import com.lianxiang.util.PasswordUtil;
import com.lianxiang.util.ReturnUtil;
import com.lianxiang.util.UuidUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 下午4:16:36
 */

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public PageInfo<Admin> getAllList(Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		List<Admin> list = this.adminMapper.selectByExample(null);
		return new PageInfo<>(list);
	}

	@Override
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
	public int deleteById(String id) {
		return this.adminMapper.deleteByPrimaryKey(id);

	}

	@Override
	@Transactional
	public int save(Admin admin) {
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
	public void saveByExample(Admin admin) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Admin> getPageList(Admin admin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public int updatePwd(String uid, String password) {
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
	public int updateById(Admin updateAdmin) {
		updateAdmin.setUpdatedAt(new Date());
		return this.adminMapper.updateByPrimaryKey(updateAdmin);
	}

}
