package rml.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Good;
import rml.model.ProductBrand;
import rml.service.ProductBrandLinkService;
import rml.service.ProductBrandService;
import rml.util.ReturnJson;

/**
 * Created by edward-echo on 2016/4/6.
 */
@Controller
@RequestMapping("/Brand")
public class BrandController {

    @Autowired
    ProductBrandService productBrandService;

    @RequestMapping(value="/Brand",method = RequestMethod.GET)
    @ResponseBody
    public Object addUser(ProductBrand brand) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setErrorCode(7000);
        returnJson.setReturnMessage("调用成功");
        returnJson.setServerStatus(0);
        returnJson.setReturnObject(productBrandService.getProductBrandUUID(brand.getUuid()));
        return returnJson;
    }
}
