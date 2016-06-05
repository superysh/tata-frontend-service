package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.BannerMapper;
import rml.model.Banner;
import rml.service.BannerService;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/7.
 */

@Service("bannerService")
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> getPageBanners(String pagedName) {
        return bannerMapper.getPageBanners(pagedName);
    }
}
