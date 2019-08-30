package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;

import org.aimanj.protocol.core.Response;
import org.aimanj.utils.Numeric;

/**
 * man_getUncleCountByBlockHash.
 */
public class ManGetUncleCountByBlockHash extends Response<String> {
    public BigInteger getUncleCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
