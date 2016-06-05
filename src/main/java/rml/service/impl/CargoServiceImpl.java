package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CargoMapper;
import rml.dao.UserMapper;
import rml.model.Cargo;
import rml.model.Good;
import rml.model.UserType;
import rml.service.CargoService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
@Service("cargoService")
public class CargoServiceImpl implements CargoService {

    @Autowired
    CargoMapper cargoMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public int insert(Cargo cargo) {
        cargo.setUuid(UUID.randomUUID().toString());
        cargo.setCreateTime(new Date());
        return cargoMapper.insert(cargo);
    }

    @Override
    public int insertGood(Cargo cargo) {
        return cargoMapper.insertGood(cargo);
    }

    @Override
    public Cargo getGood(Cargo cargo) {
        return cargoMapper.getGood(cargo);
    }

    @Override
    public int updateCount(Cargo cargo) {
        return cargoMapper.updateCount(cargo);
    }

    @Override
    public List<Cargo> getUserGood(String userId) {
        return cargoMapper.getUserGood(userId);
    }
    @Override
    public int updateCountMinus(Cargo cargo) {
        return cargoMapper.updateCountMinus(cargo);
    }

    @Override
    public int delete(String goodId) {
        return cargoMapper.delete(goodId);
    }

    @Override
    public Cargo getCargo(String uuid) {
        return cargoMapper.getCargo(uuid);
    }

    @Override
    public void deleteCargo(String uuid) {
        cargoMapper.deleteCargo(uuid);
    }

}
