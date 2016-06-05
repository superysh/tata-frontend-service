package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ShopMapper;
import rml.model.Shop;
import rml.service.ShopService;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/28.
 */

@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public int getShopTotal() {
        return shopMapper.getShopTotal();
    }

    @Override
    public void updateType(Shop shop) {
        shopMapper.updateType(shop);
    }

    @Override
    public List<Shop> getShops(Shop shop) {
        return shopMapper.getShops(shop);
    }

    @Override
    public void updateStatus(Shop shop) {
        shopMapper.updateStatus(shop);
    }

    @Override
    public int insert(Shop shop) {
        return shopMapper.insert(shop);
    }

    @Override
    public Shop getByMobile(String mobile) {
        return shopMapper.getByMobile(mobile);
    }

    @Override
    public Shop getShop(Shop shop) {
        return shopMapper.getShop(shop);
    }

    @Override
    public void updatePassword(Shop shop) {
        shopMapper.updatePassword(shop);
    }

    @Override
    public Shop getShopById(String uuid) {
        return shopMapper.getShopById(uuid);
    }

    @Override
    public void updateMoney(Shop shop) {
        shopMapper.updateMoney(shop);
    }
}
