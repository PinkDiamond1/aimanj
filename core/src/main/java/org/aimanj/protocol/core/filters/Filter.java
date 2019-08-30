package org.aimanj.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.ManFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.Response;
import org.aimanj.protocol.core.Response.Error;
import org.aimanj.protocol.core.RpcErrors;
import org.aimanj.protocol.core.methods.response.ManLog;
import org.aimanj.protocol.core.methods.response.ManUninstallFilter;


/**
 * Class for creating managed filter requests with callbacks.
 */
public abstract class Filter<T> {

    private static final Logger log = LoggerFactory.getLogger(Filter.class);

    final AiManj aiManj;
    final Callback<T> callback;

    private volatile BigInteger filterId;

    private ScheduledFuture<?> schedule;
    
    private ScheduledExecutorService scheduledExecutorService;

    private long blockTime;

    public Filter(AiManj aiManj, Callback<T> callback) {
        this.aiManj = aiManj;
        this.callback = callback;
    }

    public void run(ScheduledExecutorService scheduledExecutorService, long blockTime) {
        try {
            ManFilter manFilter = sendRequest();
            if (manFilter.hasError()) {
                throwException(manFilter.getError());
            }

            filterId = manFilter.getFilterId();
            this.scheduledExecutorService = scheduledExecutorService;
            this.blockTime = blockTime;
            // this runs in the caller thread as if any exceptions are encountered, we shouldn't
            // proceed with creating the scheduled task below
            getInitialFilterLogs();

            /*
            We want the filter to be resilient against client issues. On numerous occasions
            users have reported socket timeout exceptions when connected over HTTP to Gman and
            Parity clients. For examples, refer to
            https://github.com/aiManj/aiManj/issues/144 and
            https://github.com/Matrix/go-Matrix/issues/15243.

            Hence we consume errors and log them as errors, allowing our polling for changes to
            resume. The downside of this approach is that users will not be notified of
            downstream connection issues. But given the intermittent nature of the connection
            issues, this seems like a reasonable compromise.

            The alternative approach would be to have another thread that blocks waiting on
            schedule.get(), catching any Exceptions thrown, and passing them back up to the
            caller. However, the user would then be required to recreate subscriptions manually
            which isn't ideal given the aforementioned issues.
            */
            schedule = scheduledExecutorService.scheduleAtFixedRate(
                    () -> {
                        try {
                            this.pollFilter(manFilter);
                        } catch (Throwable e) {
                            // All exceptions must be caught, otherwise our job terminates without
                            // any notification
                            log.error("Error sending request", e);
                        }
                    },
                    0, blockTime, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            throwException(e);
        }
    }

    private void getInitialFilterLogs() {
        try {
            Optional<Request<?, ManLog>> maybeRequest = this.getFilterLogs(this.filterId);
            ManLog manLog = null;
            if (maybeRequest.isPresent()) {
                manLog = maybeRequest.get().send();
            } else {
                manLog = new ManLog();
                manLog.setResult(Collections.emptyList());
            }
            process(manLog.getLogs());

        } catch (IOException e) {
            throwException(e);
        }
    }

    private void pollFilter(ManFilter manFilter) {
        ManLog manLog = null;
        try {
            manLog = aiManj.manGetFilterChanges(filterId).send();
        } catch (IOException e) {
            throwException(e);
        }
        if (manLog.hasError()) {
            Error error = manLog.getError();
            switch (error.getCode()) {
                case RpcErrors.FILTER_NOT_FOUND: reinstallFilter();
                    break;
                default: throwException(error);
                    break;
            }
        } else {
            process(manLog.getLogs());
        }
    }

    abstract ManFilter sendRequest() throws IOException;

    abstract void process(List<ManLog.LogResult> logResults);
    
    private void reinstallFilter() {
        log.warn("The filter has not been found. Filter id: " + filterId);
        schedule.cancel(true);
        this.run(scheduledExecutorService, blockTime);
    }

    public void cancel() {
        schedule.cancel(false);

        try {
            ManUninstallFilter manUninstallFilter = aiManj.manUninstallFilter(filterId).send();
            if (manUninstallFilter.hasError()) {
                throwException(manUninstallFilter.getError());
            }

            if (!manUninstallFilter.isUninstalled()) {
                throw new FilterException("Filter with id '" + filterId + "' failed to uninstall");
            }
        } catch (IOException e) {
            throwException(e);
        }
    }

    /**
     * Retrieves historic filters for the filter with the given id.
     * Getting historic logs is not supported by all filters.
     * If not the method should return an empty ManLog object
     *
     * @param filterId Id of the filter for which the historic log should be retrieved
     * @return Historic logs, or an empty optional if the filter cannot retrieve historic logs
     */
    protected abstract Optional<Request<?, ManLog>> getFilterLogs(BigInteger filterId);

    void throwException(Response.Error error) {
        throw new FilterException("Invalid request: "
                + (error == null ? "Unknown Error" : error.getMessage()));
    }

    void throwException(Throwable cause) {
        throw new FilterException("Error sending request", cause);
    }
}
