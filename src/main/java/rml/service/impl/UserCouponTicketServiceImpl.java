package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.UserCouponTicketMapper;
import rml.model.UserCouponTicket;
import rml.service.UserCouponTicketService;

import java.util.List;

/**
 * Created by edward-echo on 2016/5/18.
 */
@Service("userCouponTicketService")
public class UserCouponTicketServiceImpl implements UserCouponTicketService {

    @Autowired
    private UserCouponTicketMapper userCouponTicketMapper;

    @Override
    public void updateShopName(UserCouponTicket ticket) {
        userCouponTicketMapper.updateShopName(ticket);
    }

    @Override
    public List<UserCouponTicket> getTickets(UserCouponTicket ticket) {
        return userCouponTicketMapper.getTickets(ticket);
    }

    @Override
    public List<UserCouponTicket> getTicketsTotal() {
        return userCouponTicketMapper.getTicketsTotal();
    }

    @Override
    public void insert(UserCouponTicket couponTicket) {
            userCouponTicketMapper.insert(couponTicket);
    }

    @Override
    public UserCouponTicket getMaxBatchNo() {
        return userCouponTicketMapper.getMaxBatchNo();
    }

    @Override
    public UserCouponTicket getTicket(UserCouponTicket ticket) {
        return userCouponTicketMapper.getTicket(ticket);
    }

    @Override
    public void disable(UserCouponTicket ticket) {
        userCouponTicketMapper.disable(ticket);
    }

    @Override
    public List<UserCouponTicket> getTicketBatch(UserCouponTicket ticket) {
        return userCouponTicketMapper.getTicketBatch(ticket);
    }
}
