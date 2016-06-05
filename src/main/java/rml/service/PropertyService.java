package rml.service;

import rml.model.Property;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/26.
 */
public interface PropertyService {
    List<Property> getProperties(String moduleId);
}
