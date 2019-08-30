package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;

import org.aimanj.protocol.core.Response;
import org.aimanj.utils.Numeric;

/**
 * man_estimateGas.
 */
public class ManEstimateGas extends Response<String> {
    public BigInteger getAmountUsed() {
        return Numeric.decodeQuantity(getResult());
    }
}
