package rml.dao;

import rml.model.ProductBrand;
import rml.model.ProductCategory;
import rml.model.ProductCollection;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/23.
 */
public interface ProductCollectionMapper {
    int insert(ProductCollection productCollection);
    List<ProductCollection> getUserCollection(ProductCollection productCollection);
    List<ProductCollection> getUserCollectionTotal(ProductCollection productCollection);
    ProductCollection  getRepeatCollection(ProductCollection productCollection);
    void delete(ProductCollection productCollection);
}
