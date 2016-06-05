package rml.model;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/15.
 */
public class CategoryPage {

    private List<Banner> banners;

    private ProductCategory firstLevelCategory;

    private ProductCategory currentCategory;

    private List<ProductCategory> secondLevelCategories;

    public ProductCategory getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(ProductCategory currentCategory) {
        this.currentCategory = currentCategory;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public ProductCategory getFirstLevelCategory() {
        return firstLevelCategory;
    }

    public void setFirstLevelCategory(ProductCategory firstLevelCategory) {
        this.firstLevelCategory = firstLevelCategory;
    }

    public List<ProductCategory> getSecondLevelCategories() {
        return secondLevelCategories;
    }

    public void setSecondLevelCategories(List<ProductCategory> secondLevelCategories) {
        this.secondLevelCategories = secondLevelCategories;
    }
}
