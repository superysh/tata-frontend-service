package rml.dao;

import rml.model.VerifyCode;

/**
 * Created by edward-echo on 2016/4/12.
 */
public interface VerifyCodeMapper {

    void insert(VerifyCode verifyCode);

    VerifyCode checkSessionId(String sessionId);

    VerifyCode checkVerifyCode(VerifyCode verifyCode);

    void updateVerifyCode(VerifyCode verifyCode);

    void deleteVerifyCode(String uuid);

}
