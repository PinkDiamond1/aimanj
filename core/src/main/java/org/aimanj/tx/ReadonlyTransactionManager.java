package org.aimanj.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;

/**
 * Transaction manager implementation for read-only operations on smart contracts.
 */
public class ReadonlyTransactionManager extends TransactionManager {

    public ReadonlyTransactionManager(AiManj aiManj, String fromAddress) {
        super(aiManj, fromAddress);
    }

    @Override
    public ManSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, String currency, BigInteger value, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime, List extra_to)
            throws IOException {
        throw new UnsupportedOperationException(
                "Only read operations are supported by this transaction manager");
    }
}
