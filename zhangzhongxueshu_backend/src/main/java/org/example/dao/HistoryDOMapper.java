package org.example.dao;

import org.example.dataobj.HistoryDO;

public interface HistoryDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HistoryDO record);

    int insertSelective(HistoryDO record);

    HistoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HistoryDO record);

    int updateByPrimaryKey(HistoryDO record);
}