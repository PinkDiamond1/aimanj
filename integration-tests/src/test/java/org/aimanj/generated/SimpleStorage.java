package org.aimanj.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Function;
import org.aimanj.abi.datatypes.Type;
import org.aimanj.abi.datatypes.generated.Uint256;
import org.aimanj.crypto.Credentials;
import org.aimanj.protocol.aiManj;
import org.aimanj.protocol.core.RemoteCall;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.tx.Contract;
import org.aimanj.tx.TransactionManager;
import org.aimanj.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.aiManj.io/command_line.html">aimanj command line tools</a>,
 * or the org.aimanj.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with aimanj version 4.0.1.
 */
public class SimpleStorage extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50600560005560bf806100246000396000f30060806040526004361060485763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166360fe47b18114604d5780636d4ce63c146064575b600080fd5b348015605857600080fd5b5060626004356088565b005b348015606f57600080fd5b506076608d565b60408051918252519081900360200190f35b600055565b600054905600a165627a7a72305820419b352168794764ac1d5d6d3460eaffedc13c00bcbb4d2ff772148d2f0670fc0029";

    public static final String FUNC_SET = "set";

    public static final String FUNC_GET = "get";

    @Deprecated
    protected SimpleStorage(String contractAddress, aiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    protected SimpleStorage(String contractAddress, aiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, credentials, contractGasProvider);
    }

    @Deprecated
    protected SimpleStorage(String contractAddress, aiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    protected SimpleStorage(String contractAddress, aiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> set(BigInteger x) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.aimanj.abi.datatypes.generated.Uint256(x)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> get() {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<SimpleStorage> deploy(aiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleStorage.class, aiManj, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SimpleStorage> deploy(aiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleStorage.class, aiManj, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleStorage> deploy(aiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleStorage.class, aiManj, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleStorage> deploy(aiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleStorage.class, aiManj, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static SimpleStorage load(String contractAddress, aiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleStorage(contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SimpleStorage load(String contractAddress, aiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleStorage(contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    public static SimpleStorage load(String contractAddress, aiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SimpleStorage(contractAddress, aiManj, credentials, contractGasProvider);
    }

    public static SimpleStorage load(String contractAddress, aiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SimpleStorage(contractAddress, aiManj, transactionManager, contractGasProvider);
    }
}
