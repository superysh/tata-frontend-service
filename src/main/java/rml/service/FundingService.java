package rml.service;

import rml.model.Funding;

/**
 * Created by edward-echo on 2015/12/26.
 */
public interface FundingService {

    int insert(Funding funding);

    Funding getUserFunding(String uuid);

    int updateOrderReal(String orderId);

    int updateMoney(Funding funding);

    Funding getFunding(String orderId);

}
