package rml.dao;

        import rml.model.FundingComment;

        import java.util.List;

/**
 * Created by edward-echo on 2015/12/27.
 */
public interface FundingCommentMapper {
    int insertComment(FundingComment fundingComment);

    List<FundingComment> getComments(String userId);

    int updateComment(String uuid);
}
