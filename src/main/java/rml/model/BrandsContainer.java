package rml.model;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/17.
 */
public class BrandsContainer {
    private List<Banner> banners;

    private List<ProductBrand> brands;

    public List<ProductBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<ProductBrand> brands) {
        this.brands = brands;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }
}
