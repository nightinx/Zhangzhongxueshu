package org.example.service.implement;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.RecomInfoDOMapper;
import org.example.dao.UserDOMapper;
import org.example.dao.UserPasswordDOMapper;
import org.example.dataobj.RecomInfoDO;
import org.example.dataobj.UserDO;
import org.example.dataobj.UserPasswordDO;
import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.service.UserService;
import org.example.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private RecomInfoDOMapper recomInfoDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        //调用userdomapper获取到对应的用户dataobject
        UserDO userDO=userDOMapper.selectByPrimaryKey(id);

        if(userDO==null){
            return null;
        }

        //通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO=userPasswordDOMapper.selectByUserId(userDO.getId());
        //通过用户id获取对应的用户推荐信息
        RecomInfoDO recomInfoDO=recomInfoDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO,recomInfoDO);

    }

    @Override
    public UserModel getUserByEmail(String email) {
        //调用userdomapper获取到对应的用户dataobject

        UserDO userDO=userDOMapper.selectByEmail(email);
        if(userDO==null){
            return null;
        }

        //通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO=userPasswordDOMapper.selectByUserId(userDO.getId());
        //System.out.println("1 yes");
        //通过用户id获取对应的用户推荐信息
        RecomInfoDO recomInfoDO=recomInfoDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO,recomInfoDO);

    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel ==null){
            System.out.println("userModel ==null");
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(!StringUtils.isNotEmpty(userModel.getName())){
            System.out.println("!StringUtils.isNotEmpty(userModel.getName())");
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //实现model->do方法
        UserDO userDO=convertFromModel(userModel);

        try{
            userDOMapper.insertSelective(userDO);
            //System.out.println("insert user_info end");
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"邮箱已重复");
        }

        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO=convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        RecomInfoDO recomInfoDO=convertRecomInfoFromModel(userModel);
        recomInfoDOMapper.insertSelective(recomInfoDO);

        return;
    }

    @Override
    @Transactional
    public void update_recom(UserModel userModel) throws BusinessException {
        if(userModel ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(userModel.getId() ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(!StringUtils.isNotEmpty(userModel.getInterest_1())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(!StringUtils.isNotEmpty(userModel.getInterest_2())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(!StringUtils.isNotEmpty(userModel.getInterest_3())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(!StringUtils.isNotEmpty(userModel.getSchool())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //实现model->do方法
        RecomInfoDO recomInfoDO=convertRecomInfoFromModel(userModel);
        recomInfoDO.setId(recomInfoDOMapper.selectByUserId(recomInfoDO.getUserId()).getId());
        recomInfoDOMapper.updateByPrimaryKeySelective(recomInfoDO);

        return;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }

        UserPasswordDO userPasswordDO=new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    private RecomInfoDO convertRecomInfoFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }

        RecomInfoDO recomInfoDO=new RecomInfoDO();
        recomInfoDO.setInterest1(userModel.getInterest_1());
        recomInfoDO.setInterest2(userModel.getInterest_2());
        recomInfoDO.setInterest3(userModel.getInterest_3());
        recomInfoDO.setSchool(userModel.getSchool());
        recomInfoDO.setUserId(userModel.getId());
        return recomInfoDO;
    }

    private UserDO convertFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserDO userDO=new UserDO();
        BeanUtils.copyProperties(userModel,userDO);

        return userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO,RecomInfoDO recomInfoDO){
        if(userDO==null){
            return null;
        }
        //System.out.println("1 yes");
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        if(recomInfoDO!=null){
            userModel.setSchool(recomInfoDO.getSchool());
            userModel.setInterest_1(recomInfoDO.getInterest1());
            userModel.setInterest_2(recomInfoDO.getInterest2());
            userModel.setInterest_3(recomInfoDO.getInterest3());
        }


        return userModel;
    }
}
