package org.aimanj.protocol.core.methods.response;

import java.util.List;

import org.aimanj.protocol.core.Response;

/**
 * man_getCompilers.
 */
public class ManGetCompilers extends Response<List<String>> {
    public List<String> getCompilers() {
        return getResult();
    }
}
