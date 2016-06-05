package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ActivityMapper;
import rml.model.Activity;
import rml.service.ActivityService;

import java.util.Date;
import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Activity getActivityByName(String name) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setEndTime(new Date());
        return activityMapper.getActivityByName(activity);
    }

    @Override
    public Activity getActivityByUUID(String uuid) {
        Activity activity = new Activity();
        activity.setUuid(uuid);
        activity.setEndTime(new Date());
        return activityMapper.getActivityByUUID(activity);
    }

    @Override
    public List<Activity> getActivities() {
        return activityMapper.getActivities() ;
    }
}
