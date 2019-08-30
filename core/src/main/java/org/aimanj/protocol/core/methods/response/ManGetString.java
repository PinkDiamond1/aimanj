package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;
import org.aimanj.utils.Numeric;

import java.math.BigInteger;

/**
 * @program: base
 * @description:
 * @author: Li.Jie
 * @create: 2019-07-19 14:11
 **/
public class ManGetString extends Response<String> {
    public String getData() {
        return getResult();
    }
}
