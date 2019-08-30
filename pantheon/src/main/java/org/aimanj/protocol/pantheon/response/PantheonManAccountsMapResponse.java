package org.aimanj.protocol.pantheon.response;

import java.util.Map;

import org.aimanj.protocol.core.Response;


public class PantheonManAccountsMapResponse extends Response<Map<String, Boolean>> {
    public Map<String, Boolean> getAccounts() {
        return getResult();
    }
}
