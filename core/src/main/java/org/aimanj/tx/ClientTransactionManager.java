package org.aimanj.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;
import org.aimanj.tx.response.TransactionReceiptProcessor;

/**
 * TransactionManager implementation for using an Matirx node to transact.
 *
 * <p><b>Note</b>: accounts must be unlocked on the node for transactions to be successful.
 */
public class ClientTransactionManager extends TransactionManager {

    private final AiManj aiManj;

    public ClientTransactionManager(
            AiManj aiManj, String fromAddress) {
        super(aiManj, fromAddress);
        this.aiManj = aiManj;
    }


    public ClientTransactionManager(
            AiManj aiManj, String fromAddress, int attempts, int sleepDuration) {
        super(aiManj, attempts, sleepDuration, fromAddress);
        this.aiManj = aiManj;
    }

    public ClientTransactionManager(
            AiManj aiManj, String fromAddress,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, fromAddress);
        this.aiManj = aiManj;
    }
    @Override
    public ManSendTransaction sendTransaction(BigInteger gasPrice, BigInteger gasLimit, String to, String data, String currency,
                                              BigInteger value, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime, List extra_to) throws IOException {
        Transaction transaction = new Transaction(
                getFromAddress(), null, gasPrice, gasLimit, to, value, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);

        return aiManj.manSendTransaction(transaction)
                .send();
    }
}
