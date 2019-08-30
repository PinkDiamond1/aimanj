package org.aimanj.protocol.core;

import java.math.BigInteger;
import java.util.List;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.aimanj.protocol.core.methods.response.ManBlock;
import org.aimanj.protocol.http.HttpService;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JSON-RPC 2.0 Integration Tests.
 */
public class CoreIT {

    private AiManj aiManj;

    private IntegrationTestConfig config = new TestnetConfig();

    public CoreIT() { }

    @Before
    public void setUp() {
        this.aiManj = AiManj.build(new HttpService());
    }

    @Test
    public void testAiManjClientVersion() throws Exception {
        AiManjClientVersion aiManClientVersion = aiManj.aiManClientVersion().send();
        String clientVersion = aiManClientVersion.getAiManjClientVersion();
        System.out.println("Matrix client version: " + clientVersion);
        assertFalse(clientVersion.isEmpty());
    }

    @Test
    public void testAiManjSha3() throws Exception {
        AiManSha3 aiManSha3 = aiManj.aiManSha3("0x68656c6c6f20776f726c64").send();
        assertThat(aiManSha3.getResult(),
                is("0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad"));
    }

    @Test
    public void testNetVersion() throws Exception {
        NetVersion netVersion = aiManj.netVersion().send();
        assertFalse(netVersion.getNetVersion().isEmpty());
    }

    @Test
    public void testNetListening() throws Exception {
        NetListening netListening = aiManj.netListening().send();
        assertTrue(netListening.isListening());
    }

    @Test
    public void testNetPeerCount() throws Exception {
        NetPeerCount netPeerCount = aiManj.netPeerCount().send();
        assertTrue(netPeerCount.getQuantity().signum() == 1);
    }

    @Test
    public void testManProtocolVersion() throws Exception {
        ManProtocolVersion manProtocolVersion = aiManj.manProtocolVersion().send();
        assertFalse(manProtocolVersion.getProtocolVersion().isEmpty());
    }

    @Test
    public void testManSyncing() throws Exception {
        ManSyncing manSyncing = aiManj.manSyncing().send();
        assertNotNull(manSyncing.getResult());
    }

    @Test
    public void testManCoinbase() throws Exception {
        ManCoinbase manCoinbase = aiManj.manCoinbase().send();
        assertNotNull(manCoinbase.getAddress());
    }

    @Test
    public void testManMining() throws Exception {
        ManMining manMining = aiManj.manMining().send();
        assertNotNull(manMining.getResult());
    }

    @Test
    public void testManHashrate() throws Exception {
        ManHashrate manHashrate = aiManj.manHashrate().send();
        assertThat(manHashrate.getHashrate(), is(BigInteger.ZERO));
    }

    @Test
    public void testManGasPrice() throws Exception {
        ManGasPrice manGasPrice = aiManj.manGasPrice().send();
        assertTrue(manGasPrice.getGasPrice().signum() == 1);
    }

    @Test
    public void testManAccounts() throws Exception {
        ManAccounts manAccounts = aiManj.manAccounts().send();
        assertNotNull(manAccounts.getAccounts());
    }

    @Test
    public void testManBlockNumber() throws Exception {
        ManBlockNumber manBlockNumber = aiManj.manBlockNumber().send();
        assertTrue(manBlockNumber.getBlockNumber().signum() == 1);
    }

    @Test
    public void testManGetBalance() throws Exception {
        ManGetBalance manGetBalance = aiManj.manGetBalance(
                config.validAccount(), DefaultBlockParameter.valueOf("latest")).send();
        assertTrue(manGetBalance.getBalance().size() > 0);
    }

    @Test
    public void testManGetStorageAt() throws Exception {
        ManGetStorageAt manGetStorageAt = aiManj.manGetStorageAt(
                config.validContractAddress(),
                BigInteger.valueOf(0),
                "MAN",
                DefaultBlockParameter.valueOf("latest")).send();
        assertThat(manGetStorageAt.getData(), is(config.validContractAddressPositionZero()));
    }

    @Test
    public void testManGetTransactionCount() throws Exception {
        ManGetTransactionCount manGetTransactionCount = aiManj.manGetTransactionCount(
                config.validAccount(),
                DefaultBlockParameter.valueOf("latest")).send();
        assertTrue(manGetTransactionCount.getTransactionCount().signum() == 1);
    }

    @Test
    public void testManGetBlockTransactionCountByHash() throws Exception {
        ManGetBlockTransactionCountByHash manGetBlockTransactionCountByHash =
                aiManj.manGetBlockTransactionCountByHash(
                        config.validBlockHash()).send();
        assertThat(manGetBlockTransactionCountByHash.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testManGetBlockTransactionCountByNumber() throws Exception {
        ManGetBlockTransactionCountByNumber manGetBlockTransactionCountByNumber =
                aiManj.manGetBlockTransactionCountByNumber(
                        DefaultBlockParameter.valueOf(config.validBlock())).send();
        assertThat(manGetBlockTransactionCountByNumber.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testManGetUncleCountByBlockHash() throws Exception {
        ManGetUncleCountByBlockHash manGetUncleCountByBlockHash =
                aiManj.manGetUncleCountByBlockHash(config.validBlockHash()).send();
        assertThat(manGetUncleCountByBlockHash.getUncleCount(),
                equalTo(config.validBlockUncleCount()));
    }

    @Test
    public void testManGetUncleCountByBlockNumber() throws Exception {
        ManGetUncleCountByBlockNumber manGetUncleCountByBlockNumber =
                aiManj.manGetUncleCountByBlockNumber(
                        DefaultBlockParameter.valueOf("latest")).send();
        assertThat(manGetUncleCountByBlockNumber.getUncleCount(),
                equalTo(config.validBlockUncleCount()));
    }

    @Test
    public void testManGetCode() throws Exception {
        ManGetCode manGetCode = aiManj.manGetCode(config.validContractAddress(), "MAN",
                DefaultBlockParameter.valueOf(config.validBlock())).send();
        assertThat(manGetCode.getCode(), is(config.validContractCode()));
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testManSign() throws Exception {
        // ManSign manSign = aimanj.manSign();
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testManSendTransaction() throws Exception {
        ManSendTransaction manSendTransaction = aiManj.manSendTransaction(
                config.buildTransaction()).send();
        assertFalse(manSendTransaction.getTransactionHash().isEmpty());
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testManSendRawTransaction() throws Exception {

    }

    @Test
    public void testManCall() throws Exception {
        ManCall manCall = aiManj.manCall(config.buildTransaction(),
                DefaultBlockParameter.valueOf("latest")).send();

        assertThat(DefaultBlockParameterName.LATEST.getValue(), is("latest"));
        assertThat(manCall.getValue(), is("0x"));
    }

    @Test
    public void testManEstimateGas() throws Exception {
        ManEstimateGas manEstimateGas = aiManj.manEstimateGas(config.buildTransaction())
                .send();
        assertTrue(manEstimateGas.getAmountUsed().signum() == 1);
    }

    @Test
    public void testManGetBlockByHashReturnHashObjects() throws Exception {
        ManBlock manBlock = aiManj.manGetBlockByHash(config.validBlockHash(), false)
                .send();

        ManBlock.Block block = manBlock.getBlock();
        assertNotNull(manBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                is(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testManGetBlockByHashReturnFullTransactionObjects() throws Exception {
        ManBlock manBlock = aiManj.manGetBlockByHash(config.validBlockHash(), true)
                .send();

        ManBlock.Block block = manBlock.getBlock();
        assertNotNull(manBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testManGetBlockByNumberReturnHashObjects() throws Exception {
        ManBlock manBlock = aiManj.manGetBlockByNumber(
                DefaultBlockParameter.valueOf(config.validBlock()), false).send();

        ManBlock.Block block = manBlock.getBlock();
        assertNotNull(manBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testManGetBlockByNumberReturnTransactionObjects() throws Exception {
        ManBlock manBlock = aiManj.manGetBlockByNumber(
                DefaultBlockParameter.valueOf(config.validBlock()), true).send();

        ManBlock.Block block = manBlock.getBlock();
        assertNotNull(manBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testManGetTransactionByHash() throws Exception {
        ManTransaction manTransaction = aiManj.manGetTransactionByHash(
                config.validTransactionHash()).send();
        assertTrue(manTransaction.getTransaction().isPresent());
        Transaction transaction = manTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
    }

    @Test
    public void testManGetTransactionByBlockHashAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        ManTransaction manTransaction = aiManj.manGetTransactionByBlockHashAndIndex(
                config.validBlockHash(), index, "MAN").send();
        assertTrue(manTransaction.getTransaction().isPresent());
        Transaction transaction = manTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testManGetTransactionByBlockNumberAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        ManTransaction manTransaction = aiManj.manGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(config.validBlock()), index, "MAN").send();
        assertTrue(manTransaction.getTransaction().isPresent());
        Transaction transaction = manTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testManGetTransactionReceipt() throws Exception {
        ManGetTransactionReceipt manGetTransactionReceipt = aiManj.manGetTransactionReceipt(
                config.validTransactionHash()).send();
        assertTrue(manGetTransactionReceipt.getTransactionReceipt().isPresent());
        TransactionReceipt transactionReceipt =
                manGetTransactionReceipt.getTransactionReceipt().get();
        assertThat(transactionReceipt.getTransactionHash(), is(config.validTransactionHash()));
    }

    @Test
    public void testManGetUncleByBlockHashAndIndex() throws Exception {
        ManBlock manBlock = aiManj.manGetUncleByBlockHashAndIndex(
                config.validUncleBlockHash(), BigInteger.ZERO).send();
        assertNotNull(manBlock.getBlock());
    }

    @Test
    public void testManGetUncleByBlockNumberAndIndex() throws Exception {
        ManBlock manBlock = aiManj.manGetUncleByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(config.validUncleBlock()), BigInteger.ZERO)
                .send();
        assertNotNull(manBlock.getBlock());
    }

    @Test
    public void testManGetCompilers() throws Exception {
        ManGetCompilers manGetCompilers = aiManj.manGetCompilers().send();
        assertNotNull(manGetCompilers.getCompilers());
    }

    @Ignore  // The method man_compileLLL does not exist/is not available
    @Test
    public void testManCompileLLL() throws Exception {
        ManCompileLLL manCompileLLL = aiManj.manCompileLLL(
                "(returnlll (suicide (caller)))").send();
        assertFalse(manCompileLLL.getCompiledSourceCode().isEmpty());
    }

    @Test
    public void testManCompileSolidity() throws Exception {
        String sourceCode = "pragma solidity ^0.4.0;"
                + "\ncontract test { function multiply(uint a) returns(uint d) {"
                + "   return a * 7;   } }"
                + "\ncontract test2 { function multiply2(uint a) returns(uint d) {"
                + "   return a * 7;   } }";
        ManCompileSolidity manCompileSolidity = aiManj.manCompileSolidity(sourceCode)
                .send();
        assertNotNull(manCompileSolidity.getCompiledSolidity());
        assertThat(
                manCompileSolidity.getCompiledSolidity().get("test2").getInfo().getSource(),
                is(sourceCode));
    }

    @Ignore  // The method man_compileSerpent does not exist/is not available
    @Test
    public void testManCompileSerpent() throws Exception {
        ManCompileSerpent manCompileSerpent = aiManj.manCompileSerpent(
                "/* some serpent */").send();
        assertFalse(manCompileSerpent.getCompiledSourceCode().isEmpty());
    }

    @Test
    public void testFiltersByFilterId() throws Exception {
        org.aimanj.protocol.core.methods.request.ManFilter manFilter =
                new org.aimanj.protocol.core.methods.request.ManFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress());

        String eventSignature = config.encodedEvent();
        manFilter.addSingleTopic(eventSignature);

        // man_newFilter
        ManFilter manNewFilter = aiManj.manNewFilter(manFilter).send();
        BigInteger filterId = manNewFilter.getFilterId();

        // man_getFilterLogs
        ManLog manFilterLogs = aiManj.manGetFilterLogs(filterId).send();
        List<ManLog.LogResult> filterLogs = manFilterLogs.getLogs();
        assertFalse(filterLogs.isEmpty());

        // man_getFilterChanges - nothing will have changed in this interval
        ManLog manLog = aiManj.manGetFilterChanges(filterId).send();
        assertTrue(manLog.getLogs().isEmpty());

        // man_uninstallFilter
        ManUninstallFilter manUninstallFilter = aiManj.manUninstallFilter(filterId).send();
        assertTrue(manUninstallFilter.isUninstalled());
    }

    @Test
    public void testManNewBlockFilter() throws Exception {
        ManFilter manNewBlockFilter = aiManj.manNewBlockFilter().send();
        assertNotNull(manNewBlockFilter.getFilterId());
    }

    @Test
    public void testManNewPendingTransactionFilter() throws Exception {
        ManFilter manNewPendingTransactionFilter =
                aiManj.manNewPendingTransactionFilter().send();
        assertNotNull(manNewPendingTransactionFilter.getFilterId());
    }

    @Test
    public void testManGetLogs() throws Exception {
        org.aimanj.protocol.core.methods.request.ManFilter manFilter =
                new org.aimanj.protocol.core.methods.request.ManFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress()
        );

        manFilter.addSingleTopic(config.encodedEvent());

        ManLog manLog = aiManj.manGetLogs(manFilter).send();
        List<ManLog.LogResult> logs = manLog.getLogs();
        assertFalse(logs.isEmpty());
    }

    // @Test
    // public void testManGetWork() throws Exception {
    //     ManGetWork manGetWork = requestFactory.manGetWork();
    //     assertNotNull(manGetWork.getResult());
    // }

    @Test
    public void testManSubmitWork() throws Exception {

    }

    @Test
    public void testManSubmitHashrate() throws Exception {
    
    }

    @Test
    public void testDbPutString() throws Exception {
    
    }

    @Test
    public void testDbGetString() throws Exception {
    
    }

    @Test
    public void testDbPutHex() throws Exception {
    
    }

    @Test
    public void testDbGetHex() throws Exception {
    
    }

    @Test
    public void testShhPost() throws Exception {
    
    }

    @Ignore // The method shh_version does not exist/is not available
    @Test
    public void testShhVersion() throws Exception {
        ShhVersion shhVersion = aiManj.shhVersion().send();
        assertNotNull(shhVersion.getVersion());
    }

    @Ignore  // The method shh_newIdentity does not exist/is not available
    @Test
    public void testShhNewIdentity() throws Exception {
        ShhNewIdentity shhNewIdentity = aiManj.shhNewIdentity().send();
        assertNotNull(shhNewIdentity.getAddress());
    }

    @Test
    public void testShhHasIdentity() throws Exception {
    
    }

    @Ignore  // The method shh_newIdentity does not exist/is not available
    @Test
    public void testShhNewGroup() throws Exception {
        ShhNewGroup shhNewGroup = aiManj.shhNewGroup().send();
        assertNotNull(shhNewGroup.getAddress());
    }

    @Ignore  // The method shh_addToGroup does not exist/is not available
    @Test
    public void testShhAddToGroup() throws Exception {

    }

    @Test
    public void testShhNewFilter() throws Exception {
    
    }

    @Test
    public void testShhUninstallFilter() throws Exception {
    
    }

    @Test
    public void testShhGetFilterChanges() throws Exception {
    
    }

    @Test
    public void testShhGetMessages() throws Exception {
    
    }
}
