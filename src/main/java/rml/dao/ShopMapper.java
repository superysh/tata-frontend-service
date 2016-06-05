package rml.dao;

import rml.model.Shop;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/28.
 */
public interface ShopMapper {
    int insert(Shop shop);

    Shop getByMobile(String mobile);

    Shop getShop(Shop shop);

    void updatePassword(Shop shop);

    Shop getShopById(String uuid);

    void updateMoney(Shop shop);

    List<Shop> getShops(Shop shop);

    void updateStatus(Shop shop);

    int getShopTotal();

    void updateType(Shop shop);
}
