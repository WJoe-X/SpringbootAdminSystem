package lib.sixzeroseven.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lib.sixzeroseven.admin.mapper.RoleMapper;
import lib.sixzeroseven.admin.po.Role;
import lib.sixzeroseven.admin.po.RoleExample;
import lib.sixzeroseven.admin.po.valid.ListVaild;
import lib.sixzeroseven.admin.service.RoleService;
import lib.sixzeroseven.admin.util.CamelCaseUtil;
import lib.sixzeroseven.admin.util.UuidUtil;

/**
 * Description:
 * 
 * @author WJoe
 * @time 上午9:45:05
 */

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Integer getCount(RoleExample example) {
		Long count = this.roleMapper.countByExample(example);
		return count.intValue();
	}

	@Override
	public List<Role> findRoleListByAdminId(String uid) {
		return roleMapper.selectRoleListByAdminId(uid);
	}

	@Override
	public List<Role> getFromAll() {
		RoleExample example = new RoleExample();
		example.createCriteria().andEnableEqualTo((byte) 1);
		return this.roleMapper.selectByExample(example);
	}



	@Override
	public PageInfo<Role> getPageList(ListVaild listVaild) {
		RoleExample example = new RoleExample();
		
		example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort()+" "+listVaild.getOrder()));
		PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
		List<Role> list= this.roleMapper.selectByExample(example);
	    return new PageInfo<>(list);
		
		
	}

	@Override
	public Role findById(String roleId) {
		return this.roleMapper.selectByPrimaryKey(roleId);
		
		
	}

	@Override
	@Transactional
	public int save(Role role) {
		 if (StringUtils.isEmpty(role.getRoleId())) {
             role.setRoleId(UuidUtil.getUUID());
             role.setCreatedAt(new Date());
             role.setUpdatedAt(new Date());
             return this.roleMapper.insert(role);
         } else {
             role.setUpdatedAt(new Date());
             return this.roleMapper.insertSelective(role);
         }
		
		
	}

	@Override
	public int deleteById(String roleId) {
		return this.roleMapper.deleteByPrimaryKey(roleId);
		
	}

}
