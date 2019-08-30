package org.aimanj.protocol.pantheon.response;

import org.aimanj.protocol.core.Response;

public class PantheonFullDebugTraceResponse extends Response<FullDebugTraceInfo> {
    public FullDebugTraceInfo getFullDebugTraceInfo() {
        return getResult();
    }
}
