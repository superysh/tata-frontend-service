package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ProductBrandLinkMapper;
import rml.model.ProductBrandLink;
import rml.service.ProductBrandLinkService;
import rml.service.ProductBrandService;

/**
 * Created by edward-echo on 2016/4/6.
 */

@Service("productBrandLinkService")
public class ProductBrandLinkServiceImpl implements ProductBrandLinkService {

    @Autowired
    private ProductBrandLinkMapper productBrandLinkMapper;

    @Override
    public int insertBrand(ProductBrandLink productBrandLink) {
        return productBrandLinkMapper.insertBrand(productBrandLink);
    }

    @Override
    public void deleteBrand(String productId) {
        productBrandLinkMapper.deleteBrand(productId);
    }
}
