package com.lianxiang.mapper;

import com.lianxiang.po.AdminRoleExample;
import com.lianxiang.po.AdminRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminRoleMapper {
    long countByExample(AdminRoleExample example);

    int deleteByExample(AdminRoleExample example);

    int deleteByPrimaryKey(AdminRoleKey key);

    int insert(AdminRoleKey record);

    int insertSelective(AdminRoleKey record);

    List<AdminRoleKey> selectByExample(AdminRoleExample example);

    int updateByExampleSelective(@Param("record") AdminRoleKey record, @Param("example") AdminRoleExample example);

    int updateByExample(@Param("record") AdminRoleKey record, @Param("example") AdminRoleExample example);
}