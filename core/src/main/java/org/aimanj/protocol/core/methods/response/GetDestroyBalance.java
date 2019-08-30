package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * @program: base
 * @description:
 * @author: Li.Jie
 * @create: 2019-07-19 11:27
 **/
public class GetDestroyBalance extends Response<String> {
    public String getDestroyBalance() {
        return getResult();
    }
}
