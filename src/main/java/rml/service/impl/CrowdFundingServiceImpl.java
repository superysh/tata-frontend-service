package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CrowdFundingMapper;
import rml.model.CrowdFunding;
import rml.service.CrowdFundingService;

import java.util.List;

/**
 * Created by edward-echo on 2016/1/3.
 */
@Service("crowdFundingService")
public class CrowdFundingServiceImpl implements CrowdFundingService {

    @Autowired
    private CrowdFundingMapper crowdFundingMapper;

    @Override
    public int insert(CrowdFunding crowdFunding) {
        return crowdFundingMapper.insert(crowdFunding);
    }

    @Override
    public List<CrowdFunding> getLevelOne() {
        return crowdFundingMapper.getLevelOne();
    }

    @Override
    public List<CrowdFunding> getLevelTwo() {
        return crowdFundingMapper.getLevelTwo();
    }


    public List<CrowdFunding> getLevelThree() {
        return crowdFundingMapper.getLevelThree();
    }

    @Override
    public CrowdFunding getUser(CrowdFunding crowdFunding) {
        return crowdFundingMapper.getUser(crowdFunding);
    }
}
