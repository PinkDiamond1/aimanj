package org.aimanj.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;

import org.aimanj.protocol.AiManj;
import org.junit.Test;

import org.aimanj.protocol.RequestTester;
import org.aimanj.protocol.core.methods.request.ManFilter;
import org.aimanj.protocol.core.methods.request.ShhFilter;
import org.aimanj.protocol.core.methods.request.ShhPost;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.http.HttpService;
import org.aimanj.utils.Numeric;

public class RequestTest extends RequestTester {

    private AiManj aiManj;

    @Override
    protected void initAiManjClient(HttpService httpService) {
        aiManj = AiManj.build(httpService);
    }

    @Test
    public void testAiManjClientVersion() throws Exception {
        aiManj.aiManClientVersion().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"aiManj_clientVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testAiManjSha3() throws Exception {
        aiManj.aiManSha3("0x68656c6c6f20776f726c64").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"aiManj_sha3\","
                        + "\"params\":[\"0x68656c6c6f20776f726c64\"],\"id\":1}");
    }

    @Test
    public void testNetVersion() throws Exception {
        aiManj.netVersion().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_version\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetListening() throws Exception {
        aiManj.netListening().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_listening\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetPeerCount() throws Exception {
        aiManj.netPeerCount().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_peerCount\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManProtocolVersion() throws Exception {
        aiManj.manProtocolVersion().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_protocolVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManSyncing() throws Exception {
        aiManj.manSyncing().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_syncing\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManCoinbase() throws Exception {
        aiManj.manCoinbase().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_coinbase\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManMining() throws Exception {
        aiManj.manMining().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_mining\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManHashrate() throws Exception {
        aiManj.manHashrate().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_hashrate\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManGasPrice() throws Exception {
        aiManj.manGasPrice().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_gasPrice\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManAccounts() throws Exception {
        aiManj.manAccounts().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_accounts\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManBlockNumber() throws Exception {
        aiManj.manBlockNumber().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_blockNumber\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManGetBalance() throws Exception {
        aiManj.manGetBalance("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                DefaultBlockParameterName.LATEST).send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"man_getBalance\","
                        + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
                        + "\"id\":1}");
    }

    @Test
    public void testManGetStorageAt() throws Exception {
        aiManj.manGetStorageAt("0x295a70b2de5e3953354a6a8344e616ed314d7251", BigInteger.ZERO,
                "MAN", DefaultBlockParameterName.LATEST).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getStorageAt\","
                + "\"params\":[\"0x295a70b2de5e3953354a6a8344e616ed314d7251\",\"0x0\",\"latest\"],"
                + "\"id\":1}");
    }

    @Test
    public void testManGetTransactionCount() throws Exception {
        aiManj.manGetTransactionCount("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                DefaultBlockParameterName.LATEST).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getTransactionCount\","
                + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
                + "\"id\":1}");
    }

    @Test
    public void testManGetBlockTransactionCountByHash() throws Exception {
        aiManj.manGetBlockTransactionCountByHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getBlockTransactionCountByHash\",\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testManGetBlockTransactionCountByNumber() throws Exception {
        aiManj.manGetBlockTransactionCountByNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getBlockTransactionCountByNumber\","
                + "\"params\":[\"0xe8\"],\"id\":1}");
    }

    @Test
    public void testManGetUncleCountByBlockHash() throws Exception {
        aiManj.manGetUncleCountByBlockHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getUncleCountByBlockHash\",\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testManGetUncleCountByBlockNumber() throws Exception {
        aiManj.manGetUncleCountByBlockNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getUncleCountByBlockNumber\","
                + "\"params\":[\"0xe8\"],\"id\":1}");
    }

    @Test
    public void testManGetCode() throws Exception {
        aiManj.manGetCode("0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b", "MAN",
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x2"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getCode\","
                + "\"params\":[\"0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"0x2\"],\"id\":1}");
    }

    @Test
    public void testManSign() throws Exception {
        aiManj.manSign("0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab",
                "0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_sign\","
                + "\"params\":[\"0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab\","
                + "\"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470\"],"
                + "\"id\":1}");
    }

    @Test
    public void testManSendTransaction() throws Exception {
        aiManj.manSendTransaction(new Transaction(
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                BigInteger.ONE,
                Numeric.toBigInt("0x9184e72a000"),
                Numeric.toBigInt("0x76c0"),
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                Numeric.toBigInt("0x9184e72a"),
                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb"
                        + "970870f072445675058bb8eb970870f072445675",
                "MAN",
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                null)).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_sendTransaction\",\"params\":[{\"from\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"gas\":\"0x76c0\",\"gasPrice\":\"0x9184e72a000\",\"value\":\"0x9184e72a\",\"data\":\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\",\"nonce\":\"0x1\"}],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testManSendRawTransaction() throws Exception {
        aiManj.manSendRawTransaction(
                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f"
                        + "072445675058bb8eb970870f072445675").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_sendRawTransaction\",\"params\":[\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\"],\"id\":1}");
        //CHECKSTYLE:ON
    }


    @Test
    public void testManCall() throws Exception {
        aiManj.manCall(Transaction.createManCallTransaction(
                "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                        "0x0", "MAN"),
                DefaultBlockParameter.valueOf("latest")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_call\","
                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
                + "\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"data\":\"0x0\"},"
                + "\"latest\"],\"id\":1}");
    }

    @Test
    public void testManEstimateGas() throws Exception {
        aiManj.manEstimateGas(
                Transaction.createManCallTransaction(
                        "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", "0x0", "MAN")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_estimateGas\","
                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
                + "\"to\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\",\"data\":\"0x0\"}],"
                + "\"id\":1}");
    }

    @Test
    public void testManEstimateGasContractCreation() throws Exception {
        aiManj.manEstimateGas(
                Transaction.createContractTransaction(
                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", BigInteger.ONE,
                        BigInteger.TEN, "", "MAN", BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, null)).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_estimateGas\","
                + "\"params\":[{\"from\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\","
                + "\"gasPrice\":\"0xa\",\"data\":\"0x\",\"nonce\":\"0x1\"}],\"id\":1}");
    }

    @Test
    public void testManGetBlockByHash() throws Exception {
        aiManj.manGetBlockByHash(
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331", true).send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"man_getBlockByHash\",\"params\":["
                        + "\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\""
                        + ",true],\"id\":1}");
    }

    @Test
    public void testManGetBlockByNumber() throws Exception {
        aiManj.manGetBlockByNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x1b4")), true).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getBlockByNumber\","
                + "\"params\":[\"0x1b4\",true],\"id\":1}");
    }

    @Test
    public void testManGetTransactionByHash() throws Exception {
        aiManj.manGetTransactionByHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getTransactionByHash\",\"params\":["
                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
                + "\"id\":1}");
    }

    @Test
    public void testManGetTransactionByBlockHashAndIndex() throws Exception {
        aiManj.manGetTransactionByBlockHashAndIndex(
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                BigInteger.ZERO, "MAN").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getTransactionByBlockHashAndIndex\",\"params\":[\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\"0x0\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testManGetTransactionByBlockNumberAndIndex() throws Exception {
        aiManj.manGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x29c")), BigInteger.ZERO, "MAN").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getTransactionByBlockNumberAndIndex\","
                + "\"params\":[\"0x29c\",\"0x0\"],\"id\":1}");
    }

    @Test
    public void testManGetTransactionReceipt() throws Exception {
        aiManj.manGetTransactionReceipt(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getTransactionReceipt\",\"params\":["
                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
                + "\"id\":1}");
    }

    @Test
    public void testManGetUncleByBlockHashAndIndex() throws Exception {
        aiManj.manGetUncleByBlockHashAndIndex(
                "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
                BigInteger.ZERO).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getUncleByBlockHashAndIndex\","
                + "\"params\":["
                + "\"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\"0x0\"],"
                + "\"id\":1}");
    }

    @Test
    public void testManGetUncleByBlockNumberAndIndex() throws Exception {
        aiManj.manGetUncleByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x29c")), BigInteger.ZERO).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getUncleByBlockNumberAndIndex\","
                + "\"params\":[\"0x29c\",\"0x0\"],\"id\":1}");
    }

    @Test
    public void testManGetCompilers() throws Exception {
        aiManj.manGetCompilers().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getCompilers\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testManCompileSolidity() throws Exception {
        aiManj.manCompileSolidity(
                "contract test { function multiply(uint a) returns(uint d) {   return a * 7;   } }")
                .send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_compileSolidity\","
                + "\"params\":[\"contract test { function multiply(uint a) returns(uint d) {"
                + "   return a * 7;   } }\"],\"id\":1}");
    }

    @Test
    public void testManCompileLLL() throws Exception {
        aiManj.manCompileLLL("(returnlll (suicide (caller)))").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_compileLLL\","
                + "\"params\":[\"(returnlll (suicide (caller)))\"],\"id\":1}");
    }

    @Test
    public void testManCompileSerpent() throws Exception {
        aiManj.manCompileSerpent("/* some serpent */").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_compileSerpent\","
                + "\"params\":[\"/* some serpent */\"],\"id\":1}");
    }

    @Test
    public void testManNewFilter() throws Exception {
        ManFilter manFilter = new ManFilter()
                .addSingleTopic("0x12341234");

        aiManj.manNewFilter(manFilter).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_newFilter\","
                + "\"params\":[{\"topics\":[\"0x12341234\"]}],\"id\":1}");
    }

    @Test
    public void testManNewBlockFilter() throws Exception {
        aiManj.manNewBlockFilter().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_newBlockFilter\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testManNewPendingTransactionFilter() throws Exception {
        aiManj.manNewPendingTransactionFilter().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_newPendingTransactionFilter\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testManUninstallFilter() throws Exception {
        aiManj.manUninstallFilter(Numeric.toBigInt("0xb")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_uninstallFilter\","
                + "\"params\":[\"0x0b\"],\"id\":1}");
    }

    @Test
    public void testManGetFilterChanges() throws Exception {
        aiManj.manGetFilterChanges(Numeric.toBigInt("0x16")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getFilterChanges\","
                + "\"params\":[\"0x16\"],\"id\":1}");
    }

    @Test
    public void testManGetFilterLogs() throws Exception {
        aiManj.manGetFilterLogs(Numeric.toBigInt("0x16")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getFilterLogs\","
                + "\"params\":[\"0x16\"],\"id\":1}");
    }

    @Test
    public void testManGetLogs() throws Exception {
        aiManj.manGetLogs(new ManFilter().addSingleTopic(
                "0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b"))
                .send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getLogs\","
                + "\"params\":[{\"topics\":["
                + "\"0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b\"]}],"
                + "\"id\":1}");
    }

    @Test
    public void testManGetLogsWithNumericBlockRange() throws Exception {
        aiManj.manGetLogs(new ManFilter(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8")),
                DefaultBlockParameter.valueOf("latest"), ""))
                .send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"man_getLogs\","
                        + "\"params\":[{\"topics\":[],\"fromBlock\":\"0xe8\","
                        + "\"toBlock\":\"latest\",\"address\":[\"\"]}],\"id\":1}");
    }

    @Test
    public void testManGetWork() throws Exception {
        aiManj.manGetWork().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_getWork\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testManSubmitWork() throws Exception {
        aiManj.manSubmitWork("0x0000000000000001",
                "0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef",
                "0xD1FE5700000000000000000000000000D1FE5700000000000000000000000000").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_submitWork\","
                + "\"params\":[\"0x0000000000000001\","
                + "\"0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef\","
                + "\"0xD1FE5700000000000000000000000000D1FE5700000000000000000000000000\"],"
                + "\"id\":1}");
    }

    @Test
    public void testManSubmitHashRate() throws Exception {
        aiManj.manSubmitHashrate(
                "0x0000000000000000000000000000000000000000000000000000000000500000",
                "0x59daa26581d0acd1fce254fb7e85952f4c09d0915afd33d3886cd914bc7d283c").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"man_submitHashrate\","
                + "\"params\":["
                + "\"0x0000000000000000000000000000000000000000000000000000000000500000\","
                + "\"0x59daa26581d0acd1fce254fb7e85952f4c09d0915afd33d3886cd914bc7d283c\"],"
                + "\"id\":1}");
    }

    @Test
    public void testDbPutString() throws Exception {
        aiManj.dbPutString("testDB", "myKey", "myString").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_putString\","
                + "\"params\":[\"testDB\",\"myKey\",\"myString\"],\"id\":1}");
    }

    @Test
    public void testDbGetString() throws Exception {
        aiManj.dbGetString("testDB", "myKey").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_getString\","
                + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
    }

    @Test
    public void testDbPutHex() throws Exception {
        aiManj.dbPutHex("testDB", "myKey", "0x68656c6c6f20776f726c64").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_putHex\","
                + "\"params\":[\"testDB\",\"myKey\",\"0x68656c6c6f20776f726c64\"],\"id\":1}");
    }

    @Test
    public void testDbGetHex() throws Exception {
        aiManj.dbGetHex("testDB", "myKey").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_getHex\","
                + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
    }

    @Test
    public void testShhVersion() throws Exception {
        aiManj.shhVersion().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_version\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testShhPost() throws Exception {
        //CHECKSTYLE:OFF
        aiManj.shhPost(new ShhPost(
                "0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1",
                "0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1",
                Arrays.asList("0x776869737065722d636861742d636c69656e74", "0x4d5a695276454c39425154466b61693532"),
                "0x7b2274797065223a226d6",
                Numeric.toBigInt("0x64"),
                Numeric.toBigInt("0x64"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_post\",\"params\":[{\"from\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\",\"to\":\"0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1\",\"topics\":[\"0x776869737065722d636861742d636c69656e74\",\"0x4d5a695276454c39425154466b61693532\"],\"payload\":\"0x7b2274797065223a226d6\",\"priority\":\"0x64\",\"ttl\":\"0x64\"}],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhNewIdentity() throws Exception {
        aiManj.shhNewIdentity().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_newIdentity\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testShhHasIdentity() throws Exception {
        //CHECKSTYLE:OFF
        aiManj.shhHasIdentity("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_hasIdentity\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhNewGroup() throws Exception {
        aiManj.shhNewGroup().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_newGroup\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testShhAddToGroup() throws Exception {
        //CHECKSTYLE:OFF
        aiManj.shhAddToGroup("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_addToGroup\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhNewFilter() throws Exception {
        //CHECKSTYLE:OFF
        aiManj.shhNewFilter(
                new ShhFilter("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1")
                        .addSingleTopic("0x12341234bf4b564f")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_newFilter\",\"params\":[{\"topics\":[\"0x12341234bf4b564f\"],\"to\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"}],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhUninstallFilter() throws Exception {
        aiManj.shhUninstallFilter(Numeric.toBigInt("0x7")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_uninstallFilter\","
                + "\"params\":[\"0x07\"],\"id\":1}");
    }

    @Test
    public void testShhGetFilterChanges() throws Exception {
        aiManj.shhGetFilterChanges(Numeric.toBigInt("0x7")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_getFilterChanges\","
                + "\"params\":[\"0x07\"],\"id\":1}");
    }

    @Test
    public void testShhGetMessages() throws Exception {
        aiManj.shhGetMessages(Numeric.toBigInt("0x7")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_getMessages\","
                + "\"params\":[\"0x07\"],\"id\":1}");
    }
}
