package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

import java.util.List;

public class ManGetMatrixCoin extends Response<List<String>> {

    public List<String> getMatrixCoin() {
        return getResult();
    }
}
