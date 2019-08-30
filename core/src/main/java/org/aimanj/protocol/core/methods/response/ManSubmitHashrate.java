package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_submitHashrate.
 */
public class ManSubmitHashrate extends Response<Boolean> {

    public boolean submissionSuccessful() {
        return getResult();
    }
}
