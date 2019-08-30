package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_coinbase.
 */
public class ManCoinbase extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
