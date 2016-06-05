package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.UserMapper;
import rml.model.UserType;
import rml.service.UserTypeService;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
@Service("userTypeService")
public class UserTypeServiceImpl implements UserTypeService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insertUserType(UserType userType) {
        userType.setUuid(UUID.randomUUID().toString());
        userType.setCreateTime(new Date());
        return userMapper.insertUserType(userType);
    }
}
