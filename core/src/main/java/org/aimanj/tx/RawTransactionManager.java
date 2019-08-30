package org.aimanj.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.aimanj.crypto.Credentials;
import org.aimanj.crypto.Hash;
import org.aimanj.crypto.RawTransaction;
import org.aimanj.crypto.TransactionEncoder;
import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.methods.response.ManGetTransactionCount;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;
import org.aimanj.tx.exceptions.TxHashMismatchException;
import org.aimanj.tx.response.TransactionReceiptProcessor;
import org.aimanj.utils.Numeric;
import org.aimanj.utils.TxHashVerifier;

/**
 * TransactionManager implementation using Matrix wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/matrix/EIPs/issues/155">EIP155</a>, as well as for locally signing
 * RawTransaction instances without broadcasting them.
 */
public class RawTransactionManager extends TransactionManager {

    private final AiManj aiManj;
    final Credentials credentials;

    private final byte chainId;

    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public RawTransactionManager(AiManj aiManj, Credentials credentials, byte chainId) {
        super(aiManj, credentials.getAddress());

        this.aiManj = aiManj;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            AiManj aiManj, Credentials credentials, byte chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, credentials.getAddress());

        this.aiManj = aiManj;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            AiManj aiManj, Credentials credentials, byte chainId, int attempts, long sleepDuration) {
        super(aiManj, attempts, sleepDuration, credentials.getAddress());

        this.aiManj = aiManj;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(AiManj aiManj, Credentials credentials) {
        this(aiManj, credentials, ChainId.NONE);
    }

    public RawTransactionManager(
            AiManj aiManj, Credentials credentials, int attempts, int sleepDuration) {
        this(aiManj, credentials, ChainId.NONE, attempts, sleepDuration);
    }

    protected BigInteger getNonce() throws IOException {
        ManGetTransactionCount manGetTransactionCount = aiManj.manGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        return manGetTransactionCount.getTransactionCount();
    }

    public TxHashVerifier getTxHashVerifier() {
        return txHashVerifier;
    }

    public void setTxHashVerifier(TxHashVerifier txHashVerifier) {
        this.txHashVerifier = txHashVerifier;
    }

    @Override
    public ManSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, String currency, BigInteger value, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime, List extra_to) throws IOException {

        BigInteger nonce = getNonce();

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                to,
                value,
                data,
                currency,
                txEnterType,
                IsEntrustTx,
                commitTime,
                extra_to);

        return signAndSend(rawTransaction);
    }
    
    /*
     * @param rawTransaction a RawTransaction istance to be signed
     * @return The transaction signed and encoded without ever broadcasting it
     */
    public String sign(RawTransaction rawTransaction) {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        return Numeric.toHexString(signedMessage);
    }

    public ManSendTransaction signAndSend(RawTransaction rawTransaction)
            throws IOException {
        String hexValue = sign(rawTransaction);
        ManSendTransaction manSendTransaction = aiManj.manSendRawTransaction(hexValue).send();

        if (manSendTransaction != null && !manSendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(hexValue);
            String txHashRemote = manSendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }

        return manSendTransaction;
    }
}
