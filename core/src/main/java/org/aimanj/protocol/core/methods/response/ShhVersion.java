package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * shh_version.
 */
public class ShhVersion extends Response<String> {

    public String getVersion() {
        return getResult();
    }
}
