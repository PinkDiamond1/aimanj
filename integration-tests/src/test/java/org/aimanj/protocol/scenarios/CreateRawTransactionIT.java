package org.aimanj.protocol.scenarios;

import java.math.BigInteger;

import org.junit.Test;

import org.aimanj.crypto.RawTransaction;
import org.aimanj.crypto.TransactionEncoder;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.methods.response.ManGetTransactionCount;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.utils.Convert;
import org.aimanj.utils.Numeric;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Create, sign and send a raw transaction.
 */
public class CreateRawTransactionIT extends Scenario {

    @Test
    public void testTransferMan() throws Exception {
        BigInteger nonce = getNonce(ALICE.getAddress());
        RawTransaction rawTransaction = createManTransaction(
                nonce, BOB.getAddress());

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);

        ManSendTransaction manSendTransaction =
                aiManj.manSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = manSendTransaction.getTransactionHash();

        assertFalse(transactionHash.isEmpty());

        TransactionReceipt transactionReceipt =
                waitForTransactionReceipt(transactionHash);

        assertThat(transactionReceipt.getTransactionHash(), is(transactionHash));
    }

    @Test
    public void testDeploySmartContract() throws Exception {
        BigInteger nonce = getNonce(ALICE.getAddress());
        RawTransaction rawTransaction = createSmartContractTransaction(nonce);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);

        ManSendTransaction manSendTransaction =
                aiManj.manSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = manSendTransaction.getTransactionHash();

        assertFalse(transactionHash.isEmpty());

        TransactionReceipt transactionReceipt =
                waitForTransactionReceipt(transactionHash);

        assertThat(transactionReceipt.getTransactionHash(), is(transactionHash));

        assertFalse("Contract execution ran out of gas",
                rawTransaction.getGasLimit().equals(transactionReceipt.getGasUsed()));
    }

    private static RawTransaction createManTransaction(BigInteger nonce, String toAddress) {
        BigInteger value = Convert.toWei("0.5", Convert.Unit.MAN).toBigInteger();

        return RawTransaction.createManTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, toAddress, "", "MAN", value, BigInteger.ZERO
        ,BigInteger.ZERO,BigInteger.ZERO,null);
    }

    private static RawTransaction createSmartContractTransaction(BigInteger nonce)
            throws Exception {
        return RawTransaction.createContractTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, BigInteger.ZERO, getFibonacciSolidityBinary(), "MAN"
                , BigInteger.ZERO
                ,BigInteger.ZERO,BigInteger.ZERO,null);
    }

    BigInteger getNonce(String address) throws Exception {
        ManGetTransactionCount manGetTransactionCount = aiManj.manGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();

        return manGetTransactionCount.getTransactionCount();
    }
}
