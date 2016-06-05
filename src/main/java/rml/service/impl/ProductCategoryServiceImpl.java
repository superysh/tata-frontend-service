package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ProductCategoryMapper;
import rml.model.ProductCategory;
import rml.service.ProductCategoryService;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/24.
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> getProductCategories(String moduleId) {
        return productCategoryMapper.getProductCategories(moduleId);
    }

    @Override
    public List<ProductCategory> getProductPid(String uuid) {
        return productCategoryMapper.getProductPid(uuid);
    }

    @Override
    public int insertCategory(ProductCategory productCategory) {
        return productCategoryMapper.insertCategory(productCategory);
    }

    @Override
    public List<ProductCategory> getCategoriesRoot(String moduleId) {
        return productCategoryMapper.getCategoriesRoot(moduleId);
    }

    @Override
    public List<ProductCategory> getFrontPageCategories() {
        return productCategoryMapper.getFrontPageCategories();
    }

    @Override
    public List<ProductCategory> getFirstPageCategories() {
        return productCategoryMapper.getFirstPageCategories();
    }

    @Override
    public List<ProductCategory> getNormalRec() {
        return productCategoryMapper.getNormalRec();
    }

    @Override
    public List<ProductCategory> getHotRec() {
        return productCategoryMapper.getHotRec();
    }

    @Override
    public List<ProductCategory> getSecRec() {
        return productCategoryMapper.getSecRec();
    }

    @Override
    public ProductCategory getCategoryUUID(String uuid) {
        return productCategoryMapper.getCategoryUUID(uuid);
    }
}
