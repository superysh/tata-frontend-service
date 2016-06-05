package rml.dao;

import rml.model.Banner;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/6.
 */
public interface BannerMapper {
    List<Banner> getPageBanners(String pagedName);
}
