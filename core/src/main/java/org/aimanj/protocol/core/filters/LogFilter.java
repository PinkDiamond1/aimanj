package org.aimanj.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManFilter;
import org.aimanj.protocol.core.methods.response.ManLog;
import org.aimanj.protocol.core.methods.response.Log;

/**
 * Log filter handler.
 */
public class LogFilter extends Filter<Log> {

    private final org.aimanj.protocol.core.methods.request.ManFilter manFilter;

    public LogFilter(
            AiManj aiManj, Callback<Log> callback,
            org.aimanj.protocol.core.methods.request.ManFilter manFilter) {
        super(aiManj, callback);
        this.manFilter = manFilter;
    }


    @Override
    ManFilter sendRequest() throws IOException {
        return aiManj.manNewFilter(manFilter).send();
    }

    @Override
    void process(List<ManLog.LogResult> logResults) {
        for (ManLog.LogResult logResult : logResults) {
            if (logResult instanceof ManLog.LogObject) {
                Log log = ((ManLog.LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }
        }
    }

    @Override
    protected Optional<Request<?, ManLog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(aiManj.manGetFilterLogs(filterId));
    }
}
