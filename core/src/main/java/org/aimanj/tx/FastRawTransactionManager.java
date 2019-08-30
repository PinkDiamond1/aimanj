package org.aimanj.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.aimanj.crypto.Credentials;
import org.aimanj.protocol.AiManj;
import org.aimanj.tx.response.TransactionReceiptProcessor;

/**
 * Simple RawTransactionManager derivative that manages nonces to facilitate multiple transactions
 * per block.
 */
public class FastRawTransactionManager extends RawTransactionManager {

    private volatile BigInteger nonce = BigInteger.valueOf(-1);

    public FastRawTransactionManager(AiManj aiManj, Credentials credentials, byte chainId) {
        super(aiManj, credentials, chainId);
    }

    public FastRawTransactionManager(AiManj aiManj, Credentials credentials) {
        super(aiManj, credentials);
    }

    public FastRawTransactionManager(
            AiManj aiManj, Credentials credentials,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(aiManj, credentials, ChainId.NONE, transactionReceiptProcessor);
    }

    public FastRawTransactionManager(
            AiManj aiManj, Credentials credentials, byte chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(aiManj, credentials, chainId, transactionReceiptProcessor);
    }

    @Override
    protected synchronized BigInteger getNonce() throws IOException {
        if (nonce.signum() == -1) {
            // obtain lock
            nonce = super.getNonce();
        } else {
            nonce = nonce.add(BigInteger.ONE);
        }
        return nonce;
    }

    public BigInteger getCurrentNonce() {
        return nonce;
    }

    public synchronized void resetNonce() throws IOException {
        nonce = super.getNonce();
    }

    public synchronized void setNonce(BigInteger value) {
        nonce = value;
    }
}
