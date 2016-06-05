package rml.model;


import java.util.Date;
import java.util.List;

/**
 * Created by edward-echo on 2016/1/26.
 */
public class  Product {

    private String uuid;

    private String name;

    private int isOnline;

    private int status;

    private int storage;

    private int order;

    private String keyWords;

    private String ldescPc;

    private String ldescMobile;

    private Date createTime;

    private String updateTime;

    private int recordStatus;

    private String code;

    private String productCode;

    private int storageAlert;

    private int integral;

    private String moduleId;

    private String moduleName;

    private int pageSize;

    private int pageNo;

    private int startSize;

    private List<String> propertyKey;

    private List<String> propertyValue;

    private int price;

    private List<String> propertyId;

    private String imgs;

    private String categoryId;

    private String imgsMain;

    private String backPrice;

    private Product product;

    private int isTop;

    private String brandIds;

    private String categoryIds;

    private int type;

    private List<Size> imgsMainSizes;

    private List<Size> imgsSizes;

    private String userId;

    private boolean isCollected;

    private String detailImgs;

    private String mainImgs;

    private String secretKey;

    private String randomKey;

    private String categroyId;

    private String total;

    private String mainImgsBig;

    public String getMainImgsBig() {
        return mainImgsBig;
    }

    public void setMainImgsBig(String mainImgsBig) {
        this.mainImgsBig = mainImgsBig;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCategroyId() {
        return categroyId;
    }

    public void setCategroyId(String categroyId) {
        this.categroyId = categroyId;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(String detailImgs) {
        this.detailImgs = detailImgs;
    }

    public String getMainImgs() {
        return mainImgs;
    }

    public void setMainImgs(String mainImgs) {
        this.mainImgs = mainImgs;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Size> getImgsMainSizes() {
        return imgsMainSizes;
    }

    public void setImgsMainSizes(List<Size> imgsMainSizes) {
        this.imgsMainSizes = imgsMainSizes;
    }

    public List<Size> getImgsSizes() {
        return imgsSizes;
    }

    public void setImgsSizes(List<Size> imgsSizes) {
        this.imgsSizes = imgsSizes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getBackPrice() {
        return backPrice;
    }

    public void setBackPrice(String backPrice) {
        this.backPrice = backPrice;
    }

    public String getImgsMain() {
        return imgsMain;
    }

    public void setImgsMain(String imgsMain) {
        this.imgsMain = imgsMain;
    }

    private List<PropertyValue> propertyValues;

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }


    public List<String> getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(List<String> propertyKey) {
        this.propertyKey = propertyKey;
    }

    public List<String> getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(List<String> propertyValue) {
        this.propertyValue = propertyValue;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(List<String> propertyId) {
        this.propertyId = propertyId;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    private String brandId;

    private String brandName;

    public int getStartSize() {
        return (pageNo-1)*pageSize;
    }

    public void setStartSize(int startSize) {
        this.startSize = startSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getLdescPc() {
        return ldescPc;
    }

    public void setLdescPc(String ldescPc) {
        this.ldescPc = ldescPc;
    }

    public String getLdescMobile() {
        return ldescMobile;
    }

    public void setLdescMobile(String ldescMobile) {
        this.ldescMobile = ldescMobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getStorageAlert() {
        return storageAlert;
    }

    public void setStorageAlert(int storageAlert) {
        this.storageAlert = storageAlert;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
