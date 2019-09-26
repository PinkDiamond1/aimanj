package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

public class GetDestroyBalance extends Response<String> {
    public String getDestroyBalance() {
        return getResult();
    }
}
