package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.FundingMapper;
import rml.model.Funding;
import rml.service.FundingService;

/**
 * Created by edward-echo on 2015/12/26.
 */
@Service("fundingService")
public class FundingServiceImpl implements FundingService {

    @Autowired
    FundingMapper fundingMapper;

    @Override
    public int insert(Funding funding) {
        return fundingMapper.insert(funding);
    }

    @Override
    public Funding getUserFunding(String uuid) {
        return fundingMapper.getUserFunding(uuid);
    }

    @Override
    public int updateOrderReal(String orderId) {
        return fundingMapper.updateOrderReal(orderId);
    }

    @Override
    public int updateMoney(Funding funding) {
        return fundingMapper.updateMoney(funding);
    }

    @Override
    public Funding getFunding(String orderId) {
        return fundingMapper.getFunding(orderId);
    }
}
