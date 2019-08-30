package org.aimanj.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.protocol.exceptions.TransactionException;
import org.aimanj.tx.response.PollingTransactionReceiptProcessor;
import org.aimanj.tx.response.TransactionReceiptProcessor;

import static org.aimanj.protocol.core.JsonRpc2_0AiManj.DEFAULT_BLOCK_TIME;

/**
 * Transaction manager abstraction for executing transactions with Matrix client via
 * various mechanisms.
 */
public abstract class TransactionManager {

    public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = 40;
    public static final long DEFAULT_POLLING_FREQUENCY = DEFAULT_BLOCK_TIME;

    private final TransactionReceiptProcessor transactionReceiptProcessor;
    private final String fromAddress;

    protected TransactionManager(
            TransactionReceiptProcessor transactionReceiptProcessor, String fromAddress) {
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.fromAddress = fromAddress;
    }

    protected TransactionManager(AiManj aiManj, String fromAddress) {
        this(new PollingTransactionReceiptProcessor(
                        aiManj, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                fromAddress);
    }

    protected TransactionManager(
            AiManj aiManj, int attempts, long sleepDuration, String fromAddress) {
        this(new PollingTransactionReceiptProcessor(aiManj, sleepDuration, attempts), fromAddress);
    }

    protected TransactionReceipt executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, String currency, BigInteger value, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime, List extra_to)
            throws IOException, TransactionException {

        ManSendTransaction manSendTransaction = sendTransaction(
                gasPrice, gasLimit, to, data, currency, value, txEnterType, IsEntrustTx, commitTime, extra_to);
        return processResponse(manSendTransaction);
    }

    public abstract ManSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, String currency, BigInteger value, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime, List extra_to)
            throws IOException;

    public String getFromAddress() {
        return fromAddress;
    }

    private TransactionReceipt processResponse(ManSendTransaction transactionResponse)
            throws IOException, TransactionException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: "
                    + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();

        return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
    }


}
