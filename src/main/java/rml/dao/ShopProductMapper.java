package rml.dao;

import rml.model.Product;
import rml.model.ShopProduct;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/19.
 */
public interface ShopProductMapper {
    public void insert(ShopProduct shopProduct);
    List<ShopProduct> getShopProducts(String shopId);
    void updateProduct(ShopProduct shopProduct);
    void deleteProduct(String uuid);
}
