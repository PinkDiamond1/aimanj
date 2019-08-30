package org.aimanj.protocol.core.methods.response;

import java.util.List;

import org.aimanj.protocol.core.Response;

/**
 * man_accounts.
 */
public class ManAccounts extends Response<List<List<String>>> {
    public List<List<String>> getAccounts() {
        return getResult();
    }
}
