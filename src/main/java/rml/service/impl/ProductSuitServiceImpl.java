package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ProductSuitMapper;
import rml.model.ProductSuit;
import rml.service.ProductSuitService;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/14.
 */

@Service("productSuitService")
public class ProductSuitServiceImpl implements ProductSuitService {

    @Autowired
    private ProductSuitMapper productSuitMapper;

    @Override
    public List<ProductSuit> getProducts(ProductSuit product) {
        return productSuitMapper.getProducts(product);
    }

    @Override
    public int getProductsAll(String moduleId) {
        return productSuitMapper.getProductsAll(moduleId);
    }

    @Override
    public int insert(ProductSuit productSuit) {
        return productSuitMapper.insert(productSuit);
    }

    @Override
    public List<ProductSuit> getProductFront(ProductSuit product) {
        return productSuitMapper.getProductFront(product);
    }

    @Override
    public int getProductFrontTotal(String moduleId) {
        return productSuitMapper.getProductFrontTotal(moduleId);
    }

    @Override
    public ProductSuit getProduct(String uuid) {
        return productSuitMapper.getProduct(uuid);
    }

    @Override
    public List<ProductSuit> getProductCategory(String categoryId) {
        return productSuitMapper.getProductCategory(categoryId);
    }

    @Override
    public void update(ProductSuit product) {
            productSuitMapper.update(product);
    }

    @Override
    public void deleteProduct(String uuid) {
        productSuitMapper.deleteProduct(uuid);
    }

    @Override
    public List<ProductSuit> getProductsTotal(String moduleId) {
        return productSuitMapper.getProductsTotal(moduleId);
    }

    @Override
    public void updateImgsMain(ProductSuit product) {
            productSuitMapper.updateImgsMain(product);
    }

    @Override
    public List<ProductSuit> queryProductSearch(ProductSuit product) {
        return productSuitMapper.queryProductSearch(product);
    }

    @Override
    public void updateImgs(ProductSuit product) {
        productSuitMapper.updateImgsMain(product);
    }

    @Override
    public List<ProductSuit> queryProductSearchTotal(ProductSuit product) {
        return productSuitMapper.queryProductSearchTotal(product);
    }
}
