package rml.dao;

import rml.model.Area;

import java.util.List;

public interface AreaMapper {

       List<Area> getAreas(String cityId);

       Area getArea(String areaId);

}