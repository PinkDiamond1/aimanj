package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;

import org.aimanj.protocol.core.Response;
import org.aimanj.utils.Numeric;

/**
 * shh_newFilter.
 */
public class ShhNewFilter extends Response<String> {

    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
