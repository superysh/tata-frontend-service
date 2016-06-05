package rml.service;

import rml.model.ProductCategory;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/24.
 */
public interface ProductCategoryService {
    List<ProductCategory> getProductCategories(String moduleId);
    List<ProductCategory> getProductPid(String uuid);
    int insertCategory(ProductCategory productCategory);
    List<ProductCategory> getCategoriesRoot(String moduleId);
    List<ProductCategory> getFrontPageCategories();
    List<ProductCategory> getFirstPageCategories();
    List<ProductCategory> getNormalRec();
    List<ProductCategory> getHotRec();
    List<ProductCategory> getSecRec();
    ProductCategory getCategoryUUID(String uuid);
}
