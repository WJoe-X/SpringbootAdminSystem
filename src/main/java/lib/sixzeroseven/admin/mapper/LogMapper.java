package lib.sixzeroseven.admin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import lib.sixzeroseven.admin.po.Log;
import lib.sixzeroseven.admin.po.LogExample;
import lib.sixzeroseven.admin.vo.CountPerDayVo;

public interface LogMapper {
    long countByExample(LogExample example);

    int deleteByExample(LogExample example);

    int deleteByPrimaryKey(String logId);

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> selectByExample(LogExample example);

    Log selectByPrimaryKey(String logId);

    int updateByExampleSelective(@Param("record") Log record, @Param("example") LogExample example);

    int updateByExample(@Param("record") Log record, @Param("example") LogExample example);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
    
    List<CountPerDayVo> countPerDay();
}