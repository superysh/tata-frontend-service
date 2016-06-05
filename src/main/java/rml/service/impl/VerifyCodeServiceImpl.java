package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.VerifyCodeMapper;
import rml.model.VerifyCode;
import rml.service.VerifyCodeService;

/**
 * Created by edward-echo on 2016/4/12.
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    @Override
    public void insert(VerifyCode verifyCode) {
        verifyCodeMapper.insert(verifyCode);
    }

    @Override
    public VerifyCode checkSessionId(String sessionId) {
        return  verifyCodeMapper.checkSessionId(sessionId);
    }

    @Override
    public VerifyCode checkVerifyCode(VerifyCode VerifyCode) {
        return verifyCodeMapper.checkVerifyCode(VerifyCode);
    }

    @Override
    public void updateVerifyCode(VerifyCode verifyCode) {
        verifyCodeMapper.updateVerifyCode(verifyCode);
    }

    @Override
    public void deleteVerifyCode(String uuid) {
        verifyCodeMapper.deleteVerifyCode(uuid);
    }
}
