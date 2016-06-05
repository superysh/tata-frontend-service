package rml.service;

import rml.model.Area;
import rml.model.City;
import rml.model.Province;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3 0003.
 */
public interface LocationService {

    List<Province> getProvinces();

    List<City> getCities(String provinceId);

    List<Area> getAreas(String cityId);

    Province getProvince(String provinceId);

    City getCity(String cityId);

    Area getArea(String areaId);

}
