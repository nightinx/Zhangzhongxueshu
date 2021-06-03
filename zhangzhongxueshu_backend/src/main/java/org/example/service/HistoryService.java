package org.example.service;

import org.example.error.BusinessException;
import org.example.service.model.CommentModel;
import org.example.service.model.HistoryModel;

public interface HistoryService {
    void insertHistory(HistoryModel historyModel) throws BusinessException;
}
