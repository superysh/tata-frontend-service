package rml.service;

import rml.model.Order;
import rml.model.Product;
import rml.model.ProductCategory;
import rml.model.ProductSuit;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/26.
 */
public interface ProductService {
    List<Product> queryProductSearch(Product product);

    List<Product> getProductCategoryPid(Product product);
    Product getProductCategoryPidCount(Product product);

    List<Product> queryProductSearchTotal(Product product);

    void updateDetailImgs(Product product);

    void updateMainImgs(Product product);

    void updateImgs(Product product);

    void updateImgsMain(Product product);

    List<Product> getProductsTotal(String moduleId);

    void deleteProduct(String uuid);

    List<Product> getProducts(Product product);

    int getProductsAll(Product product);

    int getFrontAll(String moduleId);

    int insert(Product product);

    List<Product> getProductFront(Product product);

    int getProductFrontTotal(String moduleId);

    Product getProduct(String uuid);

    List<Product> getProductCategory(String categoryId);

    void update(Product product);

    List<Product> searchBrand(Product product);
}
