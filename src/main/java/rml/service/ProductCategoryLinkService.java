package rml.service;

import rml.model.ProductCategoryLink;

/**
 * Created by edward-echo on 2016/4/14.
 */
public interface ProductCategoryLinkService {
    void insertCategory(ProductCategoryLink link);
    void deleteProductCategory(ProductCategoryLink link);
}
