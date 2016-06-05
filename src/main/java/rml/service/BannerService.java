package rml.service;

import rml.model.Banner;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/7.
 */
public interface BannerService {
    List<Banner> getPageBanners(String pagedName);
}
