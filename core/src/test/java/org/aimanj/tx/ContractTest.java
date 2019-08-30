package org.aimanj.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import org.aimanj.abi.EventEncoder;
import org.aimanj.abi.EventValues;
import org.aimanj.abi.FunctionEncoder;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Address;
import org.aimanj.abi.datatypes.Event;
import org.aimanj.abi.datatypes.Function;
import org.aimanj.abi.datatypes.Type;
import org.aimanj.abi.datatypes.Utf8String;
import org.aimanj.abi.datatypes.generated.Uint256;
import org.aimanj.crypto.Credentials;
import org.aimanj.crypto.SampleKeys;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.RemoteCall;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.Response;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.core.methods.response.ManCall;
import org.aimanj.protocol.exceptions.TransactionException;
import org.aimanj.tx.gas.ContractGasProvider;
import org.aimanj.tx.gas.DefaultGasProvider;
import org.aimanj.tx.gas.StaticGasProvider;
import org.aimanj.utils.Async;
import org.aimanj.utils.Numeric;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContractTest extends ManagedTransactionTester {

    private static final String TEST_CONTRACT_BINARY = "12345";

    private TestContract contract;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        super.setUp();

        contract = new TestContract(
                ADDRESS, aiManj, getVerifiedTransactionManager(SampleKeys.CREDENTIALS),
                new DefaultGasProvider());
    }

    @Test
    public void testGetContractAddress() {
        assertThat(contract.getContractAddress(), is(ADDRESS));
    }

    @Test
    public void testGetContractTransactionReceipt() {
        assertFalse(contract.getTransactionReceipt().isPresent());
    }

    @Test
    public void testDeploy() throws Exception {
        TransactionReceipt transactionReceipt = createTransactionReceipt();
        Contract deployedContract = deployContract(transactionReceipt);

        assertThat(deployedContract.getContractAddress(), is(ADDRESS));
        assertTrue(deployedContract.getTransactionReceipt().isPresent());
        assertThat(deployedContract.getTransactionReceipt().get(), equalTo(transactionReceipt));
    }

    @Test
    public void testContractDeployFails() throws Exception {
        thrown.expect(TransactionException.class);
        thrown.expectMessage(
                "Transaction has failed with status: 0x0. Gas used: 1. (not-enough gas?)");
        TransactionReceipt transactionReceipt = createFailedTransactionReceipt();
        deployContract(transactionReceipt);
    }

    @Test
    public void testContractDeployWithNullStatusSucceeds() throws Exception {
        TransactionReceipt transactionReceipt = createTransactionReceiptWithStatus(null);
        Contract deployedContract = deployContract(transactionReceipt);

        assertThat(deployedContract.getContractAddress(), is(ADDRESS));
        assertTrue(deployedContract.getTransactionReceipt().isPresent());
        assertThat(deployedContract.getTransactionReceipt().get(), equalTo(transactionReceipt));
    }

    @Test
    public void testIsValid() throws Exception {
        prepareManGetCode(TEST_CONTRACT_BINARY);

        Contract contract = deployContract(createTransactionReceipt());
        assertTrue(contract.isValid());
    }

    @Test
    public void testIsValidSkipMetadata() throws Exception {
        prepareManGetCode(TEST_CONTRACT_BINARY
                + "a165627a7a72305820"
                + "a9bc86938894dc250f6ea25dd823d4472fad6087edcda429a3504e3713a9fc880029");

        Contract contract = deployContract(createTransactionReceipt());
        assertTrue(contract.isValid());
    }

    @Test
    public void testIsValidDifferentCode() throws Exception {
        prepareManGetCode(TEST_CONTRACT_BINARY + "0");

        Contract contract = deployContract(createTransactionReceipt());
        assertFalse(contract.isValid());
    }

    @Test
    public void testIsValidEmptyCode() throws Exception {
        prepareManGetCode("");

        Contract contract = deployContract(createTransactionReceipt());
        assertFalse(contract.isValid());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsValidNoBinThrows() throws Exception {
        TransactionManager txManager = mock(TransactionManager.class);
        TestContract contract = new TestContract(
                Contract.BIN_NOT_PROVIDED, ADDRESS, aiManj, txManager,
                new DefaultGasProvider());
        contract.isValid();
    }

    @Test(expected = RuntimeException.class)
    public void testDeployInvalidContractAddress() throws Throwable {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash(TRANSACTION_HASH);

        prepareTransaction(transactionReceipt);

        String encodedConstructor = FunctionEncoder.encodeConstructor(
                Arrays.<Type>asList(new Uint256(BigInteger.TEN)));

        try {
            TestContract.deployRemoteCall(
                    TestContract.class, aiManj, SampleKeys.CREDENTIALS,
                    ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
                    "0xcafed00d", encodedConstructor, BigInteger.ZERO).send();
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }

    @Test
    public void testCallSingleValue() throws Exception {
        // Example taken from FunctionReturnDecoderTest

        ManCall manCall = new ManCall();
        manCall.setResult("0x0000000000000000000000000000000000000000000000000000000000000020"
                + "0000000000000000000000000000000000000000000000000000000000000000");
        prepareCall(manCall);

        assertThat(contract.callSingleValue().send(), equalTo(new Utf8String("")));
    }

    @Test
    public void testCallSingleValueEmpty() throws Exception {
        // Example taken from FunctionReturnDecoderTest

        ManCall manCall = new ManCall();
        manCall.setResult("0x");
        prepareCall(manCall);

        assertNull(contract.callSingleValue().send());
    }

    @Test
    public void testCallMultipleValue() throws Exception {
        ManCall manCall = new ManCall();
        manCall.setResult("0x0000000000000000000000000000000000000000000000000000000000000037"
                + "0000000000000000000000000000000000000000000000000000000000000007");
        prepareCall(manCall);

        assertThat(contract.callMultipleValue().send(),
                equalTo(Arrays.asList(
                        new Uint256(BigInteger.valueOf(55)),
                        new Uint256(BigInteger.valueOf(7)))));
    }

    @Test
    public void testCallMultipleValueEmpty() throws Exception {
        ManCall manCall = new ManCall();
        manCall.setResult("0x");
        prepareCall(manCall);

        assertThat(contract.callMultipleValue().send(),
                equalTo(emptyList()));
    }

    @SuppressWarnings("unchecked")
    private void prepareCall(ManCall manCall) throws IOException {
        Request<?, ManCall> request = mock(Request.class);
        when(request.send()).thenReturn(manCall);

        when(aiManj.manCall(any(Transaction.class), eq(DefaultBlockParameterName.LATEST)))
                .thenReturn((Request) request);
    }

    @Test
    public void testTransaction() throws Exception {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash(TRANSACTION_HASH);
        transactionReceipt.setStatus("0x1");

        prepareTransaction(transactionReceipt);

        assertThat(contract.performTransaction(
                new Address(BigInteger.TEN), new Uint256(BigInteger.ONE)).send(),
                is(transactionReceipt));
    }

    @Test
    public void testTransactionFailed() throws Exception {
        thrown.expect(TransactionException.class);
        thrown.expectMessage(
                "Transaction has failed with status: 0x0. Gas used: 1. (not-enough gas?)");

        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash(TRANSACTION_HASH);
        transactionReceipt.setStatus("0x0");
        transactionReceipt.setGasUsed("0x1");

        prepareTransaction(transactionReceipt);
        contract.performTransaction(
                new Address(BigInteger.TEN), new Uint256(BigInteger.ONE)).send();
    }

    @Test
    public void testProcessEvent() {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        Log log = new Log();
        log.setTopics(Arrays.asList(
                // encoded function
                "0xfceb437c298f40d64702ac26411b2316e79f3c28ffa60edfc891ad4fc8ab82ca",
                // indexed value
                "0000000000000000000000003d6cb163f7c72d20b0fcd6baae5889329d138a4a"));
        // non-indexed value
        log.setData("0000000000000000000000000000000000000000000000000000000000000001");

        transactionReceipt.setLogs(Arrays.asList(log));

        EventValues eventValues = contract.processEvent(transactionReceipt).get(0);

        assertThat(eventValues.getIndexedValues(),
                equalTo(singletonList(
                        new Address("0x3d6cb163f7c72d20b0fcd6baae5889329d138a4a"))));
        assertThat(eventValues.getNonIndexedValues(),
                equalTo(singletonList(new Uint256(BigInteger.ONE))));
    }

    @Test
    public void testProcessEventForLogWithoutTopics() {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        final Log log = new Log();
        log.setTopics(Collections.emptyList());
        // non-indexed value
        log.setData("0000000000000000000000000000000000000000000000000000000000000001");
        transactionReceipt.setLogs(Arrays.asList(log));

        final List<EventValues> eventValues = contract.processEvent(transactionReceipt);
        assertTrue("No events expected", eventValues.isEmpty());
    }

    @Test(expected = TransactionException.class)
    public void testTimeout() throws Throwable {
        prepareTransaction(null);

        TransactionManager transactionManager =
                getVerifiedTransactionManager(SampleKeys.CREDENTIALS, 1, 1);

        contract = new TestContract(
                ADDRESS, aiManj, transactionManager,
                new DefaultGasProvider());

        testErrorScenario();
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testInvalidTransactionResponse() throws Throwable {
        prepareNonceRequest();

        ManSendTransaction manSendTransaction = new ManSendTransaction();
        manSendTransaction.setError(new Response.Error(1, "Invalid transaction"));

        Request<?, ManSendTransaction> rawTransactionRequest = mock(Request.class);
        when(rawTransactionRequest.sendAsync()).thenReturn(Async.run(() -> manSendTransaction));
        when(aiManj.manSendRawTransaction(any(String.class)))
                .thenReturn((Request) rawTransactionRequest);

        testErrorScenario();
    }

    @Test
    public void testSetGetAddresses() throws Exception {
        assertNull(contract.getDeployedAddress("1"));
        contract.setDeployedAddress("1", "0x000000000000add0e00000000000");
        assertNotNull(contract.getDeployedAddress("1"));
        contract.setDeployedAddress("2", "0x000000000000add0e00000000000");
        assertNotNull(contract.getDeployedAddress("2"));
    }

    @Test
    public void testSetGetGasPrice() {
        assertEquals(ManagedTransaction.GAS_PRICE, contract.getGasPrice());
        BigInteger newPrice = ManagedTransaction.GAS_PRICE.multiply(BigInteger.valueOf(2));
        contract.setGasPrice(newPrice);
        assertEquals(newPrice, contract.getGasPrice());
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testInvalidTransactionReceipt() throws Throwable {
        prepareNonceRequest();
        prepareTransactionRequest();

        ManGetTransactionReceipt manGetTransactionReceipt = new ManGetTransactionReceipt();
        manGetTransactionReceipt.setError(new Response.Error(1, "Invalid transaction receipt"));

        Request<?, ManGetTransactionReceipt> getTransactionReceiptRequest = mock(Request.class);
        when(getTransactionReceiptRequest.sendAsync())
                .thenReturn(Async.run(() -> manGetTransactionReceipt));
        when(aiManj.manGetTransactionReceipt(TRANSACTION_HASH))
                .thenReturn((Request) getTransactionReceiptRequest);

        testErrorScenario();
    }

    @Test
    public void testExtractEventParametersWithLogGivenATransactionReceipt() {

        final java.util.function.Function<String, Event> eventFactory = name ->
                new Event(name, emptyList());

        final BiFunction<Integer, Event, Log> logFactory = (logIndex, event) ->
                new Log(false, "" + logIndex, "0", "0x0", "0x0", "0", "0x" + logIndex, "", "",
                        singletonList(EventEncoder.encode(event)));

        final Event testEvent1 = eventFactory.apply("TestEvent1");
        final Event testEvent2 = eventFactory.apply("TestEvent2");

        final List<Log> logs = Arrays.asList(
                logFactory.apply(0, testEvent1),
                logFactory.apply(1, testEvent2)
        );

        final TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setLogs(logs);

        final List<Contract.EventValuesWithLog> eventValuesWithLogs1 =
                contract.extractEventParametersWithLog(testEvent1, transactionReceipt);

        assertEquals(eventValuesWithLogs1.size(), 1);
        assertEquals(eventValuesWithLogs1.get(0).getLog(), logs.get(0));

        final List<Contract.EventValuesWithLog> eventValuesWithLogs2 =
                contract.extractEventParametersWithLog(testEvent2, transactionReceipt);

        assertEquals(eventValuesWithLogs2.size(), 1);
        assertEquals(eventValuesWithLogs2.get(0).getLog(), logs.get(1));
    }

    void testErrorScenario() throws Throwable {
        try {
            contract.performTransaction(
                    new Address(BigInteger.TEN), new Uint256(BigInteger.ONE)).send();
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }

    private TransactionReceipt createTransactionReceipt() {
        return createTransactionReceiptWithStatus("0x1");
    }

    private TransactionReceipt createFailedTransactionReceipt() {
        return createTransactionReceiptWithStatus("0x0");
    }

    private TransactionReceipt createTransactionReceiptWithStatus(String status) {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash(TRANSACTION_HASH);
        transactionReceipt.setContractAddress(ADDRESS);
        transactionReceipt.setStatus(status);
        transactionReceipt.setGasUsed("0x1");
        return transactionReceipt;
    }

    private Contract deployContract(TransactionReceipt transactionReceipt)
            throws Exception {

        prepareTransaction(transactionReceipt);

        String encodedConstructor = FunctionEncoder.encodeConstructor(
                Arrays.<Type>asList(new Uint256(BigInteger.TEN)));

        return TestContract.deployRemoteCall(
                TestContract.class, aiManj, getVerifiedTransactionManager(SampleKeys.CREDENTIALS),
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
                "0xcafed00d", encodedConstructor, BigInteger.ZERO).send();
    }

    @SuppressWarnings("unchecked")
    private void prepareManGetCode(String binary) throws IOException {
        ManGetCode manGetCode = new ManGetCode();
        manGetCode.setResult(Numeric.prependHexPrefix(binary));

        Request<?, ManGetCode> manGetCodeRequest = mock(Request.class);
        when(manGetCodeRequest.send())
                .thenReturn(manGetCode);
        when(aiManj.manGetCode(ADDRESS, "MAN", DefaultBlockParameterName.LATEST))
                .thenReturn((Request) manGetCodeRequest);
    }

    private static class TestContract extends Contract {
        public TestContract(
                String contractAddress, AiManj aiManj, Credentials credentials,
                BigInteger gasPrice, BigInteger gasLimit) {
            super(TEST_CONTRACT_BINARY, contractAddress, aiManj, credentials, gasPrice, gasLimit);
        }

        public TestContract(
                String contractAddress,
                AiManj aiManj, TransactionManager transactionManager,
                ContractGasProvider gasProvider) {
            this(TEST_CONTRACT_BINARY, contractAddress, aiManj, transactionManager, gasProvider);
        }

        public TestContract(String binary, String contractAddress,
                            AiManj aiManj, TransactionManager transactionManager,
                            ContractGasProvider gasProvider) {
            super(binary, contractAddress, aiManj, transactionManager, gasProvider);
        }

        public RemoteCall<Utf8String> callSingleValue() {
            Function function = new Function("call",
                    Arrays.<Type>asList(),
                    Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                    }));
            return executeRemoteCallSingleValueReturn(function);
        }

        public RemoteCall<List<Type>> callMultipleValue()
                throws ExecutionException, InterruptedException {
            Function function = new Function("call",
                    Arrays.<Type>asList(),
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Uint256>() {
                            },
                            new TypeReference<Uint256>() {
                            }));
            return executeRemoteCallMultipleValueReturn(function);
        }

        public RemoteCall<TransactionReceipt> performTransaction(
                Address address, Uint256 amount) {
            Function function = new Function("approve",
                    Arrays.<Type>asList(address, amount),
                    Collections.<TypeReference<?>>emptyList());
            return executeRemoteCallTransaction(function);
        }

        public List<EventValues> processEvent(TransactionReceipt transactionReceipt) {
            Event event = new Event("Event",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>(true) {
                            },
                            new TypeReference<Uint256>() {
                            }));
            return extractEventParameters(event, transactionReceipt);
        }
    }
}
