package rml.service;

import rml.model.VerifyCode;

/**
 * Created by edward-echo on 2016/4/12.
 */
public interface VerifyCodeService {
    void insert(VerifyCode verifyCode);
    VerifyCode checkSessionId(String sessionId);
    VerifyCode checkVerifyCode(VerifyCode VerifyCode);
    void updateVerifyCode(VerifyCode verifyCode);
    void deleteVerifyCode(String uuid);
}
