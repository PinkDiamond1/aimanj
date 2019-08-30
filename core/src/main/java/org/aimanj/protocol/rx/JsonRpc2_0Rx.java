package org.aimanj.protocol.rx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameter;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.DefaultBlockParameterNumber;
import org.aimanj.protocol.core.filters.BlockFilter;
import org.aimanj.protocol.core.filters.LogFilter;
import org.aimanj.protocol.core.filters.PendingTransactionFilter;
import org.aimanj.protocol.core.methods.request.ManFilter;
import org.aimanj.protocol.core.methods.response.ManBlock;
import org.aimanj.protocol.core.methods.response.Log;
import org.aimanj.protocol.core.methods.response.Transaction;
import org.aimanj.utils.Flowables;

/**
 * aimanj reactive API implementation.
 */
public class JsonRpc2_0Rx {

    private final AiManj aimanj;
    private final ScheduledExecutorService scheduledExecutorService;
    private final Scheduler scheduler;

    public JsonRpc2_0Rx(AiManj aimanj, ScheduledExecutorService scheduledExecutorService) {
        this.aimanj = aimanj;
        this.scheduledExecutorService = scheduledExecutorService;
        this.scheduler = Schedulers.from(scheduledExecutorService);
    }

    public Flowable<String> manBlockHashFlowable(long pollingInterval) {
        return Flowable.create(subscriber -> {
            BlockFilter blockFilter = new BlockFilter(
                    aimanj, subscriber::onNext);
            run(blockFilter, subscriber, pollingInterval);
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<String> manPendingTransactionHashFlowable(long pollingInterval) {
        return Flowable.create(subscriber -> {
            PendingTransactionFilter pendingTransactionFilter = new PendingTransactionFilter(
                    aimanj, subscriber::onNext);

            run(pendingTransactionFilter, subscriber, pollingInterval);
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<Log> manLogFlowable(
            ManFilter manFilter, long pollingInterval) {
        return Flowable.create(subscriber -> {
            LogFilter logFilter = new LogFilter(
                    aimanj, subscriber::onNext, manFilter);

            run(logFilter, subscriber, pollingInterval);
        }, BackpressureStrategy.BUFFER);
    }

    private <T> void run(
            org.aimanj.protocol.core.filters.Filter<T> filter, FlowableEmitter<? super T> emitter,
            long pollingInterval) {

        filter.run(scheduledExecutorService, pollingInterval);
        emitter.setCancellable(filter::cancel);
    }

    public Flowable<Transaction> transactionFlowable(long pollingInterval) {
        return blockFlowable(true, pollingInterval)
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    public Flowable<Transaction> pendingTransactionFlowable(long pollingInterval) {
        return manPendingTransactionHashFlowable(pollingInterval)
                .flatMap(transactionHash ->
                        aimanj.manGetTransactionByHash(transactionHash).flowable())
                .filter(manTransaction -> manTransaction.getTransaction().isPresent())
                .map(manTransaction -> manTransaction.getTransaction().get());
    }

    public Flowable<ManBlock> blockFlowable(
            boolean fullTransactionObjects, long pollingInterval) {
        return manBlockHashFlowable(pollingInterval)
                .flatMap(blockHash ->
                        aimanj.manGetBlockByHash(blockHash, fullTransactionObjects).flowable());
    }

    public Flowable<ManBlock> replayBlocksFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return replayBlocksFlowable(startBlock, endBlock, fullTransactionObjects, true);
    }

    public Flowable<ManBlock> replayBlocksFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {
        // We use a scheduler to ensure this Flowable runs asynchronously for users to be
        // consistent with the other Flowables
        return replayBlocksFlowableSync(startBlock, endBlock, fullTransactionObjects, ascending)
                .subscribeOn(scheduler);
    }

    private Flowable<ManBlock> replayBlocksFlowableSync(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return replayBlocksFlowableSync(startBlock, endBlock, fullTransactionObjects, true);
    }

    private Flowable<ManBlock> replayBlocksFlowableSync(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {

        BigInteger startBlockNumber = null;
        BigInteger endBlockNumber = null;
        try {
            startBlockNumber = getBlockNumber(startBlock);
            endBlockNumber = getBlockNumber(endBlock);
        } catch (IOException e) {
            Flowable.error(e);
        }

        if (ascending) {
            return Flowables.range(startBlockNumber, endBlockNumber)
                    .flatMap(i -> aimanj.manGetBlockByNumber(
                            new DefaultBlockParameterNumber(i),
                            fullTransactionObjects).flowable());
        } else {
            return Flowables.range(startBlockNumber, endBlockNumber, false)
                    .flatMap(i -> aimanj.manGetBlockByNumber(
                            new DefaultBlockParameterNumber(i),
                            fullTransactionObjects).flowable());
        }
    }

    public Flowable<Transaction> replayTransactionsFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return replayBlocksFlowable(startBlock, endBlock, true)
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    public Flowable<ManBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Flowable<ManBlock> onCompleteFlowable) {
        // We use a scheduler to ensure this Flowable runs asynchronously for users to be
        // consistent with the other Flowables
        return replayPastBlocksFlowableSync(
                startBlock, fullTransactionObjects, onCompleteFlowable)
                .subscribeOn(scheduler);
    }

    public Flowable<ManBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return replayPastBlocksFlowable(
                startBlock, fullTransactionObjects, Flowable.empty());
    }

    private Flowable<ManBlock> replayPastBlocksFlowableSync(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Flowable<ManBlock> onCompleteFlowable) {

        BigInteger startBlockNumber;
        BigInteger latestBlockNumber;
        try {
            startBlockNumber = getBlockNumber(startBlock);
            latestBlockNumber = getLatestBlockNumber();
        } catch (IOException e) {
            return Flowable.error(e);
        }

        if (startBlockNumber.compareTo(latestBlockNumber) > -1) {
            return onCompleteFlowable;
        } else {
            return Flowable.concat(
                    replayBlocksFlowableSync(
                            new DefaultBlockParameterNumber(startBlockNumber),
                            new DefaultBlockParameterNumber(latestBlockNumber),
                            fullTransactionObjects),
                    Flowable.defer(() -> replayPastBlocksFlowableSync(
                            new DefaultBlockParameterNumber(latestBlockNumber.add(BigInteger.ONE)),
                            fullTransactionObjects,
                            onCompleteFlowable)));
        }
    }

    public Flowable<Transaction> replayPastTransactionsFlowable(
            DefaultBlockParameter startBlock) {
        return replayPastBlocksFlowable(
                startBlock, true, Flowable.empty())
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    public Flowable<ManBlock> replayPastAndFutureBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            long pollingInterval) {

        return replayPastBlocksFlowable(
                startBlock, fullTransactionObjects,
                blockFlowable(fullTransactionObjects, pollingInterval));
    }

    public Flowable<Transaction> replayPastAndFutureTransactionsFlowable(
            DefaultBlockParameter startBlock, long pollingInterval) {
        return replayPastAndFutureBlocksFlowable(
                startBlock, true, pollingInterval)
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    private BigInteger getLatestBlockNumber() throws IOException {
        return getBlockNumber(DefaultBlockParameterName.LATEST);
    }

    private BigInteger getBlockNumber(
            DefaultBlockParameter defaultBlockParameter) throws IOException {
        if (defaultBlockParameter instanceof DefaultBlockParameterNumber) {
            return ((DefaultBlockParameterNumber) defaultBlockParameter).getBlockNumber();
        } else {
            ManBlock latestManBlock = aimanj.manGetBlockByNumber(
                    defaultBlockParameter, false).send();
            return latestManBlock.getBlock().getNumber();
        }
    }

    private static List<Transaction> toTransactions(ManBlock manBlock) {
        // If you ever see an exception thrown here, it's probably due to an incomplete chain in
        // Gman/Parity. You should resync to solve.
//        return manBlock.getBlock().getTransactions().stream()
//                .map(transactionResult -> (Transaction) transactionResult.get())
//                .collect(Collectors.toList());
        return null;
    }
}
