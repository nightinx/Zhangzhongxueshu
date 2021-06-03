package org.example.service.implement;


import org.apache.commons.lang3.StringUtils;
import org.example.dao.CommentDOMapper;
import org.example.dataobj.CommentDO;
import org.example.dataobj.UserDO;
import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.service.CommentService;
import org.example.service.model.CommentModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServicImpl implements CommentService {


    @Autowired
    private CommentDOMapper commentDOMapper;

    @Override
    public List<CommentModel> getCommentByArticleid(String articleid) {
        List<CommentDO> commentDOList=commentDOMapper.getCommentByArticleid(articleid);
        List<CommentModel> commentModelList=new ArrayList<>();

        for(int i=0;i<commentDOList.size();++i){
            commentModelList.add(convertFromDataObject(commentDOList.get(i)));
        }
        return commentModelList;
    }

    @Override
    public void insertComment(CommentModel commentModel) throws BusinessException {
        if(commentModel ==null){
            System.out.println("userModel ==null");
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }


        //实现model->do方法
        CommentDO commentDO=convertFromModel(commentModel);
        commentDOMapper.insertSelective(commentDO);
        return;
    }

    private CommentModel convertFromDataObject(CommentDO commentDO){
        if(commentDO==null) {
            return null;
        }

        CommentModel commentModel=new CommentModel();
        BeanUtils.copyProperties(commentDO,commentModel);


        return commentModel;
    }

    private CommentDO convertFromModel(CommentModel commentModel){
        if(commentModel==null) {
            return null;
        }

        CommentDO commentDO=new CommentDO();
        BeanUtils.copyProperties(commentModel,commentDO);


        return commentDO;
    }
}
