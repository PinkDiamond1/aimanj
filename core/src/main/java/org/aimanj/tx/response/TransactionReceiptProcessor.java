package org.aimanj.tx.response;

import java.io.IOException;
import java.util.Optional;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.ManGetTransactionReceipt;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.protocol.exceptions.TransactionException;

/**
 * Abstraction for managing how we wait for transaction receipts to be generated on the network.
 */
public abstract class TransactionReceiptProcessor {

    private final AiManj aiManj;

    public TransactionReceiptProcessor(AiManj aiManj) {
        this.aiManj = aiManj;
    }

    public abstract TransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws IOException, TransactionException;

    Optional<TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws IOException, TransactionException {
        ManGetTransactionReceipt transactionReceipt =
                aiManj.manGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new TransactionException("Error processing request: "
                    + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
