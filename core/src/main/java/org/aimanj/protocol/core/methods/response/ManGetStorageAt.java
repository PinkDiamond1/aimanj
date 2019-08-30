package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_getStorageAt.
 */
public class ManGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
