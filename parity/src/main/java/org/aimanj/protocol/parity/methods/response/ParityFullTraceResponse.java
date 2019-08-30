package org.aimanj.protocol.parity.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * trace_rawTransaction
 * trace_replayTransaction.
 */
public class ParityFullTraceResponse extends Response<FullTraceInfo> {
    public FullTraceInfo getFullTraceInfo() {
        return getResult();
    }
}
