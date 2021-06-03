package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.example.dataobj.ArticleDO;
import org.example.service.model.ArticleModel;

import java.util.List;

public interface ArticleDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleDO record);

    int insertSelective(ArticleDO record);

    ArticleDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleDO record);

    int updateByPrimaryKeyWithBLOBs(ArticleDO record);

    int updateByPrimaryKey(ArticleDO record);

    List<ArticleDO> selectByTitle(String title);
    List<ArticleDO> getArticleByType(@Param("source")String source);
}