package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.AreaMapper;
import rml.dao.CityMapper;
import rml.dao.ProvinceMapper;
import rml.model.Area;
import rml.model.City;
import rml.model.Province;
import rml.service.LocationService;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3 0003.
 */
@Service("locationService")
public class LocationServiceImpl implements LocationService {

    @Autowired
    ProvinceMapper provinceMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    AreaMapper areaMapper;

    @Override
    public List<Province> getProvinces() {
        return provinceMapper.getProvinces();
    }

    @Override
    public List<City> getCities(String provinceId) {
        return cityMapper.getCities(provinceId);
    }

    @Override
    public List<Area> getAreas(String cityId) {
        return areaMapper.getAreas(cityId);
    }

    @Override
    public Province getProvince(String provinceId) {
        return provinceMapper.getProvince(provinceId);
    }

    @Override
    public City getCity(String cityId) {
        return cityMapper.getCity(cityId);
    }

    @Override
    public Area getArea(String areaId) {
        return areaMapper.getArea(areaId);
    }
}
