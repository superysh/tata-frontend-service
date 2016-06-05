package rml.service;

import rml.model.Product;
import rml.model.ProductSuit;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/14.
 */
public interface ProductSuitService {
    List<ProductSuit> getProducts(ProductSuit product);
    int getProductsAll(String moduleId);
    int insert(ProductSuit product);
    List<ProductSuit> getProductFront(ProductSuit product);
    int getProductFrontTotal(String moduleId);
    ProductSuit getProduct(String uuid);
    List<ProductSuit> getProductCategory(String categoryId);
    void update(ProductSuit product);
    void deleteProduct(String uuid);
    List<ProductSuit> getProductsTotal(String moduleId);
    void updateImgsMain(ProductSuit product);
    List<ProductSuit> queryProductSearch(ProductSuit product);
    void updateImgs(ProductSuit product);
    List<ProductSuit> queryProductSearchTotal(ProductSuit product);
}
