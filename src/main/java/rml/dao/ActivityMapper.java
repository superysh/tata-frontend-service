package rml.dao;

import rml.model.Activity;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */
public interface ActivityMapper {

    Activity getActivityByName(Activity activity);

    Activity getActivityByUUID(Activity activity);

    List<Activity> getActivities();

}
