package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ProductCollectionMapper;
import rml.model.ProductCollection;
import rml.service.ProductCollectionService;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/22.
 */

@Service("productCollectionService")
public class ProductCollectionServiceImpl implements ProductCollectionService {

    @Autowired
    private ProductCollectionMapper productCollectionMapper;

    @Override
    public int insert(ProductCollection productCollection) {
        return productCollectionMapper.insert(productCollection);
    }

    @Override
    public ProductCollection getRepeatCollection(ProductCollection productCollection) {
        return productCollectionMapper.getRepeatCollection(productCollection);
    }

    @Override
    public List<ProductCollection> getUserCollection(ProductCollection productCollection) {
        return productCollectionMapper.getUserCollection(productCollection);
    }

    @Override
    public List<ProductCollection> getUserCollectionTotal(ProductCollection productCollection) {
        return productCollectionMapper.getUserCollectionTotal(productCollection);
    }

    @Override
    public void delete(ProductCollection uuid) {
        productCollectionMapper.delete(uuid);
    }
}
