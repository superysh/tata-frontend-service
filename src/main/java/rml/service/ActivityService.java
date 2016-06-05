package rml.service;

import rml.model.Activity;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */
public interface ActivityService {
    Activity getActivityByName(String name);

    Activity getActivityByUUID(String uuid);

    List<Activity> getActivities();
}
