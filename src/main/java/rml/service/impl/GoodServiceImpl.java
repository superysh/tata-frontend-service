package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.GoodMapper;
import rml.model.Good;
import rml.service.GoodService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by edward-echo on 2015/12/20.
 */

@Service("goodService")
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public int insert(Good good) {
        good.setUuid(UUID.randomUUID().toString());
        good.setCreateTime(new Date());
        return goodMapper.insert(good);
    }

    @Override
    public List<Good> getActivityGoods(String uuids) {
        List<Good> results = goodMapper.getActivityGoods(uuids);
        return results;
    }

    @Override
    public Good getGoodByUUID(String uuid) {
        return goodMapper.getGoodByUUID(uuid);
    }
}
