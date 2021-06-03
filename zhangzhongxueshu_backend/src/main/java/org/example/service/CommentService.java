package org.example.service;

import org.example.error.BusinessException;

import org.example.service.model.CommentModel;

import java.util.List;

public interface CommentService {
    List<CommentModel> getCommentByArticleid(String articleid);
    void insertComment(CommentModel commentModel) throws BusinessException;
}
