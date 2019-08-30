package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;

import org.aimanj.protocol.core.Response;
import org.aimanj.utils.Numeric;

/**
 * man_getBlockTransactionCountByHash.
 */
public class ManGetBlockTransactionCountByHash extends Response<String> {
    public BigInteger getTransactionCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
