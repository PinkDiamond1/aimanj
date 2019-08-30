package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_mining.
 */
public class ManMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}
