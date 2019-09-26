package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

import java.util.Map;

public class ManGetMap extends Response<Map<String, Object>> {
    public Map<String, Object> getResultMap() {
        return getResult();
    }
}
