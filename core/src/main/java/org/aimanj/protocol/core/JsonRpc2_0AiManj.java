package org.aimanj.protocol.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import io.reactivex.Flowable;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.core.methods.request.ShhFilter;
import org.aimanj.protocol.core.methods.request.ShhPost;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.core.methods.response.*;
import org.aimanj.protocol.rx.JsonRpc2_0Rx;
import org.aimanj.protocol.websocket.events.LogNotification;
import org.aimanj.protocol.websocket.events.NewHeadsNotification;
import org.aimanj.utils.Async;
import org.aimanj.utils.Numeric;

/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0AiManj implements AiManj {

    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;

    protected final AiManjService aiManjService;
    private final JsonRpc2_0Rx aiManjRx;
    private final long blockTime;
    private final ScheduledExecutorService scheduledExecutorService;

    public JsonRpc2_0AiManj(AiManjService aiManjService) {
        this(aiManjService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0AiManj(
            AiManjService aiManjService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.aiManjService = aiManjService;
        this.aiManjRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public Request<?, AiManjClientVersion> aiManClientVersion() {
        return new Request<>(
                "aiman_clientVersion",
                Collections.<String>emptyList(),
                aiManjService,
                AiManjClientVersion.class);
    }

    @Override
    public Request<?, AiManSha3> aiManSha3(String data) {
        return new Request<>(
                "aiman_sha3",
                Arrays.asList(data),
                aiManjService,
                AiManSha3.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<>(
                "net_version",
                Collections.<String>emptyList(),
                aiManjService,
                NetVersion.class);
    }

    @Override
    public Request<?, NetListening> netListening() {
        return new Request<>(
                "net_listening",
                Collections.<String>emptyList(),
                aiManjService,
                NetListening.class);
    }

    @Override
    public Request<?, NetPeerCount> netPeerCount() {
        return new Request<>(
                "net_peerCount",
                Collections.<String>emptyList(),
                aiManjService,
                NetPeerCount.class);
    }

    @Override
    public Request<?, ManProtocolVersion> manProtocolVersion() {
        return new Request<>(
                "man_protocolVersion",
                Collections.<String>emptyList(),
                aiManjService,
                ManProtocolVersion.class);
    }

    @Override
    public Request<?, ManCoinbase> manCoinbase() {
        return new Request<>(
                "man_coinbase",
                Collections.<String>emptyList(),
                aiManjService,
                ManCoinbase.class);
    }

    @Override
    public Request<?, ManSyncing> manSyncing() {
        return new Request<>(
                "man_syncing",
                Collections.<String>emptyList(),
                aiManjService,
                ManSyncing.class);
    }

    @Override
    public Request<?, ManMining> manMining() {
        return new Request<>(
                "man_mining",
                Collections.<String>emptyList(),
                aiManjService,
                ManMining.class);
    }

    @Override
    public Request<?, ManHashrate> manHashrate() {
        return new Request<>(
                "man_hashrate",
                Collections.<String>emptyList(),
                aiManjService,
                ManHashrate.class);
    }

    @Override
    public Request<?, ManGasPrice> manGasPrice() {
        return new Request<>(
                "man_gasPrice",
                Collections.<String>emptyList(),
                aiManjService,
                ManGasPrice.class);
    }

    @Override
    public Request<?, ManAccounts> manAccounts() {
        return new Request<>(
                "man_accounts",
                Collections.<String>emptyList(),
                aiManjService,
                ManAccounts.class);
    }

    @Override
    public Request<?, ManBlockNumber> manBlockNumber() {
        return new Request<>(
                "man_blockNumber",
                Collections.<String>emptyList(),
                aiManjService,
                ManBlockNumber.class);
    }

    @Override
    public Request<?, ManGetBalance> manGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getBalance",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                aiManjService,
                ManGetBalance.class);
    }

    @Override
    public Request<?, ManGetEntrustList> manGetEntrustList(
            String address) {
        return new Request<>(
                "man_getEntrustList",
                Arrays.asList(address),
                aiManjService,
                ManGetEntrustList.class);
    }

    @Override
    public Request<?, ManGetStorageAt> manGetStorageAt(
            String address, BigInteger position, String currency, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getStorageAt",
                Arrays.asList(
                        address,
                        Numeric.encodeQuantity(position),
                        currency,
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetStorageAt.class);
    }

    @Override
    public Request<?, ManGetTransactionCount> manGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                aiManjService,
                ManGetTransactionCount.class);
    }

    @Override
    public Request<?, ManGetBlockTransactionCountByHash> manGetBlockTransactionCountByHash(
            String blockHash) {
        return new Request<>(
                "man_getBlockTransactionCountByHash",
                Arrays.asList(blockHash),
                aiManjService,
                ManGetBlockTransactionCountByHash.class);
    }

    @Override
    public Request<?, ManGetBlockTransactionCountByNumber> manGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getBlockTransactionCountByNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                aiManjService,
                ManGetBlockTransactionCountByNumber.class);
    }

    @Override
    public Request<?, ManGetUncleCountByBlockHash> manGetUncleCountByBlockHash(String blockHash) {
        return new Request<>(
                "man_getUncleCountByBlockHash",
                Arrays.asList(blockHash),
                aiManjService,
                ManGetUncleCountByBlockHash.class);
    }

    @Override
    public Request<?, ManGetUncleCountByBlockNumber> manGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getUncleCountByBlockNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                aiManjService,
                ManGetUncleCountByBlockNumber.class);
    }

    @Override
    public Request<?, ManGetCode> manGetCode(
            String address, String currency, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getCode",
                Arrays.asList(address, currency, defaultBlockParameter.getValue()),
                aiManjService,
                ManGetCode.class);
    }

    @Override
    public Request<?, ManSign> manSign(String address, String sha3HashOfDataToSign) {
        return new Request<>(
                "man_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                aiManjService,
                ManSign.class);
    }

    @Override
    public Request<?, ManSendTransaction>
            manSendTransaction(
            Transaction transaction) {
        return new Request<>(
                "man_sendTransaction",
                Arrays.asList(transaction),
                aiManjService,
                ManSendTransaction.class);
    }

    @Override
    public Request<?, ManSendTransaction>
            manSendRawTransaction(
            String signedTransactionData) {
        return new Request<>(
                "man_sendRawTransaction",
                Arrays.asList(signedTransactionData),
                aiManjService,
                ManSendTransaction.class);
    }
    @Override
    public Request<?, ManSendTransaction>
    manSendRawTransaction(
            Map map) {
        return new Request<>(
                "man_sendRawTransaction",
                Arrays.asList(map),
                aiManjService,
                ManSendTransaction.class);
    }

    @Override
    public Request<?, ManCall> manCall(
            Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_call",
                Arrays.asList(transaction, defaultBlockParameter),
                aiManjService,
                ManCall.class);
    }

    @Override
    public Request<?, ManEstimateGas> manEstimateGas(Transaction transaction) {
        return new Request<>(
                "man_estimateGas",
                Arrays.asList(transaction),
                aiManjService,
                ManEstimateGas.class);
    }

    @Override
    public Request<?, ManBlock> manGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "man_getBlockByHash",
                Arrays.asList(
                        blockHash,
                        returnFullTransactionObjects),
                aiManjService,
                ManBlock.class);
    }

    @Override
    public Request<?, ManBlock> manGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) {
        return new Request<>(
                "man_getBlockByNumber",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        returnFullTransactionObjects),
                aiManjService,
                ManBlock.class);
    }

    @Override
    public Request<?, ManTransaction> manGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "man_getTransactionByHash",
                Arrays.asList(transactionHash),
                aiManjService,
                ManTransaction.class);
    }

    @Override
    public Request<?, ManTransaction> manGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex, String currency) {
        return new Request<>(
                "man_getTransactionByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex),
                        currency),
                aiManjService,
                ManTransaction.class);
    }

    @Override
    public Request<?, ManTransaction> manGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex, String currency) {
        return new Request<>(
                "man_getTransactionByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex),
                        currency),
                aiManjService,
                ManTransaction.class);
    }

    @Override
    public Request<?, ManGetTransactionReceipt> manGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "man_getTransactionReceipt",
                Arrays.asList(transactionHash),
                aiManjService,
                ManGetTransactionReceipt.class);
    }

    @Override
    public Request<?, ManBlock> manGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "man_getUncleByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                aiManjService,
                ManBlock.class);
    }

    @Override
    public Request<?, ManBlock> manGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger uncleIndex) {
        return new Request<>(
                "man_getUncleByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(uncleIndex)),
                aiManjService,
                ManBlock.class);
    }

    @Override
    public Request<?, ManGetCompilers> manGetCompilers() {
        return new Request<>(
                "man_getCompilers",
                Collections.<String>emptyList(),
                aiManjService,
                ManGetCompilers.class);
    }

    @Override
    public Request<?, ManCompileLLL> manCompileLLL(String sourceCode) {
        return new Request<>(
                "man_compileLLL",
                Arrays.asList(sourceCode),
                aiManjService,
                ManCompileLLL.class);
    }

    @Override
    public Request<?, ManCompileSolidity> manCompileSolidity(String sourceCode) {
        return new Request<>(
                "man_compileSolidity",
                Arrays.asList(sourceCode),
                aiManjService,
                ManCompileSolidity.class);
    }

    @Override
    public Request<?, ManCompileSerpent> manCompileSerpent(String sourceCode) {
        return new Request<>(
                "man_compileSerpent",
                Arrays.asList(sourceCode),
                aiManjService,
                ManCompileSerpent.class);
    }

    @Override
    public Request<?, ManFilter> manNewFilter(
            org.aimanj.protocol.core.methods.request.ManFilter manFilter) {
        return new Request<>(
                "man_newFilter",
                Arrays.asList(manFilter),
                aiManjService,
                ManFilter.class);
    }

    @Override
    public Request<?, ManFilter> manNewBlockFilter() {
        return new Request<>(
                "man_newBlockFilter",
                Collections.<String>emptyList(),
                aiManjService,
                ManFilter.class);
    }

    @Override
    public Request<?, ManFilter> manNewPendingTransactionFilter() {
        return new Request<>(
                "man_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                aiManjService,
                ManFilter.class);
    }

    @Override
    public Request<?, ManUninstallFilter> manUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "man_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                aiManjService,
                ManUninstallFilter.class);
    }

    @Override
    public Request<?, ManLog> manGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "man_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                aiManjService,
                ManLog.class);
    }

    @Override
    public Request<?, ManLog> manGetFilterLogs(BigInteger filterId) {
        return new Request<>(
                "man_getFilterLogs",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                aiManjService,
                ManLog.class);
    }

    @Override
    public Request<?, ManLog> manGetLogs(
            org.aimanj.protocol.core.methods.request.ManFilter manFilter) {
        return new Request<>(
                "man_getLogs",
                Arrays.asList(manFilter),
                aiManjService,
                ManLog.class);
    }

    @Override
    public Request<?, ManGetDepositAddr> manGetDepositByAddr(String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getDepositByAddr",
                Arrays.asList(
                        address,
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetDepositAddr.class);
    }
    @Override
    public Request<?, ManGetDeposit> manGetDeposit(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getDeposit",
                Arrays.asList(
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetDeposit.class);
    }
    @Override
    public Request<?, ManGetMatrixCoin> manGetMatrixCoin(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getMatrixCoin",
                Arrays.asList(
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetMatrixCoin.class);
    }

    @Override
    public Request<?, ManGetMatrixCoinConfig> manGetMatrixCoinConfig(String currency, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getMatrixCoinConfig",
                Arrays.asList(
                        currency,
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetMatrixCoinConfig.class);
    }
    @Override
    public Request<?, GetDestroyBalance> manGetDestroyBalance(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getDestroyBalance",
                Arrays.asList(
                        defaultBlockParameter.getValue()),
                aiManjService,
                GetDestroyBalance.class);
    }
    @Override
    public Request<?, ManGetSignAccountsByHash> manGetSignAccountsByHash(String hash) {
        return new Request<>(
                "man_getSignAccountsByHash",
                Arrays.asList(
                        hash),
                aiManjService,
                ManGetSignAccountsByHash.class);
    }
    @Override
    public Request<?, ManGetSignAccountsByHash> manGetSignAccountsByNumber(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getSignAccountsByNumber",
                Arrays.asList(
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetSignAccountsByHash.class);
    }
    @Override
    public Request<?, ManGetMap> manGetValidatorGroupInfo(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getValidatorGroupInfo",
                Arrays.asList(
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetMap.class);
    }
    @Override
    public Request<?, ManGetMap> manGetTopologyStatusByNumber(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getTopologyStatusByNumber",
                Arrays.asList(
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetMap.class);
    }
    @Override
    public Request<?, ManGetMap> manGetBlackList() {
        return new Request<>(
                "man_getBlackList",
                null,
                aiManjService,
                ManGetMap.class);
    }
    @Override
    public Request<?, ManGetString> manGetSelfLevel() {
        return new Request<>(
                "man_getSelfLevel",
                null,
                aiManjService,
                ManGetString.class);
    }
    @Override
    public Request<?, ManGetString> manGetUpTime(String manAddress, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "man_getUpTime",
                Arrays.asList(
                        manAddress,
                        defaultBlockParameter.getValue()),
                aiManjService,
                ManGetString.class);
    }
    @Override
    public Request<?, ManGetWork> manGetWork() {
        return new Request<>(
                "man_getWork",
                Collections.<String>emptyList(),
                aiManjService,
                ManGetWork.class);
    }

    @Override
    public Request<?, ManSubmitWork> manSubmitWork(
            String nonce, String headerPowHash, String mixDigest) {
        return new Request<>(
                "man_submitWork",
                Arrays.asList(nonce, headerPowHash, mixDigest),
                aiManjService,
                ManSubmitWork.class);
    }

    @Override
    public Request<?, ManSubmitHashrate> manSubmitHashrate(String hashrate, String clientId) {
        return new Request<>(
                "man_submitHashrate",
                Arrays.asList(hashrate, clientId),
                aiManjService,
                ManSubmitHashrate.class);
    }

    @Override
    public Request<?, DbPutString> dbPutString(
            String databaseName, String keyName, String stringToStore) {
        return new Request<>(
                "db_putString",
                Arrays.asList(databaseName, keyName, stringToStore),
                aiManjService,
                DbPutString.class);
    }

    @Override
    public Request<?, DbGetString> dbGetString(String databaseName, String keyName) {
        return new Request<>(
                "db_getString",
                Arrays.asList(databaseName, keyName),
                aiManjService,
                DbGetString.class);
    }

    @Override
    public Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore) {
        return new Request<>(
                "db_putHex",
                Arrays.asList(databaseName, keyName, dataToStore),
                aiManjService,
                DbPutHex.class);
    }

    @Override
    public Request<?, DbGetHex> dbGetHex(String databaseName, String keyName) {
        return new Request<>(
                "db_getHex",
                Arrays.asList(databaseName, keyName),
                aiManjService,
                DbGetHex.class);
    }

    @Override
    public Request<?, org.aimanj.protocol.core.methods.response.ShhPost> shhPost(ShhPost shhPost) {
        return new Request<>(
                "shh_post",
                Arrays.asList(shhPost),
                aiManjService,
                org.aimanj.protocol.core.methods.response.ShhPost.class);
    }

    @Override
    public Request<?, ShhVersion> shhVersion() {
        return new Request<>(
                "shh_version",
                Collections.<String>emptyList(),
                aiManjService,
                ShhVersion.class);
    }

    @Override
    public Request<?, ShhNewIdentity> shhNewIdentity() {
        return new Request<>(
                "shh_newIdentity",
                Collections.<String>emptyList(),
                aiManjService,
                ShhNewIdentity.class);
    }

    @Override
    public Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress) {
        return new Request<>(
                "shh_hasIdentity",
                Arrays.asList(identityAddress),
                aiManjService,
                ShhHasIdentity.class);
    }

    @Override
    public Request<?, ShhNewGroup> shhNewGroup() {
        return new Request<>(
                "shh_newGroup",
                Collections.<String>emptyList(),
                aiManjService,
                ShhNewGroup.class);
    }

    @Override
    public Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress) {
        return new Request<>(
                "shh_addToGroup",
                Arrays.asList(identityAddress),
                aiManjService,
                ShhAddToGroup.class);
    }

    @Override
    public Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter) {
        return new Request<>(
                "shh_newFilter",
                Arrays.asList(shhFilter),
                aiManjService,
                ShhNewFilter.class);
    }

    @Override
    public Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "shh_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                aiManjService,
                ShhUninstallFilter.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "shh_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                aiManjService,
                ShhMessages.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetMessages(BigInteger filterId) {
        return new Request<>(
                "shh_getMessages",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                aiManjService,
                ShhMessages.class);
    }

    @Override
    public Flowable<NewHeadsNotification> newHeadsNotifications() {
        return aiManjService.subscribe(
                new Request<>(
                        "man_subscribe",
                        Collections.singletonList("newHeads"),
                        aiManjService,
                        ManSubscribe.class),
                "man_unsubscribe",
                NewHeadsNotification.class
        );
    }

    @Override
    public Flowable<LogNotification> logsNotifications(
            List<String> addresses, List<String> topics) {

        Map<String, Object> params = createLogsParams(addresses, topics);

        return aiManjService.subscribe(
                new Request<>(
                        "man_subscribe",
                        Arrays.asList("logs", params),
                        aiManjService,
                        ManSubscribe.class),
                "man_unsubscribe",
                LogNotification.class
        );
    }

    private Map<String, Object> createLogsParams(List<String> addresses, List<String> topics) {
        Map<String, Object> params = new HashMap<>();
        if (!addresses.isEmpty()) {
            params.put("address", addresses);
        }
        if (!topics.isEmpty()) {
            params.put("topics", topics);
        }
        return params;
    }

    @Override
    public Flowable<String> manBlockHashFlowable() {
        return aiManjRx.manBlockHashFlowable(blockTime);
    }

    @Override
    public Flowable<String> manPendingTransactionHashFlowable() {
        return aiManjRx.manPendingTransactionHashFlowable(blockTime);
    }

    @Override
    public Flowable<Log> manLogFlowable(
            org.aimanj.protocol.core.methods.request.ManFilter manFilter) {
        return aiManjRx.manLogFlowable(manFilter, blockTime);
    }

    @Override
    public Flowable<org.aimanj.protocol.core.methods.response.Transaction>
            transactionFlowable() {
        return aiManjRx.transactionFlowable(blockTime);
    }

    @Override
    public Flowable<org.aimanj.protocol.core.methods.response.Transaction>
            pendingTransactionFlowable() {
        return aiManjRx.pendingTransactionFlowable(blockTime);
    }

    @Override
    public Flowable<ManBlock> blockFlowable(boolean fullTransactionObjects) {
        return aiManjRx.blockFlowable(fullTransactionObjects, blockTime);
    }

    @Override
    public Flowable<ManBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return aiManjRx.replayBlocksFlowable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Flowable<ManBlock> replayPastBlocksFlowable(DefaultBlockParameter startBlock,
                                                       DefaultBlockParameter endBlock,
                                                       boolean fullTransactionObjects,
                                                       boolean ascending) {
        return aiManjRx.replayBlocksFlowable(startBlock, endBlock,
                fullTransactionObjects, ascending);
    }

    @Override
    public Flowable<ManBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Flowable<ManBlock> onCompleteFlowable) {
        return aiManjRx.replayPastBlocksFlowable(
                startBlock, fullTransactionObjects, onCompleteFlowable);
    }

    @Override
    public Flowable<ManBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return aiManjRx.replayPastBlocksFlowable(startBlock, fullTransactionObjects);
    }

    @Override
    public Flowable<org.aimanj.protocol.core.methods.response.Transaction>
            replayPastTransactionsFlowable(DefaultBlockParameter startBlock,
                                          DefaultBlockParameter endBlock) {
        return aiManjRx.replayTransactionsFlowable(startBlock, endBlock);
    }

    @Override
    public Flowable<org.aimanj.protocol.core.methods.response.Transaction>
            replayPastTransactionsFlowable(DefaultBlockParameter startBlock) {
        return aiManjRx.replayPastTransactionsFlowable(startBlock);
    }

    @Override
    public Flowable<ManBlock> replayPastAndFutureBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return aiManjRx.replayPastAndFutureBlocksFlowable(
                startBlock, fullTransactionObjects, blockTime);
    }

    @Override
    public Flowable<org.aimanj.protocol.core.methods.response.Transaction>
            replayPastAndFutureTransactionsFlowable(DefaultBlockParameter startBlock) {
        return aiManjRx.replayPastAndFutureTransactionsFlowable(
                startBlock, blockTime);
    }

    @Override
    public void shutdown() {
        scheduledExecutorService.shutdown();
        try {
            aiManjService.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close aimanj service", e);
        }
    }
}
