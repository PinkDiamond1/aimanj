package org.aimanj.tx.response;

import java.io.IOException;

import org.aimanj.protocol.AiManj;
import org.junit.Before;
import org.junit.Test;

import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.Response;
import org.aimanj.protocol.core.methods.response.ManGetTransactionReceipt;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.protocol.exceptions.TransactionException;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PollingTransactionReceiptProcessorTest {
    private static final String TRANSACTION_HASH = "0x00";
    private AiManj aiManj;
    private long sleepDuration;
    private int attempts;
    private PollingTransactionReceiptProcessor processor;

    @Before
    public void setUp() {
        aiManj = mock(AiManj.class);
        sleepDuration = 100;
        attempts = 3;
        processor = new PollingTransactionReceiptProcessor(aiManj, sleepDuration, attempts);
    }

    @Test
    public void returnsTransactionReceiptWhenItIsAvailableInstantly() throws Exception {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        doReturn(requestReturning(response(transactionReceipt)))
                .when(aiManj).manGetTransactionReceipt(TRANSACTION_HASH);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(TRANSACTION_HASH);

        assertThat(receipt, sameInstance(transactionReceipt));
    }

    @Test
    public void throwsTransactionExceptionWhenReceiptIsNotAvailableInTime() throws Exception {
        doReturn(requestReturning(response(null)))
                .when(aiManj).manGetTransactionReceipt(TRANSACTION_HASH);

        try {
            processor.waitForTransactionReceipt(TRANSACTION_HASH);
            fail("call should fail with TransactionException");
        } catch (TransactionException e) {
            assertTrue(e.getTransactionHash().isPresent());
            assertEquals(e.getTransactionHash().get(), TRANSACTION_HASH);
        }
    }

    private static <T extends Response<?>> Request<String, T> requestReturning(T response) {
        Request<String, T> request = mock(Request.class);
        try {
            when(request.send()).thenReturn(response);
        } catch (IOException e) {
            // this will never happen
        }
        return request;
    }

    private static ManGetTransactionReceipt response(TransactionReceipt transactionReceipt) {
        ManGetTransactionReceipt response = new ManGetTransactionReceipt();
        response.setResult(transactionReceipt);
        return response;
    }
}
