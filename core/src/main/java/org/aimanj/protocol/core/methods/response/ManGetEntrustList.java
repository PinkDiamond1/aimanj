package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

import java.util.List;
import java.util.Map;

/**
 *man_getBalance.
 */
public class ManGetEntrustList extends Response<List<Map<String, Object>>> {
    public List<Map<String, Object>> getEntrustList() {
        return getResult();
    }
}
