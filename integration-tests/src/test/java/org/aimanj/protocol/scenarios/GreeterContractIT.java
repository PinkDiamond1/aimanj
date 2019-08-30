package org.aimanj.protocol.scenarios;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.aimanj.protocol.core.methods.response.ManCall;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;
import org.junit.Test;

import org.aimanj.abi.FunctionEncoder;
import org.aimanj.abi.FunctionReturnDecoder;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Function;
import org.aimanj.abi.datatypes.Type;
import org.aimanj.abi.datatypes.Utf8String;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Integration test demonstrating integration with Greeter contract taken from the
 * <a href="https://github.com/Matrix/go-Matrix/wiki/Contract-Tutorial">Contract Tutorial</a>
 * on the Go Matrix Wiki.
 */
public class GreeterContractIT extends Scenario {

    private static final String VALUE = "Greetings!";

    @Test
    public void testGreeterContract() throws Exception {
        boolean accountUnlocked = unlockAccount();
        assertTrue(accountUnlocked);

        // create our smart contract
        String createTransactionHash = sendCreateContractTransaction();
        assertFalse(createTransactionHash.isEmpty());

        TransactionReceipt createTransactionReceipt =
                waitForTransactionReceipt(createTransactionHash);

        assertThat(createTransactionReceipt.getTransactionHash(), is(createTransactionHash));

        assertFalse("Contract execution ran out of gas",
                createTransactionReceipt.getGasUsed().equals(GAS_LIMIT));

        String contractAddress = createTransactionReceipt.getContractAddress();

        assertNotNull(contractAddress);

        // call our getter
        Function getFunction = createGreetFunction();
        String responseValue = callSmartContractFunction(getFunction, contractAddress);
        assertFalse(responseValue.isEmpty());

        List<Type> response = FunctionReturnDecoder.decode(
                responseValue, getFunction.getOutputParameters());
        assertThat(response.size(), is(1));
        assertThat(response.get(0).getValue(), is(VALUE));
    }

    private String sendCreateContractTransaction() throws Exception {
        BigInteger nonce = getNonce(ALICE.getAddress());

        String encodedConstructor =
                FunctionEncoder.encodeConstructor(Collections.singletonList(new Utf8String(VALUE)));

        Transaction transaction = Transaction.createContractTransaction(
                ALICE.getAddress(),
                nonce,
                GAS_PRICE,
                GAS_LIMIT,
                BigInteger.ZERO,
                getGreeterSolidityBinary() + encodedConstructor,
                "MAN",
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                null
                );

        ManSendTransaction
                transactionResponse = aiManj.manSendTransaction(transaction)
                .sendAsync().get();

        return transactionResponse.getTransactionHash();
    }

    private String callSmartContractFunction(
            Function function, String contractAddress) throws Exception {

        String encodedFunction = FunctionEncoder.encode(function);

        ManCall response = aiManj.manCall(
                Transaction.createManCallTransaction(
                        ALICE.getAddress(), contractAddress, encodedFunction, "MAN"),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();

        return response.getValue();
    }

    private static String getGreeterSolidityBinary() throws Exception {
        return load("/solidity/greeter/build/Greeter.bin");
    }

    Function createGreetFunction() {
        return new Function(
                "greet",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Utf8String>() {}));
    }
}
