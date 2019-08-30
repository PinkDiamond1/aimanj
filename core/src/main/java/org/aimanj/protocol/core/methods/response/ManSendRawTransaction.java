package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_sendRawTransaction.
 */
public class ManSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
