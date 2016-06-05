package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.TtUserMapper;
import rml.model.Tt_User;
import rml.service.TtUserService;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/3.
 */

@Service("ttUserService")
public class TtUserServiceImpl implements TtUserService {

    @Autowired
    private TtUserMapper ttUserMapper;

    @Override
    public List<Tt_User> getAll() {
        return ttUserMapper.getAll();
    }
}
