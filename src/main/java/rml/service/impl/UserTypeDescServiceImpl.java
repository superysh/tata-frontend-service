package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.UserMapper;
import rml.model.UserTypeDesc;
import rml.service.UserTypeDescService;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7 0007.
 */

@Service("userTypeDescService")
public class UserTypeDescServiceImpl implements UserTypeDescService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserTypeDesc> getUserTypes() {
        return userMapper.getUserTypes();
    }
}
