package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * net_version.
 */
public class NetVersion extends Response<String> {
    public String getNetVersion() {
        return getResult();
    }
}
