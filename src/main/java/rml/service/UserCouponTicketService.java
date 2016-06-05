package rml.service;

import rml.model.UserCouponTicket;

import java.util.List;

/**
 * Created by edward-echo on 2016/5/18.
 */
public interface UserCouponTicketService {
    void updateShopName(UserCouponTicket ticket);
    List<UserCouponTicket> getTickets(UserCouponTicket ticket);
    List<UserCouponTicket> getTicketsTotal();
    void insert(UserCouponTicket couponTicket);
    UserCouponTicket getMaxBatchNo();
    UserCouponTicket getTicket(UserCouponTicket ticket);
    void disable(UserCouponTicket ticket);
    List<UserCouponTicket> getTicketBatch(UserCouponTicket ticket);
}
