package rml.dao;

import rml.model.ProductBrand;
import rml.model.ProductCategory;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/23.
 */
public interface ProductBrandMapper {
    List<ProductBrand> getProductBrand(String moduleId);

    int updateBrand(ProductBrand productBrand);

    int insertBrand(ProductBrand productBrand);

    int deleteBrand(String uuid);

    List<ProductBrand> getFrontBrands();

    List<ProductCategory> getFirstPageCategories();

    List<ProductBrand> getBrandsAll();

    ProductBrand getProductBrandUUID(String uuid);
}
