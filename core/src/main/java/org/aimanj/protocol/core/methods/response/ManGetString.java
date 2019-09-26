package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;
import org.aimanj.utils.Numeric;

import java.math.BigInteger;

public class ManGetString extends Response<String> {
    public String getData() {
        return getResult();
    }
}
