package com.lianxiang.service.impl;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lianxiang.mapper.MemberMapper;
import com.lianxiang.po.Admin;
import com.lianxiang.po.Member;
import com.lianxiang.po.MemberExample;
import com.lianxiang.service.MemberService;

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
		return this.memberMapper.insert(member);
	}

}
