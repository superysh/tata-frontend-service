package rml.dao;

import rml.model.Product;
import rml.model.ProductSuit;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/26.
 */
public interface ProductMapper {
    List<Product> getProducts(Product product);
    int getProductsAll(Product product);
    int insert(Product product);
    List<Product> getProductFront(Product product);
    int getProductFrontTotal(String moduleId);
    Product getProduct(String uuid);
    List<Product> getProductCategory(String categoryId);
    void update(Product product);
    void deleteProduct(String uuid);
    List<Product> getProductsTotal(String moduleId);
    void updateImgsMain(Product product);
    void updateDetailImgs(Product product);
    void updateMainImgs(Product product);
    List<Product> queryProductSearch(Product product);
    void updateImgs(Product product);
    List<Product> queryProductSearchTotal(Product product);
    int  getFrontAll(String moduleId);
    List<Product> getProductCategoryPid(Product product);
    Product getProductCategoryPidCount(Product product);
    List<Product> searchBrand(Product product);
}
