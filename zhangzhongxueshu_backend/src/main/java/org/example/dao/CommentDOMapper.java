package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.example.dataobj.CommentDO;

import java.util.List;

public interface CommentDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentDO record);

    int insertSelective(CommentDO record);

    CommentDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentDO record);

    int updateByPrimaryKeyWithBLOBs(CommentDO record);

    int updateByPrimaryKey(CommentDO record);
    List<CommentDO> getCommentByArticleid(@Param("articleid")String articleid);
}