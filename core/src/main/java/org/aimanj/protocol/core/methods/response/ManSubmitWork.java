package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_submitWork.
 */
public class ManSubmitWork extends Response<Boolean> {

    public boolean solutionValid() {
        return getResult();
    }
}
