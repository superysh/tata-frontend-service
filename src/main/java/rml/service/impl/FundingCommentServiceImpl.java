package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.FundingCommentMapper;
import rml.model.FundingComment;
import rml.service.FundingCommentService;
import rml.service.FundingService;

import java.util.List;

/**
 * Created by edward-echo on 2015/12/27.
 */

@Service("fundingCommentService")
public class FundingCommentServiceImpl implements FundingCommentService {

    @Autowired
    private FundingCommentMapper fundingCommentMapper;


    @Override
    public int insertComment(FundingComment fundingComment) {
        return fundingCommentMapper.insertComment(fundingComment);
    }

    @Override
    public List<FundingComment> getComments(String userId) {
        return fundingCommentMapper.getComments(userId);
    }

    @Override
    public int updateComment(String uuid) {
        return fundingCommentMapper.updateComment(uuid);
    }
}
