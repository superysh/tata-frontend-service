package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ProductBrandMapper;
import rml.model.Product;
import rml.model.ProductBrand;
import rml.model.ProductCategory;
import rml.service.ProductBrandService;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/23.
 */
@Service("productBrandService")
public class ProductBrandServiceImpl implements ProductBrandService {


    @Autowired
    private ProductBrandMapper productBrandMapper;


    @Override
    public List<ProductBrand> getProductBrand(String moduleId) {
        return productBrandMapper.getProductBrand(moduleId);
    }

    @Override
    public int updateBrand(ProductBrand productBrand) {
        return productBrandMapper.updateBrand(productBrand);
    }

    @Override
    public int insertBrand(ProductBrand productBrand) {
        return productBrandMapper.insertBrand(productBrand);
    }

    @Override
    public int deleteBrand(String uuid) {
        return productBrandMapper.deleteBrand(uuid);
    }

    @Override
    public List<ProductBrand> getFrontBrands() {
        return productBrandMapper.getFrontBrands();
    }

    @Override
    public List<ProductCategory> getFirstPageCategories() {
        return productBrandMapper.getFirstPageCategories();
    }

    @Override
    public List<ProductBrand> getBrandsAll() {
        return productBrandMapper.getBrandsAll();
    }

    @Override
    public ProductBrand getProductBrandUUID(String uuid) {
        return productBrandMapper.getProductBrandUUID(uuid);
    }


    public ProductBrandMapper getProductBrandMapper() {
        return productBrandMapper;
    }

    public void setProductBrandMapper(ProductBrandMapper productBrandMapper) {
        this.productBrandMapper = productBrandMapper;
    }
}
