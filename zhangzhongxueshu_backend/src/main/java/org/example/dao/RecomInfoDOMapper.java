package org.example.dao;

import org.example.dataobj.RecomInfoDO;

public interface RecomInfoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecomInfoDO record);

    int insertSelective(RecomInfoDO record);

    RecomInfoDO selectByPrimaryKey(Integer id);

    RecomInfoDO selectByUserId(Integer user_id);

    int updateByPrimaryKeySelective(RecomInfoDO record);

    int updateByPrimaryKey(RecomInfoDO record);
}