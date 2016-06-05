package rml.model;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/28.
 */
public class ProvinceCity {
    private int id;

    private String cityId;



    private String provinceId;

    private String provinceName;

    List<CityArea> cityAreaList;

    private String secretKey;

    private String randomKey;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<CityArea> getCityAreaList() {
        return cityAreaList;
    }

    public void setCityAreaList(List<CityArea> cityAreaList) {
        this.cityAreaList = cityAreaList;
    }
}
