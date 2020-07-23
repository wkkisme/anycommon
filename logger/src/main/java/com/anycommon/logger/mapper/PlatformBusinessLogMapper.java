package com.anycommon.logger.mapper;

import com.anycommon.logger.domain.PlatformBusinessLog;
import com.anycommon.logger.domain.PlatformBusinessLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatformBusinessLogMapper {
    long countByExample(PlatformBusinessLogExample example);

    int deleteByExample(PlatformBusinessLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlatformBusinessLog record);

    int insertSelective(PlatformBusinessLog record);

    List<PlatformBusinessLog> selectByExample(PlatformBusinessLogExample example);

    PlatformBusinessLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlatformBusinessLog record, @Param("example") PlatformBusinessLogExample example);

    int updateByExample(@Param("record") PlatformBusinessLog record, @Param("example") PlatformBusinessLogExample example);

    int updateByPrimaryKeySelective(PlatformBusinessLog record);

    int updateByPrimaryKey(PlatformBusinessLog record);
}