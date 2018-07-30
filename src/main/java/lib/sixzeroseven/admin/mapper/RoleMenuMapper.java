package lib.sixzeroseven.admin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import lib.sixzeroseven.admin.po.RoleMenuExample;
import lib.sixzeroseven.admin.po.RoleMenuKey;

public interface RoleMenuMapper {
    long countByExample(RoleMenuExample example);

    int deleteByExample(RoleMenuExample example);

    int deleteByPrimaryKey(RoleMenuKey key);

    int insert(RoleMenuKey record);

    int insertSelective(RoleMenuKey record);

    List<RoleMenuKey> selectByExample(RoleMenuExample example);

    int updateByExampleSelective(@Param("record") RoleMenuKey record, @Param("example") RoleMenuExample example);

    int updateByExample(@Param("record") RoleMenuKey record, @Param("example") RoleMenuExample example);
}