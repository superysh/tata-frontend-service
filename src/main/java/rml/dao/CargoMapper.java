package rml.dao;

import rml.model.Cargo;
import rml.model.Good;

import java.util.List;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public interface CargoMapper {

     int insert(Cargo cargo);

    int insertGood(Cargo cargo);

    Cargo getGood(Cargo cargo);

    int updateCount(Cargo cargo);

    List<Cargo> getUserGood(String userId);

    int updateCountMinus(Cargo cargo);

    int delete(String goodId);

    Cargo getCargo(String uuid);

    void deleteCargo(String uuid);

}
