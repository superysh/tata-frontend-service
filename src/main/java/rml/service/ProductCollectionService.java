package rml.service;

import rml.model.ProductCollection;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/22.
 */
public interface ProductCollectionService {
    int insert(ProductCollection productCollection);
    ProductCollection  getRepeatCollection(ProductCollection productCollection);
    List<ProductCollection> getUserCollection(ProductCollection productCollection);
    List<ProductCollection> getUserCollectionTotal(ProductCollection productCollection);
    void delete(ProductCollection productCollection);
}
