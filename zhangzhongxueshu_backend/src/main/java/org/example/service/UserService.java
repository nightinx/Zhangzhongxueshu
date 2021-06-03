package org.example.service;

import org.example.dataobj.RecomInfoDO;
import org.example.error.BusinessException;
import org.example.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    void update_recom(UserModel userModel) throws BusinessException;
    UserModel getUserByEmail(String email);
}
