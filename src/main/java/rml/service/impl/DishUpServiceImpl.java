package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.DishUpMapper;
import rml.model.DishUp;
import rml.service.DishUpService;

/**
 * Created by edward on 2016/1/5 0005.
 */

@Service("dishUpService")
public class DishUpServiceImpl implements DishUpService {

    @Autowired
    private DishUpMapper dishUpMapper;

    @Override
    public int insert(DishUp dishUp) {
        return dishUpMapper.insert(dishUp);
    }
}
