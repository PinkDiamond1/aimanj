package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.aimanj.protocol.core.Response;
import org.aimanj.utils.Numeric;

/**
 * man_getBalance.
 */
public class ManGetBalance extends Response<List<Map<String, Object>>> {
    public List<Map<String, BigInteger>> getBalance() {
        List data = getResult();
        for (int i = 0, length = data.size(); i < length; i++) {
            ((Map)data.get(i)).replace("balance", Numeric.decodeQuantity(String.valueOf(((Map)data.get(i)).get("balance"))));
        }
        return data;
    }
}
