package org.aimanj.protocol;

import java.util.concurrent.ScheduledExecutorService;

import org.aimanj.protocol.core.Matrix;
import org.aimanj.protocol.core.JsonRpc2_0AiManj;
import org.aimanj.protocol.rx.AiManjRx;

/**
 * JSON-RPC Request object building factory.
 */
public interface AiManj extends Matrix, AiManjRx {

    /**
     * Construct a new AiManj instance.
     *
     * @param aiManjService aimanj service instance - i.e. HTTP or IPC
     * @return new AiManj instance
     */
    static AiManj build(AiManjService aiManjService) {
        return new JsonRpc2_0AiManj(aiManjService);
    }

    /**
     * Construct a new AiManj instance.
     *
     * @param aiManjService aimanj service instance - i.e. HTTP or IPC
     * @param pollingInterval polling interval for responses from network nodes
     * @param scheduledExecutorService executor service to use for scheduled tasks.
     *                                 <strong>You are responsible for terminating this thread
     *                                 pool</strong>
     * @return new AiManj instance
     */
    static AiManj build(
            AiManjService aiManjService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0AiManj(aiManjService, pollingInterval, scheduledExecutorService);
    }

    /**
     * Shutdowns a AiManj instance and closes opened resources.
     */
    void shutdown();
}
