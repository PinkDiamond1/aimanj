package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_compileLLL.
 */
public class ManCompileLLL extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
