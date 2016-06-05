package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ProductMapper;
import rml.model.Product;
import rml.model.ProductSuit;
import rml.service.ProductService;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/26.
 */

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> queryProductSearch(Product name) {
        return productMapper.queryProductSearch(name);
    }

    @Override
    public List<Product> getProductCategoryPid(Product product) {
        return productMapper.getProductCategoryPid(product);
    }

    @Override
    public Product getProductCategoryPidCount(Product product) {
        return productMapper.getProductCategoryPidCount(product);
    }

    @Override
    public List<Product> queryProductSearchTotal(Product product) {
        return productMapper.queryProductSearchTotal(product);
    }

    @Override
    public void updateDetailImgs(Product product) {
        productMapper.updateDetailImgs(product);
    }

    @Override
    public void updateMainImgs(Product product) {
        productMapper.updateMainImgs(product);
    }

    @Override
    public void updateImgs(Product product) {
        productMapper.updateImgs(product);
    }

    @Override
    public void updateImgsMain(Product product) {
        productMapper.updateImgsMain(product);
    }

    @Override
    public List<Product> getProductsTotal(String moduleId) {
        return productMapper.getProductsTotal(moduleId);
    }

    @Override
    public void deleteProduct(String uuid) {
        productMapper.deleteProduct(uuid);
    }

    @Override
    public List<Product> getProducts(Product product) {
        return productMapper.getProducts(product);
    }

    @Override
    public int getProductsAll(Product product) {
        return productMapper.getProductsAll(product);
    }

    @Override
    public int getFrontAll(String moduleId) {
        return productMapper.getFrontAll(moduleId);
    }

    @Override
    public int insert(Product product) {
        return productMapper.insert(product);
    }

    @Override
    public List<Product> getProductFront(Product product) {
        return productMapper.getProductFront(product);
    }

    @Override
    public int getProductFrontTotal(String moduleId) {
        return productMapper.getProductFrontTotal(moduleId);
    }

    @Override
    public Product getProduct(String uuid) {
        return productMapper.getProduct(uuid);
    }

    @Override
    public List<Product> getProductCategory(String categoryId) {
        return productMapper.getProductCategory(categoryId);
    }

    @Override
    public List<Product> searchBrand(Product product) {
        return productMapper.searchBrand(product);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

}
