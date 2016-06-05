package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.PropertyValueMapper;
import rml.model.PropertyValue;
import rml.service.PropertyValueService;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/28.
 */
@Service("propertyValueService")
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Override
    public int insert(PropertyValue propertyValue) {
        return propertyValueMapper.insert(propertyValue);
    }

    @Override
    public List<PropertyValue> getProductProperty(String productId) {
        return propertyValueMapper.getProductProperty(productId);
    }

    @Override
    public PropertyValue getPropertyValue(String uuid) {
        return propertyValueMapper.getPropertyValue(uuid);
    }

    @Override
    public void delete(String productId) {
        propertyValueMapper.delete(productId);
    }
}
