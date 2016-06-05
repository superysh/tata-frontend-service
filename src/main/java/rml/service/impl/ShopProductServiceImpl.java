package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ShopProductMapper;
import rml.model.ShopProduct;
import rml.service.ShopProductService;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/19.
 */
@Service("shopProductService")
public class ShopProductServiceImpl implements ShopProductService {

    @Autowired
    private ShopProductMapper shopProductMapper;

    @Override
    public void insert(ShopProduct shopProduct) {
        shopProductMapper.insert(shopProduct);
    }

    @Override
    public List<ShopProduct> getShopProducts(String shopId) {
        return shopProductMapper.getShopProducts(shopId);
    }

    @Override
    public void updateProduct(ShopProduct shopProduct) {
        shopProductMapper.updateProduct(shopProduct);
    }

    @Override
    public void deleteProduct(String uuid) {
        shopProductMapper.deleteProduct(uuid);
    }
}
