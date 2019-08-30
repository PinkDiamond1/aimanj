package org.aimanj.tx;

import java.io.IOException;

import org.aimanj.protocol.AiManj;
import org.junit.Before;

import org.aimanj.crypto.Credentials;
import org.aimanj.crypto.SampleKeys;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManGasPrice;
import org.aimanj.protocol.core.methods.response.ManGetTransactionCount;
import org.aimanj.protocol.core.methods.response.ManGetTransactionReceipt;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.utils.TxHashVerifier;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public abstract class ManagedTransactionTester {

    static final String ADDRESS = "0x3d6cb163f7c72d20b0fcd6baae5889329d138a4a";
    static final String TRANSACTION_HASH = "0xHASH";
    protected AiManj aiManj;
    protected TxHashVerifier txHashVerifier;

    @Before
    public void setUp() throws Exception {
        aiManj = mock(AiManj.class);
        txHashVerifier = mock(TxHashVerifier.class);
        when(txHashVerifier.verify(any(), any())).thenReturn(true);
    }

    public TransactionManager getVerifiedTransactionManager(Credentials credentials,
                                                            int attempts, int sleepDuration) {
        RawTransactionManager transactionManager =
                new RawTransactionManager(aiManj, credentials, attempts, sleepDuration);
        transactionManager.setTxHashVerifier(txHashVerifier);
        return transactionManager;
    }

    public TransactionManager getVerifiedTransactionManager(Credentials credentials) {
        RawTransactionManager transactionManager = new RawTransactionManager(aiManj, credentials);
        transactionManager.setTxHashVerifier(txHashVerifier);
        return transactionManager;
    }

    void prepareTransaction(TransactionReceipt transactionReceipt) throws IOException {
        prepareNonceRequest();
        prepareTransactionRequest();
        prepareTransactionReceipt(transactionReceipt);
    }

    @SuppressWarnings("unchecked")
    void prepareNonceRequest() throws IOException {
        ManGetTransactionCount manGetTransactionCount = new ManGetTransactionCount();
        manGetTransactionCount.setResult("0x1");

        Request<?, ManGetTransactionCount> transactionCountRequest = mock(Request.class);
        when(transactionCountRequest.send())
                .thenReturn(manGetTransactionCount);
        when(aiManj.manGetTransactionCount(SampleKeys.ADDRESS, DefaultBlockParameterName.PENDING))
                .thenReturn((Request) transactionCountRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionRequest() throws IOException {
        ManSendTransaction manSendTransaction = new ManSendTransaction();
        manSendTransaction.setResult(TRANSACTION_HASH);

        Request<?, ManSendTransaction> rawTransactionRequest = mock(Request.class);
        when(rawTransactionRequest.send()).thenReturn(manSendTransaction);
        when(aiManj.manSendRawTransaction(any(String.class)))
                .thenReturn((Request) rawTransactionRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionReceipt(TransactionReceipt transactionReceipt) throws IOException {
        ManGetTransactionReceipt manGetTransactionReceipt = new ManGetTransactionReceipt();
        manGetTransactionReceipt.setResult(transactionReceipt);

        Request<?, ManGetTransactionReceipt> getTransactionReceiptRequest = mock(Request.class);
        when(getTransactionReceiptRequest.send())
                .thenReturn(manGetTransactionReceipt);
        when(aiManj.manGetTransactionReceipt(TRANSACTION_HASH))
                .thenReturn((Request) getTransactionReceiptRequest);
    }

    @SuppressWarnings("unchecked")
    protected TransactionReceipt prepareTransfer() throws IOException {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash(TRANSACTION_HASH);
        transactionReceipt.setStatus("0x1");
        prepareTransaction(transactionReceipt);

        ManGasPrice manGasPrice = new ManGasPrice();
        manGasPrice.setResult("0x1");

        Request<?, ManGasPrice> gasPriceRequest = mock(Request.class);
        when(gasPriceRequest.send()).thenReturn(manGasPrice);
        when(aiManj.manGasPrice()).thenReturn((Request) gasPriceRequest);

        return transactionReceipt;
    }
}
