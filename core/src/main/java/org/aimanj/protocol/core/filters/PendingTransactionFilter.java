package org.aimanj.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManFilter;
import org.aimanj.protocol.core.methods.response.ManLog;

/**
 * Handler for working with transaction filter requests.
 */
public class PendingTransactionFilter extends Filter<String> {

    public PendingTransactionFilter(AiManj aiManj, Callback<String> callback) {
        super(aiManj, callback);
    }

    @Override
    ManFilter sendRequest() throws IOException {
        return aiManj.manNewPendingTransactionFilter().send();
    }

    @Override
    void process(List<ManLog.LogResult> logResults) {
        for (ManLog.LogResult logResult : logResults) {
            if (logResult instanceof ManLog.Hash) {
                String transactionHash = ((ManLog.Hash) logResult).get();
                callback.onEvent(transactionHash);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }
        }
    }

    /**
     * Since the pending transaction filter does not support historic filters,
     * the filterId is ignored and an empty optional is returned
     * @param filterId
     * Id of the filter for which the historic log should be retrieved
     * @return
     * Optional.empty()
     */
    @Override
    protected Optional<Request<?, ManLog>> getFilterLogs(BigInteger filterId) {
        return Optional.empty();
    }
}

