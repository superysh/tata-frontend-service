package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ProductCategoryLinkMpper;
import rml.model.ProductCategoryLink;
import rml.service.ProductCategoryLinkService;

/**
 * Created by edward-echo on 2016/4/14.
 */

@Service("productCategoryLinkService")
public class ProductCategoryLinkServiceImpl implements ProductCategoryLinkService {

    @Autowired
    private ProductCategoryLinkMpper productCategoryLinkMpper;

    @Override
    public void insertCategory(ProductCategoryLink link) {
        productCategoryLinkMpper.insertCategory(link);
    }

    @Override
    public void deleteProductCategory(ProductCategoryLink link) {
        productCategoryLinkMpper.deleteProductCategory(link);
    }
}
