package org.aimanj.protocol.core.methods.response;

import java.util.List;

import org.aimanj.protocol.core.Response;

/**
 *man_getWork.
 */
public class ManGetWork extends Response<List<String>> {

    public String getCurrentBlockHeaderPowHash() {
        return getResult().get(0);
    }

    public String getSeedHashForDag() {
        return getResult().get(1);
    }

    public String getBoundaryCondition() {
        return getResult().get(2);
    }
}
