package rml.dao;

import rml.model.ProductCategoryLink;

/**
 * Created by edward-echo on 2016/4/14.
 */
public interface ProductCategoryLinkMpper {
    void insertCategory(ProductCategoryLink  link);
    void deleteProductCategory(ProductCategoryLink link);
}
