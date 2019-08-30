package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_getCode.
 */
public class ManGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}
