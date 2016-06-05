package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.PropertyMapper;
import rml.model.Property;
import rml.service.PropertyService;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/26.
 */

@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;

    @Override
    public List<Property> getProperties(String moduleId) {
        return propertyMapper.getProperties(moduleId);
    }
}
