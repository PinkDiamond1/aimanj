package org.aimanj.protocol.core;

import java.math.BigInteger;
import java.util.Map;

import org.aimanj.protocol.core.methods.request.ShhFilter;
import org.aimanj.protocol.core.methods.response.*;

/**
 * Core Manereum JSON-RPC API.
 */
public interface Matrix {
    Request<?, AiManjClientVersion> aiManClientVersion();

    Request<?, AiManSha3> aiManSha3(String data);

    Request<?, NetVersion> netVersion();

    Request<?, NetListening> netListening();

    Request<?, NetPeerCount> netPeerCount();

    Request<?, ManProtocolVersion> manProtocolVersion();

    Request<?, ManCoinbase> manCoinbase();

    Request<?, ManSyncing> manSyncing();

    Request<?, ManMining> manMining();

    Request<?, ManHashrate> manHashrate();

    Request<?, ManGasPrice> manGasPrice();

    Request<?, ManAccounts> manAccounts();

    Request<?, ManBlockNumber> manBlockNumber();

    Request<?, ManGetBalance> manGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetEntrustList> manGetEntrustList(String address);

    Request<?, ManGetStorageAt> manGetStorageAt(
            String address, BigInteger position, String currency,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetTransactionCount> manGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetBlockTransactionCountByHash> manGetBlockTransactionCountByHash(
            String blockHash);

    Request<?, ManGetBlockTransactionCountByNumber> manGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetUncleCountByBlockHash> manGetUncleCountByBlockHash(String blockHash);

    Request<?, ManGetUncleCountByBlockNumber> manGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetCode> manGetCode(String address, String currency, DefaultBlockParameter defaultBlockParameter);

    Request<?, ManSign> manSign(String address, String sha3HashOfDataToSign);

    Request<?, org.aimanj.protocol.core.methods.response.ManSendTransaction> manSendTransaction(
            org.aimanj.protocol.core.methods.request.Transaction transaction);

    Request<?, org.aimanj.protocol.core.methods.response.ManSendTransaction> manSendRawTransaction(
            String signedTransactionData);

    Request<?, org.aimanj.protocol.core.methods.response.ManSendTransaction> manSendRawTransaction(
            Map map);

    Request<?, org.aimanj.protocol.core.methods.response.ManCall> manCall(
            org.aimanj.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, ManEstimateGas> manEstimateGas(
            org.aimanj.protocol.core.methods.request.Transaction transaction);

    Request<?, ManBlock> manGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, ManBlock> manGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects);

    Request<?, ManTransaction> manGetTransactionByHash(String transactionHash);

    Request<?, ManTransaction> manGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex, String currency);

    Request<?, ManTransaction> manGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex, String currency);

    Request<?, ManGetTransactionReceipt> manGetTransactionReceipt(String transactionHash);

    Request<?, ManBlock> manGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, ManBlock> manGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, ManGetCompilers> manGetCompilers();

    Request<?, ManCompileLLL> manCompileLLL(String sourceCode);

    Request<?, ManCompileSolidity> manCompileSolidity(String sourceCode);

    Request<?, ManCompileSerpent> manCompileSerpent(String sourceCode);

    Request<?, ManFilter> manNewFilter(org.aimanj.protocol.core.methods.request.ManFilter manFilter);

    Request<?, ManFilter> manNewBlockFilter();

    Request<?, ManFilter> manNewPendingTransactionFilter();

    Request<?, ManUninstallFilter> manUninstallFilter(BigInteger filterId);

    Request<?, ManLog> manGetFilterChanges(BigInteger filterId);

    Request<?, ManLog> manGetFilterLogs(BigInteger filterId);

    Request<?, ManLog> manGetLogs(org.aimanj.protocol.core.methods.request.ManFilter manFilter);

    Request<?, ManGetDepositAddr> manGetDepositByAddr(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetDeposit> manGetDeposit(DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetMatrixCoin> manGetMatrixCoin(DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetMatrixCoinConfig> manGetMatrixCoinConfig(String currency, DefaultBlockParameter defaultBlockParameter);

    Request<?, GetDestroyBalance> manGetDestroyBalance(DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetSignAccountsByHash> manGetSignAccountsByHash(String hash);

    Request<?, ManGetSignAccountsByHash> manGetSignAccountsByNumber(DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetMap> manGetValidatorGroupInfo(DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetMap> manGetTopologyStatusByNumber(DefaultBlockParameter defaultBlockParameter);

    Request<?, ManGetMap> manGetBlackList();

    Request<?, ManGetString> manGetSelfLevel();

    Request<?, ManGetString> manGetUpTime(String manAddress, DefaultBlockParameter defaultBlockParameter);

//    Request<?, ManGetString> manNewBlockFilter();

    Request<?, ManGetWork> manGetWork();

    Request<?, ManSubmitWork> manSubmitWork(String nonce, String headerPowHash, String mixDigest);

    Request<?, ManSubmitHashrate> manSubmitHashrate(String hashrate, String clientId);

    Request<?, DbPutString> dbPutString(String databaseName, String keyName, String stringToStore);

    Request<?, DbGetString> dbGetString(String databaseName, String keyName);

    Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore);

    Request<?, DbGetHex> dbGetHex(String databaseName, String keyName);

    Request<?, org.aimanj.protocol.core.methods.response.ShhPost> shhPost(
            org.aimanj.protocol.core.methods.request.ShhPost shhPost);

    Request<?, ShhVersion> shhVersion();

    Request<?, ShhNewIdentity> shhNewIdentity();

    Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress);

    Request<?, ShhNewGroup> shhNewGroup();

    Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress);

    Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter);

    Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId);

    Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId);

    Request<?, ShhMessages> shhGetMessages(BigInteger filterId);

}
