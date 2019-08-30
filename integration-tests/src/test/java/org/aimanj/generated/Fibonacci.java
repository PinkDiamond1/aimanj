package org.aimanj.generated;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.aimanj.abi.EventEncoder;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Event;
import org.aimanj.abi.datatypes.Function;
import org.aimanj.abi.datatypes.Type;
import org.aimanj.abi.datatypes.generated.Uint256;
import org.aimanj.crypto.Credentials;
import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameter;
import org.aimanj.protocol.core.RemoteCall;
import org.aimanj.protocol.core.methods.request.ManFilter;
import org.aimanj.protocol.core.methods.response.Log;
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
public class Fibonacci extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5061014f806100206000396000f30060806040526004361061004b5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416633c7fdc70811461005057806361047ff41461007a575b600080fd5b34801561005c57600080fd5b50610068600435610092565b60408051918252519081900360200190f35b34801561008657600080fd5b506100686004356100e0565b600061009d826100e0565b604080518481526020810183905281519293507f71e71a8458267085d5ab16980fd5f114d2d37f232479c245d523ce8d23ca40ed929081900390910190a1919050565b60008115156100f15750600061011e565b81600114156101025750600161011e565b61010e600283036100e0565b61011a600184036100e0565b0190505b9190505600a165627a7a723058201b9d0941154b95636fb5e4225fefd5c2c460060efa5f5e40c9826dce08814af80029";

    public static final String FUNC_FIBONACCINOTIFY = "fibonacciNotify";

    public static final String FUNC_FIBONACCI = "fibonacci";

    public static final Event NOTIFY_EVENT = new Event("Notify", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Fibonacci(String contractAddress, AiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    protected Fibonacci(String contractAddress, AiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, credentials, contractGasProvider);
    }

    @Deprecated
    protected Fibonacci(String contractAddress, AiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    protected Fibonacci(String contractAddress, AiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, aiManj, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> fibonacciNotify(BigInteger number) {
        final Function function = new Function(
                FUNC_FIBONACCINOTIFY, 
                Arrays.<Type>asList(new org.aimanj.abi.datatypes.generated.Uint256(number)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> fibonacci(BigInteger number) {
        final Function function = new Function(FUNC_FIBONACCI, 
                Arrays.<Type>asList(new org.aimanj.abi.datatypes.generated.Uint256(number)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<NotifyEventResponse> getNotifyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NOTIFY_EVENT, transactionReceipt);
        ArrayList<NotifyEventResponse> responses = new ArrayList<NotifyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NotifyEventResponse typedResponse = new NotifyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.input = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NotifyEventResponse> notifyEventFlowable(ManFilter filter) {
        return aiManj.manLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NotifyEventResponse>() {
            @Override
            public NotifyEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NOTIFY_EVENT, log);
                NotifyEventResponse typedResponse = new NotifyEventResponse();
                typedResponse.log = log;
                typedResponse.input = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NotifyEventResponse> notifyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        ManFilter filter = new ManFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NOTIFY_EVENT));
        return notifyEventFlowable(filter);
    }

    public static RemoteCall<Fibonacci> deploy(AiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Fibonacci.class, aiManj, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Fibonacci> deploy(AiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Fibonacci.class, aiManj, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Fibonacci> deploy(AiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Fibonacci.class, aiManj, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Fibonacci> deploy(AiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Fibonacci.class, aiManj, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static Fibonacci load(String contractAddress, AiManj aiManj, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fibonacci(contractAddress, aiManj, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Fibonacci load(String contractAddress, AiManj aiManj, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fibonacci(contractAddress, aiManj, transactionManager, gasPrice, gasLimit);
    }

    public static Fibonacci load(String contractAddress, AiManj aiManj, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Fibonacci(contractAddress, aiManj, credentials, contractGasProvider);
    }

    public static Fibonacci load(String contractAddress, AiManj aiManj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Fibonacci(contractAddress, aiManj, transactionManager, contractGasProvider);
    }

    public static class NotifyEventResponse {
        public Log log;

        public BigInteger input;

        public BigInteger result;
    }
}
