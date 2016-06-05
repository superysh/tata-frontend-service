package rml.controller;

import com.lowagie.text.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import rml.model.Product;
import rml.model.ProductCategoryLink;
import rml.model.Size;
import rml.service.ProductCategoryLinkService;
import rml.service.ProductService;
import rml.servlet.FileUploadImgServlet;
import rml.servlet.FileUploadServlet;
import rml.util.FileUtil;
import rml.util.JedisConn;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by edward-echo on 2016/4/8.
 */
@Controller
@RequestMapping("/Transfer")
public class transferImgController {

    @RequestMapping(value="/Transfer/Main",method = RequestMethod.GET)
    @ResponseBody
    public void doTransfer() {
        List<Product> products = productService.getProductsTotal("1");
        for (Product product : products) {
            if (StringUtils.isEmpty(product.getMainImgs())) {
                String temp0 = product.getImgsMain();
                if (!StringUtils.isEmpty(temp0)) {
                    String[] imgs_main = temp0.split(",");
                    String main_files = "";
                    for (String img : imgs_main) {
                        if (!StringUtils.isEmpty(img) || img == null) {
                            try {
                                String fileName = FileUtil.transferImg1(img, System.getProperty("java.io.tmpdir") + "/tmp0");
                                String filePath = FileUploadServlet.fileUpload(new File(fileName));
                                if (main_files != "") {
                                    main_files = main_files + "," + filePath;
                                } else {
                                    main_files = main_files + filePath;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    System.err.print(main_files);
                    product.setImgsMain(main_files);
                    productService.updateMainImgs(product);
                }
            }

        }
    }

    @RequestMapping(value="/Transfer/Detail",method = RequestMethod.GET)
    @ResponseBody
    public void transferDetail(){
        List<Product> products = productService.getProductsTotal("1");
        for (Product product : products) {
        if (StringUtils.isEmpty(product.getDetailImgs())) {
            String temp0 = "";
            String[] imgs_main = temp0.split(",");
            String main_files = "";
            temp0 = product.getImgs();
            if(temp0!=null) {
                imgs_main = temp0.split(",");
                main_files = "";
                for (String img : imgs_main) {
                    if (!StringUtils.isEmpty(img)||img==null)
                        try {
                            String fileName = FileUtil.transferImg1(img, System.getProperty("java.io.tmpdir") + "/tmp1" );
                            String filePath = FileUploadServlet.fileUpload(new File(fileName));
                            if (main_files != "") {
                                main_files = main_files + "," + filePath;
                            } else {
                                main_files = main_files + filePath;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
                System.err.print(main_files);
                product.setImgsMain(main_files);
                productService.updateDetailImgs(product);
            }
        }
    }
    }




    @RequestMapping(value="/Transfer/Name",method = RequestMethod.GET)
    @ResponseBody
    public void doTransferName(){
        List<Product> products = productService.getProductsTotal("1");
        for(Product product:products){
            String temp0 = product.getImgsMain();
            if(temp0!=null) {
                String[] imgs_main = temp0.split(",");
                String main_files = "";
                for (String img : imgs_main) {
                    try {
                        String destPath = "http://222.73.17.49";
                        int index = img.indexOf("/group1");
                        destPath = destPath + img.substring(index, img.length());
                        if (main_files != "") {
                            main_files = main_files + "," + destPath;
                        } else {
                            main_files = main_files + destPath;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.err.print(main_files);
                    product.setImgsMain(main_files);
                    productService.updateImgsMain(product);
                }
            }
            temp0 = product.getImgs();
            if(temp0!=null) {
                String[] imgs_main = temp0.split(",");
                String main_files = "";
                for (String img : imgs_main) {
                    try {
                        String destPath = "http://222.73.17.49";
                        int index = img.indexOf("/group1");
                        destPath = destPath + img.substring(index, img.length());
                        if (main_files != "") {
                            main_files = main_files + "," + destPath;
                        } else {
                            main_files = main_files + destPath;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.err.print(main_files);
                    product.setImgs(main_files);
                    productService.updateImgs(product);
                }
            }
        }
    }




    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryLinkService productCategoryLinkService;

    @RequestMapping(value="/Transfer/Category",method = RequestMethod.GET)
    @ResponseBody
    public void transferProductCateory(){
        List<Product> products = productService.getProductsTotal("1");
        for(Product product:products){
            ProductCategoryLink productCategoryLink = new ProductCategoryLink();
            productCategoryLink.setProductId(product.getUuid());
            productCategoryLink.setCategoryId(product.getCategoryId());
            productCategoryLink.setUuid(UUID.randomUUID().toString());
            productCategoryLink.setCreateTime(new Date());
            productCategoryLinkService.insertCategory(productCategoryLink);
        }
    }

    @RequestMapping(value="/Transfer/Img ",method = RequestMethod.GET)
    @ResponseBody
    public void getPicSizes(){
        Jedis jedis = JedisConn.getRedis();
        List<Product> products = productService.getProductsTotal("1");
        for (Product product : products) {
            String tmpValue = product.getMainImgs();
            if(tmpValue!=null){
            String[] imgsMain = tmpValue.split(",");
            List<Size> imgsMianSizes = new ArrayList<Size>();
            for (int i = 0; i < imgsMain.length; i++) {
                Image img = null;
                Size size = new Size();
                if (jedis.get(imgsMain[i] + "," + "width") != null) {
                    size.setWidth(jedis.get(imgsMain[i] + "," + "width"));
                } else {
                    if (img == null) {
                        try {
                            img = Image.getInstance(new URL(imgsMain[i]));
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    size.setWidth(new Float(img.width()).toString());
                    jedis.append(imgsMain[i] + "," + "width", new Float(img.width()).toString());
                }

                if (jedis.get(imgsMain[i] + "," + "height") != null) {
                    size.setWidth(jedis.get(imgsMain[i] + "," + "height"));
                } else {
                    if (img == null) {
                        try {
                            img = Image.getInstance(new URL(imgsMain[i]));
                        }catch (Exception e){
                            continue;
                        }
                    }
                    size.setHeight(new Float(img.height()).toString());
                    jedis.append(imgsMain[i] + "," + "height", new Float(img.height()).toString());
                }
            }
            }
            tmpValue = product.getDetailImgs();
            if(tmpValue!=null) {
                String[] imgsValue = tmpValue.split(",");
                for (int i = 0; i < imgsValue.length; i++) {
                    Image img = null;
                    Size size = new Size();
                    if (jedis.get(imgsValue[i] + "," + "width") != null) {
                        size.setWidth(jedis.get(imgsValue[i] + "," + "width"));
                    } else {
                        if (img == null) {
                            try {
                                img = Image.getInstance(new URL(imgsValue[i]));
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        size.setWidth(new Float(img.width()).toString());
                        jedis.append(imgsValue[i] + "," + "width", new Float(img.width()).toString());
                    }
                    if (jedis.get(imgsValue[i] + "," + "height") != null) {
                        size.setHeight(jedis.get(imgsValue[i] + "," + "height"));
                    } else {
                        if (img == null) {
                            try {
                                img = Image.getInstance(new URL(imgsValue[i]));
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        size.setHeight(new Float(img.height()).toString());
                        jedis.append(imgsValue[i] + "," + "height", new Float(img.height()).toString());
                    }
                }
            }
        }
    }
}
