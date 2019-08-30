package org.aimanj.tx;

import java.math.BigDecimal;

import org.junit.Test;

import org.aimanj.crypto.SampleKeys;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.tx.exceptions.TxHashMismatchException;
import org.aimanj.utils.Convert;

public class RawTransactionManagerTest extends ManagedTransactionTester {

    @Test(expected = TxHashMismatchException.class)
    public void testTxHashMismatch() throws Exception {
        TransactionReceipt transactionReceipt = prepareTransfer();
        prepareTransaction(transactionReceipt);

        TransactionManager transactionManager =
                new RawTransactionManager(aiManj, SampleKeys.CREDENTIALS);
        Transfer transfer = new Transfer(aiManj, transactionManager);
        transfer.sendFunds(ADDRESS, BigDecimal.ONE, Convert.Unit.MAN).send();
    }
}
