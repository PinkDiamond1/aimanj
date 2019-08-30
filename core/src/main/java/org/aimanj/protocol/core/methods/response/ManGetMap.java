package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

import java.util.Map;

/**
 * @program: base
 * @description:
 * @author: Li.Jie
 * @create: 2019-07-19 11:44
 **/
public class ManGetMap extends Response<Map<String, Object>> {
    public Map<String, Object> getResultMap() {
        return getResult();
    }
}
