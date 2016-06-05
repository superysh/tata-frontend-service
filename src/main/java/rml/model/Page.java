package rml.model;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/6.
 */
public class Page {

    List<ProductCategory> headCategories;

    List<ProductCategory> middleCategories;

    List<ProductBrand> brands;

    List<Banner> banners;

    List<Banner> suit;

    private List<Size> bannerSizes;
    private List<Size> suitSizes;
    private List<Size> brandSizes;

    public List<Size> getBrandSizes() {
        return brandSizes;
    }

    public void setBrandSizes(List<Size> brandSizes) {
        this.brandSizes = brandSizes;
    }

    public List<Size> getSuitSizes() {
        return suitSizes;
    }

    public void setSuitSizes(List<Size> suitSizes) {
        this.suitSizes = suitSizes;
    }

    public List<Size> getBannerSizes() {
        return bannerSizes;
    }

    public void setBannerSizes(List<Size> bannerSizes) {
        this.bannerSizes = bannerSizes;
    }

    public List<Banner> getSuit() {
        return suit;
    }

    public void setSuit(List<Banner> suit) {
        this.suit = suit;
    }

    public List<ProductCategory> getHeadCategories() {
        return headCategories;
    }

    public void setHeadCategories(List<ProductCategory> headCategories) {
        this.headCategories = headCategories;
    }

    public List<ProductCategory> getMiddleCategories() {
        return middleCategories;
    }

    public void setMiddleCategories(List<ProductCategory> middleCategories) {
        this.middleCategories = middleCategories;
    }

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
