package rml.service;

import rml.model.ProductBrandLink;

/**
 * Created by edward-echo on 2016/4/6.
 */
public interface ProductBrandLinkService {
    int insertBrand(ProductBrandLink productBrandLink);
    void deleteBrand(String productId);
}
