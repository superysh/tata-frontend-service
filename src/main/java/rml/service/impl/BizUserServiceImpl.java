package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.BizUserMapper;
import rml.model.BizUser;
import rml.service.BizUserService;
import rml.util.MD5;

/**
 * Created by edward-echo on 2016/5/18.
 */

@Service("bizUserService")
public class BizUserServiceImpl implements BizUserService {

    @Autowired
    BizUserMapper bizUserMapper;

    @Override
    public BizUser getUser(BizUser bizUser) {
        return bizUserMapper.getUser(bizUser);
    }

    public static  void main(String[]args){
        System.err.println(MD5.GetMD5Code("123456"));
    }
}
