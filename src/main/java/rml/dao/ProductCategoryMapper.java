package rml.dao;

import rml.model.ProductCategory;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/24.
 */
public interface ProductCategoryMapper {
    List<ProductCategory> getProductCategories(String moduleId);
    List<ProductCategory> getProductPid(String uuid);
    List<ProductCategory> getCategoriesRoot(String moduleId);
    int insertCategory(ProductCategory productCategory);
    List<ProductCategory> getFrontPageCategories();
    List<ProductCategory> getFirstPageCategories();
    List<ProductCategory> getNormalRec();
    List<ProductCategory> getHotRec();
    List<ProductCategory> getSecRec();
    ProductCategory getCategoryUUID(String uuid);
}
