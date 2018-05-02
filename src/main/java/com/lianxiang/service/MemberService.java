package com.lianxiang.service;

import javax.validation.Valid;

import com.lianxiang.po.Member;

/**
 * Description:用户业务接口
 * 
 * @author WJoe
 * @time 下午3:22:19
 */
public interface MemberService {

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public Member findByUsername(String username);

	/**
	 * 根据用户名查询是否有用户
	 * @param account
	 * @return
	 */
	public Integer getCountByAccount(String account);

	public Integer save( Member member);

}
