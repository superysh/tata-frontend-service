package rml.dao;

import rml.model.City;

import java.util.List;

public interface CityMapper {

       List<City> getCities(String provinceId);

       City getCity(String cityId);

}