package rml.dao;

import rml.model.Property;

import java.util.List;

public interface PropertyMapper {
       List<Property> getProperties(String moduleId);
}