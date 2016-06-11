package rml.service;


import rml.model.WechatPayResult;

/**
 * Created by yeshenghong on 2016/06/11.
 */
public interface WechatPayResultService {

  
    int insert(WechatPayResult wechatPayResult);


    WechatPayResult getWechatPayResultByOrderNo(String orderNo);
    
    int delete(String orderNo);

}
