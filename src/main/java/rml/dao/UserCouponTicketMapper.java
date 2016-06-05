package rml.dao;

import rml.model.UserCouponTicket;

import java.util.List;

/**
 * Created by edward-echo on 2016/5/17.
 */
public interface UserCouponTicketMapper {
    void insert(UserCouponTicket couponTicket);
    UserCouponTicket getMaxBatchNo();
    UserCouponTicket getTicket(UserCouponTicket ticket);
    void disable(UserCouponTicket ticket);
    List<UserCouponTicket> getTicketBatch(UserCouponTicket ticket);
    List<UserCouponTicket> getTickets(UserCouponTicket ticket);
    List<UserCouponTicket> getTicketsTotal();
    void updateShopName(UserCouponTicket ticket);
}
