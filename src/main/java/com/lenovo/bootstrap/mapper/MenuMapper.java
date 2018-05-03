package com.lenovo.bootstrap.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.lenovo.bootstrap.po.Menu;
import com.lenovo.bootstrap.po.MenuExample;

public interface MenuMapper {
	long countByExample(MenuExample example);

	int deleteByExample(MenuExample example);

	int deleteByPrimaryKey(String menuId);

	int insert(Menu record);

	int insertSelective(Menu record);

	List<Menu> selectByExample(MenuExample example);

	Menu selectByPrimaryKey(String menuId);

	int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

	int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

	int updateByPrimaryKeySelective(Menu record);

	int updateByPrimaryKey(Menu record);

	Set<String> findMenuCodeByUserId(String userId);

	List<Menu> selectMenuByAdminId(String userId);

	List<Menu> selectAllMenu();

	List<Menu> selectMenuByRoleId(String roleId);
}