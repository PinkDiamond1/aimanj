package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * db_getString.
 */
public class DbGetString extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
