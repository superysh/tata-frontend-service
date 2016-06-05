package rml.dao;

import rml.model.Province;

import java.util.List;

public interface ProvinceMapper {

       List<Province> getProvinces();

       Province getProvince(String provinceId);

}