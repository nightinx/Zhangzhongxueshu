package org.example.service;

import org.example.service.model.ArticleModel;

import java.util.List;

public interface ArticleService {
    List<ArticleModel> getArticleByTitle(String title);
    List<ArticleModel> getArticleByType(String source, Integer num);
}
