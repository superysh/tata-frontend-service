package rml.dao;

import rml.model.CrowdFunding;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/3.
 */
public interface CrowdFundingMapper {
    int insert(CrowdFunding crowdFunding);

    List<CrowdFunding> getLevelOne();

    List<CrowdFunding> getLevelTwo();

    List<CrowdFunding> getLevelThree();

    CrowdFunding getUser(CrowdFunding crowdFunding);

}
