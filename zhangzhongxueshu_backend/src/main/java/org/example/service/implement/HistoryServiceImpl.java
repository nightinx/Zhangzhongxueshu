package org.example.service.implement;

import org.example.dao.CommentDOMapper;
import org.example.dao.HistoryDOMapper;
import org.example.dataobj.CommentDO;
import org.example.dataobj.HistoryDO;
import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.service.HistoryService;
import org.example.service.model.CommentModel;
import org.example.service.model.HistoryModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryDOMapper historyDOMapper;

    @Override
    public void insertHistory(HistoryModel historyModel) throws BusinessException {
        if(historyModel ==null){
            System.out.println("userModel ==null");
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }


        //实现model->do方法
        HistoryDO historyDO=convertFromModel(historyModel);
        historyDOMapper.insertSelective(historyDO);
        return;
    }

    private HistoryDO convertFromModel(HistoryModel historyModel){
        if(historyModel==null) {
            return null;
        }

        HistoryDO historyDO=new HistoryDO();
        BeanUtils.copyProperties(historyModel,historyDO);


        return historyDO;
    }
}
