package rml.service;

import rml.model.Good;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */
public interface GoodService {

    int insert(Good good);

    List<Good> getActivityGoods(String uuids);

    Good getGoodByUUID(String uuid);
}
