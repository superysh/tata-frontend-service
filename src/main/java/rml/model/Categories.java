package rml.model;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/14.
 */
public class Categories {
    private List<ProductCategory> normalCategories;

    private List<ProductCategory> hotCategories;

    private List<Banner> banners;

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public List<ProductCategory> getNormalCategories() {
        return normalCategories;
    }

    public void setNormalCategories(List<ProductCategory> normalCategories) {
        this.normalCategories = normalCategories;
    }

    public List<ProductCategory> getHotCategories() {
        return hotCategories;
    }

    public void setHotCategories(List<ProductCategory> hotCategories) {
        this.hotCategories = hotCategories;
    }
}

