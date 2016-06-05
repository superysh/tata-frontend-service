package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.TerminalMapper;
import rml.model.Terminal;
import rml.service.TerminalService;

/**
 * Created by edward-echo on 2016/3/25.
 */
@Service("terminalService")
public class TerminalServiceImpl implements TerminalService {

    @Autowired
    private TerminalMapper terminalMapper;

    @Override
    public Terminal getVersion(String version) {
        return terminalMapper.getVersion(version);
    }

    @Override
    public Terminal getMaxVersion() {
        return terminalMapper.getMaxVersion();
    }
}
