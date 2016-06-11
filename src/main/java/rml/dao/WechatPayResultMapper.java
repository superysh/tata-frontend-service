package rml.dao;

import rml.model.Order;
import rml.model.WechatPayResult;

/**
 * Created by yeshenghong on 2016/06/11.
 */
public interface WechatPayResultMapper {

    int insert(WechatPayResult WechatPayResult);

    WechatPayResult getWechatPayResultByOrderNo(String orderNo);
    
    int delete(String orderNo);

}
