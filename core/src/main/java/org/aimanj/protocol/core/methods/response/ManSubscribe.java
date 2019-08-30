package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

public class ManSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
