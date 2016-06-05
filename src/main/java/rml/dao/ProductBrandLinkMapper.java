package rml.dao;

import rml.model.ProductBrandLink;

/**
 * Created by edward-echo on 2016/4/6.
 */
public interface ProductBrandLinkMapper {
    int insertBrand(ProductBrandLink productBrandLink);
    void deleteBrand(String productId);
}
