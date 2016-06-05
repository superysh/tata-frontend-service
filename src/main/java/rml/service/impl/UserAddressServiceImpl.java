package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.UserAddressMapper;
import rml.model.UserAddress;
import rml.service.UserAddressService;

import java.util.List;
import java.util.UUID;

/**
 * Created by edward-echo on 2015/12/22.
 */

@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public int insert(UserAddress userAddress) {
        userAddress.setUuid(UUID.randomUUID().toString());
        return userAddressMapper.insert(userAddress);
    }

    @Override
    public List<UserAddress> getUserAddress(String userId) {
        return userAddressMapper.getUserAddress(userId);
    }

    @Override
    public int setDefalutUnique(String uuid) {
        return userAddressMapper.setDefalutUnique(uuid);
    }

    @Override
    public int setDefalutZero(String userId) {
        return userAddressMapper.setDefalutZero(userId);
    }

    @Override
    public int update(UserAddress userAddress) {
        return userAddressMapper.update(userAddress);
    }

    @Override
    public UserAddress getAddressById(String uuid) {
        return userAddressMapper.getAddressById(uuid);
    }

    @Override
    public int deleteAddress(String uuid) {
        return userAddressMapper.deleteAddress(uuid);
    }

    @Override
    public UserAddress getDefaultAddress(String uuid) {
        return userAddressMapper.getDefaultAddress(uuid);
    }
}
