package org.aimanj.contracts.eip165.generated;

import java.math.BigInteger;
import java.util.Arrays;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Bool;
import org.aimanj.abi.datatypes.Function;
import org.aimanj.abi.datatypes.Type;
import org.aimanj.crypto.Credentials;
import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.RemoteCall;
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
 * <p>Generated with aimanj version 4.1.1.
 */
public class ERC165 extends Contract {
    private static final String BINARY = "Bin file was not provided";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    @Deprecated
    protected ERC165(String contractAddress, AiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    protected ERC165(String contractAddress, AiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC165(String contractAddress, AiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    protected ERC165(String contractAddress, AiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, transactionManager, contractGasProvider);
    }

    public RemoteCall<Boolean> supportsInterface(byte[] interfaceID) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.aimanj.abi.datatypes.generated.Bytes4(interfaceID)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static ERC165 load(String contractAddress, AiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC165(contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC165 load(String contractAddress, AiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC165(contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    public static ERC165 load(String contractAddress, AiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC165(contractAddress, aiManj, credentials, contractGasProvider);
    }

    public static ERC165 load(String contractAddress, AiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC165(contractAddress, aiManj, transactionManager, contractGasProvider);
    }
}
