package rml.dao;

import rml.model.Order;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */
public interface OrderMapper {

    int insert(Order order);

    int insertUserPay(Order order);

    Order getUserOrder(String uuid);

    int updateOrderReal(String uuid);

    Order getOrderById(String uuid);

    Order getOrderByIdTemp(String uuid);

    int updateAddress(Order order);

    int updateOrderMoney(Order order);

    Order getOrderByIdTemp1(String uuid);

    void updateOrderFinish(String uuid);

    int insertNormalUser(Order order);

    int insertNormalUserNew(Order order);

    Order getOrder(String uuid);

    List<Order> getUserOrderStatus(Order order);

    List<Order> getOrders(Order order);
    List<Order> getOrdersTotal();

    void delete(String uuid);

    List<Order> getBizOrderDaily(Order order);
    List<Order> getOnlineMoneyOrders(Order order);
    int getShopReport(Order order);

    void updateOrderStatus(Order order);

    void updateSubStatus(Order order);

    List<Order> getOrdersPid(String pid);

    void updateOrderMoneyNew(Order order);

    Order getOnlineMoneyTotal(Order order);

    List<Order> getOnlineMoneyOrdersTotal(Order order);

    List<Order> searchMobile(Order order);

    List<Order> searchMobileDate(Order order);


}
