package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 *man_sign.
 */
public class ManSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
