package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_protocolVersion.
 */
public class ManProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}
