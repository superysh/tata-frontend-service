package rml.service;

import rml.model.Terminal;

/**
 * Created by edward-echo on 2016/3/25.
 */
public interface TerminalService {
    Terminal getVersion(String version);
    Terminal getMaxVersion();
}
