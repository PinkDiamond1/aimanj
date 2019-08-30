package org.aimanj.protocol.rx;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.aimanj.protocol.core.methods.response.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import org.aimanj.protocol.ObjectMapperFactory;
import org.aimanj.protocol.aiManj;
import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.core.DefaultBlockParameterNumber;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManBlock;
import org.aimanj.utils.Numeric;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonRpc2_0RxTest {

    private final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

    private aiManj aiManj;

    private AiManjService aiManjService;

    @Before
    public void setUp() {
        aiManjService = mock(AiManjService.class);
        aiManj = aiManj.build(aiManjService, 1000, Executors.newSingleThreadScheduledExecutor());
    }

    @Test
    public void testReplayBlocksFlowable() throws Exception {

        List<ManBlock> manBlocks = Arrays.asList(createBlock(0), createBlock(1), createBlock(2));

        OngoingStubbing<ManBlock> stubbing =
                when(aiManjService.send(any(Request.class), eq(ManBlock.class)));
        for (ManBlock manBlock : manBlocks) {
            stubbing = stubbing.thenReturn(manBlock);
        }

        Flowable<ManBlock> flowable = aiManj.replayPastBlocksFlowable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                new DefaultBlockParameterNumber(BigInteger.valueOf(2)),
                false);

        CountDownLatch transactionLatch = new CountDownLatch(manBlocks.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<ManBlock> results = new ArrayList<>(manBlocks.size());
        Disposable subscription = flowable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(manBlocks));

        subscription.dispose();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isDisposed());
    }

    @Test
    public void testReplayBlocksDescendingFlowable() throws Exception {

        List<ManBlock> manBlocks = Arrays.asList(createBlock(2), createBlock(1), createBlock(0));

        OngoingStubbing<ManBlock> stubbing =
                when(aiManjService.send(any(Request.class), eq(ManBlock.class)));
        for (ManBlock manBlock : manBlocks) {
            stubbing = stubbing.thenReturn(manBlock);
        }

        Flowable<ManBlock> flowable = aiManj.replayPastBlocksFlowable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                new DefaultBlockParameterNumber(BigInteger.valueOf(2)),
                false, false);

        CountDownLatch transactionLatch = new CountDownLatch(manBlocks.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<ManBlock> results = new ArrayList<>(manBlocks.size());
        Disposable subscription = flowable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(manBlocks));

        subscription.dispose();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isDisposed());
    }

    @Test
    public void testReplayPastBlocksFlowable() throws Exception {
        List<ManBlock> expected = Arrays.asList(
                createBlock(0), createBlock(1), createBlock(2),
                createBlock(3), createBlock(4));

        List<ManBlock> manBlocks = Arrays.asList(
                expected.get(2),  // greatest block
                expected.get(0), expected.get(1), expected.get(2),
                expected.get(4), // greatest block
                expected.get(3), expected.get(4),
                expected.get(4));  // greatest block

        OngoingStubbing<ManBlock> stubbing =
                when(aiManjService.send(any(Request.class), eq(ManBlock.class)));
        for (ManBlock manBlock : manBlocks) {
            stubbing = stubbing.thenReturn(manBlock);
        }

        ManFilter manFilter = objectMapper.readValue(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}", ManFilter.class);
        ManLog manLog = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":["
                        + "\"0x31c2342b1e0b8ffda1507fbffddf213c4b3c1e819ff6a84b943faabb0ebf2403\""
                        + "]}",
                ManLog.class);
        ManUninstallFilter manUninstallFilter = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}", ManUninstallFilter.class);

        when(aiManjService.send(any(Request.class), eq(ManFilter.class)))
                .thenReturn(manFilter);
        when(aiManjService.send(any(Request.class), eq(ManLog.class)))
                .thenReturn(manLog);
        when(aiManjService.send(any(Request.class), eq(ManUninstallFilter.class)))
                .thenReturn(manUninstallFilter);

        Flowable<ManBlock> flowable = aiManj.replayPastBlocksFlowable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                false);

        CountDownLatch transactionLatch = new CountDownLatch(expected.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<ManBlock> results = new ArrayList<>(expected.size());
        Disposable subscription = flowable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1250, TimeUnit.MILLISECONDS);
        assertThat(results, equalTo(expected));

        subscription.dispose();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isDisposed());
    }

    public void testReplayPastAndFutureBlocksFlowable() throws Exception {
        List<ManBlock> expected = Arrays.asList(
                createBlock(0), createBlock(1), createBlock(2),
                createBlock(3), createBlock(4), createBlock(5),
                createBlock(6));

        List<ManBlock> manBlocks = Arrays.asList(
                expected.get(2),  // greatest block
                expected.get(0), expected.get(1), expected.get(2),
                expected.get(4), // greatest block
                expected.get(3), expected.get(4),
                expected.get(4),  // greatest block
                expected.get(5),  // initial response from manGetFilterLogs call
                expected.get(6)); // subsequent block from new block flowable

        OngoingStubbing<ManBlock> stubbing =
                when(aiManjService.send(any(Request.class), eq(ManBlock.class)));
        for (ManBlock manBlock : manBlocks) {
            stubbing = stubbing.thenReturn(manBlock);
        }

        ManFilter manFilter = objectMapper.readValue(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}", ManFilter.class);
        ManLog manLog = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":["
                        + "\"0x31c2342b1e0b8ffda1507fbffddf213c4b3c1e819ff6a84b943faabb0ebf2403\""
                        + "]}",
                ManLog.class);
        ManUninstallFilter manUninstallFilter = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}", ManUninstallFilter.class);

        when(aiManjService.send(any(Request.class), eq(ManFilter.class)))
                .thenReturn(manFilter);
        when(aiManjService.send(any(Request.class), eq(ManLog.class)))
                .thenReturn(manLog);
        when(aiManjService.send(any(Request.class), eq(ManUninstallFilter.class)))
                .thenReturn(manUninstallFilter);

        Flowable<ManBlock> flowable = aiManj.replayPastAndFutureBlocksFlowable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                false);

        CountDownLatch transactionLatch = new CountDownLatch(expected.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<ManBlock> results = new ArrayList<>(expected.size());
        Disposable subscription = flowable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1250, TimeUnit.MILLISECONDS);
        assertThat(results, equalTo(expected));

        subscription.dispose();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isDisposed());
    }

    @Test
    public void testReplayTransactionsFlowable() throws Exception {

        List<ManBlock> manBlocks = Arrays.asList(
                createBlockWithTransactions(0,
                        Arrays.asList(createTransaction("0x1234"),
                                createTransaction("0x1235"),
                                createTransaction("0x1236"))),
                createBlockWithTransactions(1,
                        Arrays.asList(createTransaction("0x2234"),
                                createTransaction("0x2235"),
                                createTransaction("0x2236"))),
                createBlockWithTransactions(2,
                        Arrays.asList(createTransaction("0x3234"),
                                createTransaction("0x3235"))));

        OngoingStubbing<ManBlock> stubbing =
                when(aiManjService.send(any(Request.class), eq(ManBlock.class)));
        for (ManBlock manBlock : manBlocks) {
            stubbing = stubbing.thenReturn(manBlock);
        }

        List<Transaction> expectedTransactions = manBlocks.stream()
                .flatMap(it -> it.getBlock().getTransactions().stream())
                .map(it -> (Transaction) it.get())
                .collect(Collectors.toList());

        Flowable<Transaction> flowable = aiManj.replayPastTransactionsFlowable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                new DefaultBlockParameterNumber(BigInteger.valueOf(2)));

        CountDownLatch transactionLatch = new CountDownLatch(expectedTransactions.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<Transaction> results = new ArrayList<>(expectedTransactions.size());
        Disposable subscription = flowable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(expectedTransactions));

        subscription.dispose();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isDisposed());
    }

    private ManBlock createBlock(int number) {
        ManBlock manBlock = new ManBlock();
        ManBlock.Block block = new ManBlock.Block();
        block.setNumber(Numeric.encodeQuantity(BigInteger.valueOf(number)));

        manBlock.setResult(block);
        return manBlock;
    }

    private ManBlock createBlockWithTransactions(int blockNumber, List<Transaction> transactions) {
        ManBlock manBlock = new ManBlock();
        ManBlock.Block block = new ManBlock.Block();
        block.setNumber(Numeric.encodeQuantity(BigInteger.valueOf(blockNumber)));

        List<ManBlock.TransactionResult> transactionResults =
                transactions.stream()
                        .map(it -> (ManBlock.TransactionResult<Transaction>) () -> it)
                        .collect(Collectors.toList());
        block.setTransactions(transactionResults);

        manBlock.setResult(block);
        return manBlock;
    }

    private Transaction createTransaction(String transactionHash) {
        Transaction transaction = new Transaction();
        transaction.setHash(transactionHash);
        return transaction;
    }
}
