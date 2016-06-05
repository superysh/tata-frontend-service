package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.OrderMapper;
import rml.model.Order;
import rml.service.OrderService;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/20.
 */

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getOrdersPid(String pid) {
        return orderMapper.getOrdersPid(pid);
    }

    @Override
    public int insertNormalUserNew(Order order) {
        return orderMapper.insertNormalUserNew(order);
    }

    @Override
    public void updateOrderMoneyNew(Order order) {
        orderMapper.updateOrderMoneyNew(order);
    }

    @Override
    public void updateSubStatus(Order order) {
        orderMapper.updateSubStatus(order);
    }

    @Override
    public void updateOrderStatus(Order order) {
        orderMapper.updateOrderStatus(order);
    }

    @Override
    public int getShopReport(Order order) {
        return orderMapper.getShopReport(order);
    }

    @Override
    public List<Order> getBizOrderDaily(Order order) {
        return orderMapper.getBizOrderDaily(order);
    }

    @Override
    public List<Order> searchMobile(Order order) {
        return orderMapper.searchMobile(order);
    }

    @Override
    public List<Order> searchMobileDate(Order order) {
        return orderMapper.searchMobileDate(order);
    }

    @Override
    public List<Order> getOnlineMoneyOrders(Order order) {
        return orderMapper.getOnlineMoneyOrders(order);
    }

    @Override
    public List<Order> getOnlineMoneyOrdersTotal(Order order) {
        return orderMapper.getOnlineMoneyOrdersTotal(order);
    }

    @Override
    public Order getOnlineMoneyTotal(Order order) {
        return orderMapper.getOnlineMoneyTotal(order);
    }

    @Override
    public List<Order> getOrders(Order order) {
        return orderMapper.getOrders(order);
    }

    @Override
    public List<Order> getOrdersTotal() {
        return orderMapper.getOrdersTotal();
    }

    @Override
    public void delete(String uuid) {
        orderMapper.delete(uuid);
    }

    @Override
    public List<Order> getUserOrderStatus(Order order) {
        return orderMapper.getUserOrderStatus(order);
    }

    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public int insertUserPay(Order order) {
        return orderMapper.insertUserPay(order);
    }

    @Override
    public Order getUserOrder(String uuid) {
        return orderMapper.getUserOrder(uuid);
    }

    @Override
    public int updateOrderReal(String uuid) {
        return orderMapper.updateOrderReal(uuid);
    }

    @Override
    public Order getOrderById(String uuid) {
        return orderMapper.getOrderById(uuid);
    }

    @Override
    public int updateAddress(Order order) {
        return orderMapper.updateAddress(order);
    }

    @Override
    public Order getOrderByIdTemp(String uuid) {
        return orderMapper.getOrderByIdTemp(uuid);
    }

    @Override
    public int updateOrderMoney(Order order) {
        return orderMapper.updateOrderMoney(order);
    }

    @Override
    public void updateOrderFinish(String uuid) {
        orderMapper.updateOrderFinish(uuid);
    }

    @Override
    public int insertNormalUser(Order order) {
        return orderMapper.insertNormalUser(order);
    }

    @Override
    public Order getOrder(String uuid) {
        return orderMapper.getOrder(uuid);
    }

    @Override
    public Order getOrderByIdTemp1(String uuid) {
        return orderMapper.getOrderByIdTemp1(uuid);
    }
}