package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 *man_compileSerpent.
 */
public class ManCompileSerpent extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
