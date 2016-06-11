package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.OrderMapper;
import rml.dao.WechatPayResultMapper;
import rml.model.Order;
import rml.model.WechatPayResult;
import rml.service.OrderService;
import rml.service.WechatPayResultService;

import java.util.List;

/**
 * Created by yeshenghong on 2015/12/20.
 */

@Service("wechatPayResultService")
public class WechatPayResultServiceImpl implements WechatPayResultService {

    @Autowired
    private WechatPayResultMapper wechatPayResultMapper;

    
    @Override
    public int insert(WechatPayResult wechatPayResult) {
        return wechatPayResultMapper.insert(wechatPayResult);
    }

    @Override
    public WechatPayResult getWechatPayResultByOrderNo(String orderNo) {
        return wechatPayResultMapper.getWechatPayResultByOrderNo(orderNo);
    }
    
    @Override
    public int delete(String orderNo){
        return wechatPayResultMapper.delete(orderNo);
    }

   
}