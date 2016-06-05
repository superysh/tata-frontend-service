package rml.service;

import rml.model.Order;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */
public interface OrderService {

    List<Order> searchMobile(Order order);

    List<Order> searchMobileDate(Order order);


    List<Order> getOnlineMoneyOrders(Order order);

    List<Order> getOnlineMoneyOrdersTotal(Order order);

    Order getOnlineMoneyTotal(Order order);

    List<Order> getOrders(Order order);

    List<Order> getOrdersTotal();

    List<Order> getOrdersPid(String pid);

    int insertNormalUserNew(Order order);

    void updateOrderMoneyNew(Order order);

    void updateSubStatus(Order order);

    void updateOrderStatus(Order order);

    int getShopReport(Order order);

    List<Order> getBizOrderDaily(Order order);

    void delete(String uuid);

    List<Order> getUserOrderStatus(Order order);

    int insert(Order order);

    int insertUserPay(Order order);

    Order getUserOrder(String uuid);

    int updateOrderReal(String uuid);

    Order getOrderById(String uuid);

    int updateAddress(Order order);

    Order getOrderByIdTemp(String uuid);

    int updateOrderMoney(Order order);

    Order getOrderByIdTemp1(String uuid);

    void updateOrderFinish(String uuid);

    int insertNormalUser(Order order);

    Order getOrder(String uuid);

}
