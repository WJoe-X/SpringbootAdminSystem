package com.lenovo.bootstrap.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.mapper.AdminMapper;
import com.lenovo.bootstrap.po.Admin;
import com.lenovo.bootstrap.po.AdminExample;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.AdminService;
import com.lenovo.bootstrap.util.CamelCaseUtil;
import com.lenovo.bootstrap.util.PasswordUtil;
import com.lenovo.bootstrap.util.UuidUtil;

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

	//@Cacheable(cacheNames = "AdminService-getAllList")
	@Override
	public PageInfo<Admin> getAllList(ListVaild listVaild) {
		AdminExample example = new AdminExample();
		 LOGGER.info("从数据库查询 adminAllList ");
			
		example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort()+" "+listVaild.getOrder()));
		PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
       
		List<Admin> list = this.adminMapper.selectByExample(example);
		return new PageInfo<>(list);
	}

	@Override
	//@Cacheable(cacheNames = "AdminService-getCount")
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
