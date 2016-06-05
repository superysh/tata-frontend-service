package rml.service;

import rml.model.UserAddress;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/22.
 */
public interface UserAddressService {

    int insert(UserAddress userAddress);

    List<UserAddress> getUserAddress(String userId);

    int setDefalutUnique(String uuid);

    int setDefalutZero(String userId);

    int update(UserAddress userAddress);

    UserAddress getAddressById(String uuid);

    int deleteAddress(String uuid);

    UserAddress getDefaultAddress(String uuid);

}
