package com.lianxiang.service;

import java.util.List;
import java.util.Set;

import com.lianxiang.po.Menu;
import com.lianxiang.po.MenuExample;

/**
*Description:
*@author WJoe
*@time 下午3:43:24
*/
public interface MenuService {

	Set<String> getAllMenuCode();

	Set<String> findMenuCodeByUserId(String uid);

	List<Menu> findAll();

	List<Menu> findByAdminId(String uid);

	Integer getCount(MenuExample example);

}
