package com.lenovo.bootstrap.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.lenovo.bootstrap.po.Member;
import com.lenovo.bootstrap.po.MemberExample;
import com.lenovo.bootstrap.po.MemberKey;

public interface MemberMapper {
    long countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(MemberKey key);

    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(MemberKey key);

    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}