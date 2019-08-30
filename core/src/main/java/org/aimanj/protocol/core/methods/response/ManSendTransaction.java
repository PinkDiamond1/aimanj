package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_sendTransaction.
 */
public class ManSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
