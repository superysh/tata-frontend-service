package rml.dao;

import rml.model.PropertyValue;

import java.util.List;


/**
 * Created by edward-echo on 2016/1/28.
 */
public interface PropertyValueMapper {
    int insert(PropertyValue propertyValue);
    List<PropertyValue> getProductProperty(String productId);
    PropertyValue getPropertyValue(String uuid);
    void delete(String productId);
}
