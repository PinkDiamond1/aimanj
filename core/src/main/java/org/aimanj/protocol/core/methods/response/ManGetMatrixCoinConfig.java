package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

import java.util.*;

public class ManGetMatrixCoinConfig extends Response<List<Map<String, Object>>> {


    public List<Map<String, Object>> getMatrixCoinConfig() {
        return getResult();
    }

}
