package rml.controller;

import com.lowagie.text.Image;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import rml.model.*;
import rml.service.BannerService;
import rml.service.ProductBrandService;
import rml.service.ProductCategoryService;
import rml.util.JedisConn;
import rml.util.MD5;
import rml.util.ReturnJson;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward-echo on 2016/3/7.
 */
@Controller
@RequestMapping("/Page")
public class PageController {

    @Autowired
    BannerService bannerService;

    @Autowired
    ProductBrandService productBrandService;


    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping(value = "/Front", method = RequestMethod.GET)
    @ResponseBody
    public Object addUserThird(Order order) {

        Jedis jedis = JedisConn.getRedis();
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(1000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        String result1 = MD5.GetMD5Code(order.getRandomKey()+"at^&*ta");
        if(!result1.equals(order.getSecretKey())){
            returnJson.setErrorCode(99999);
            returnJson.setReturnMessage("密钥无效" + order.toString());
            returnJson.setServerStatus(1);
            return returnJson;
        }
        String pageName = "frontPage";
        List<Banner> banners = bannerService.getPageBanners(pageName);
        List<Banner> suit = bannerService.getPageBanners("suit");
        List<ProductCategory> middleCategories = productCategoryService.getFrontPageCategories();
        List<ProductBrand> brands = productBrandService.getFrontBrands();
        Page page = new Page();
        List<Size> bannerSizes = new ArrayList<Size>();
        List<Size> suitSizes = new ArrayList<Size>();
        List<Size> brandSizes = new ArrayList<Size>();
        try {
            page.setBanners(banners);
            for (Banner banner : banners) {
                Size size = new Size();
                Image img = null;
                size.setPosition(banner.getPositionDesc());
                size.setOrder(banner.getOrder());
                if (jedis.get(banner.getPath() + "," + "width") != null) {
                    size.setWidth(jedis.get(banner.getPath() + "," + "width"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(banner.getPath()));
                    }
                    size.setWidth(new Float(img.width()).toString());
                    jedis.append(banner.getPath() + "," + "width", new Float(img.width()).toString());
                }
                if (jedis.get(banner.getPath() + "," + "height") != null) {
                    size.setHeight(jedis.get(banner.getPath() + "," + "height"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(banner.getPath()));
                    }
                    size.setHeight(new Float(img.height()).toString());
                    jedis.append(banner.getPath() + "," + "height", new Float(img.height()).toString());
                }
                bannerSizes.add(size);
            }
            for (Banner banner : suit) {
                Size size = new Size();
                Image img = null;
                size.setOrder(banner.getOrder());
                size.setPosition(banner.getPositionDesc());
                if (jedis.get(banner.getPath() + "," + "width") != null) {
                    size.setWidth(jedis.get(banner.getPath() + "," + "width"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(banner.getPath()));
                    }
                    size.setWidth(new Float(img.width()).toString());
                    jedis.append(banner.getPath() + "," + "width", new Float(img.width()).toString());
                }
                if (jedis.get(banner.getPath() + "," + "height") != null) {
                    size.setHeight(jedis.get(banner.getPath() + "," + "height"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(banner.getPath()));
                    }
                    size.setHeight(new Float(img.height()).toString());
                    jedis.append(banner.getPath() + "," + "height", new Float(img.height()).toString());
                }
                suitSizes.add(size);
            }
            for (ProductBrand brand : brands) {
                Size size = new Size();
                Image img = null;
                size.setOrder(brand.getOrder());
                if (jedis.get(brand.getIcon() + "," + "width") != null) {
                    size.setWidth(jedis.get(brand.getIcon() + "," + "width"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(brand.getIcon()));
                    }
                    size.setWidth(new Float(img.width()).toString());
                    jedis.append(brand.getIcon() + "," + "width", new Float(img.width()).toString());
                }
                if (jedis.get(brand.getIcon() + "," + "height") != null) {
                    size.setHeight(jedis.get(brand.getIcon() + "," + "height"));
                } else {
                    if (img == null) {
                        img = Image.getInstance(new URL(brand.getIcon()));
                    }
                    size.setHeight(new Float(img.height()).toString());
                    jedis.append(brand.getIcon() + "," + "height", new Float(img.height()).toString());
                }
                brandSizes.add(size);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        page.setBrands(brands);
        page.setSuitSizes(suitSizes);
        page.setBannerSizes(bannerSizes);
        page.setBrandSizes(brandSizes);
        page.setSuit(suit);
        page.setMiddleCategories(middleCategories);
        page.setHeadCategories(productCategoryService.getFirstPageCategories());
        returnJson.setReturnObject(page);
        return returnJson;
    }
}