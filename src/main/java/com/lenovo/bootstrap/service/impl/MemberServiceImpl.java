package com.lenovo.bootstrap.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.bootstrap.mapper.MemberMapper;
import com.lenovo.bootstrap.po.Member;
import com.lenovo.bootstrap.po.MemberExample;
import com.lenovo.bootstrap.po.valid.ListVaild;
import com.lenovo.bootstrap.service.MemberService;
import com.lenovo.bootstrap.util.CamelCaseUtil;
import com.lenovo.bootstrap.util.PasswordUtil;
import com.lenovo.bootstrap.util.UuidUtil;

/**
 * Description:用户业务实现类
 * 
 * @author WJoe
 * @time 下午3:24:15
 */

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public Member findByUsername(String username) {
		MemberExample example = new MemberExample();
		example.createCriteria().andAccountEqualTo(username);
		List<Member> list= this.memberMapper.selectByExample(example);
		return (list==null||list.size()==0)?null:list.get(0);
	}

	@Override
	public Integer getCountByAccount(String account) {
		MemberExample example = new MemberExample();
		example.createCriteria().andAccountEqualTo(account);
		List<Member>  list= this.memberMapper.selectByExample(example);
	    return list==null?0:list.size();
	}

	@Override
	public Integer save(Member member) {
		String Id = UuidUtil.getUUID();
		member.setUid(Id);
		String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
		member.setSalt(salt);
		String password = PasswordUtil.createCustomPwd(member.getPassword(), member.getSalt());
		member.setPassword(password);
		member.setState((byte) 1);
		member.setCreatedAt(new Date());
		member.setUpdatedAt(new Date());
		return this.memberMapper.insert(member);
	}

	@Override
	public PageInfo<Member> findAllList(ListVaild listVaild) {
		MemberExample example = new MemberExample();
	
	example.setOrderByClause(CamelCaseUtil.toUnderlineName(listVaild.getSort()+" "+listVaild.getOrder()));
	PageHelper.startPage(listVaild.getPageNumber(), listVaild.getPageSize());
	List<Member> list = this.memberMapper.selectByExample(example);
	return new PageInfo<>(list);
		
	}

}
