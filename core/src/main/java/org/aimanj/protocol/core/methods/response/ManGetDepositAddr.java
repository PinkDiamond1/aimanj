package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;
import java.util.Map;

/**
 *man_getBalance.
 */
public class ManGetDepositAddr extends Response<Map<String, Object>> {
    public Map<String, Object> getDeposit() {
        return getResult();
    }
}
