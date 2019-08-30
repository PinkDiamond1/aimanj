package org.aimanj.protocol.pantheon;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.aimanj.protocol.RequestTester;
import org.aimanj.protocol.core.DefaultBlockParameter;
import org.aimanj.protocol.http.HttpService;

public class RequestTest extends RequestTester {
    private Pantheon aiManj;

    @Override
    protected void initAiManjClient(HttpService httpService) {
            aiManj = Pantheon.build(httpService);
    }

    @Test
    public void testMinerStart() throws Exception {
        aiManj.minerStart().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"miner_start\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testMinerStop() throws Exception {
        aiManj.minerStop().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"miner_stop\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testClicqueDiscard() throws Exception {
        final String accountId = "0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73";
        aiManj.clicqueDiscard(accountId).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"clique_discard\","
                + "\"params\":[\"0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73\"],\"id\":1}");
    }

    @Test
    public void testClicqueGetSigners() throws Exception {
        final DefaultBlockParameter blockParameter = DefaultBlockParameter.valueOf("latest");
        aiManj.clicqueGetSigners(blockParameter).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"clique_getSigners\","
                + "\"params\":[\"latest\"],\"id\":1}");
    }

    @Test
    public void testClicqueGetSignersAtHash() throws Exception {
        final String blockHash =
                "0x98b2ddb5106b03649d2d337d42154702796438b3c74fd25a5782940e84237a48";
        aiManj.clicqueGetSignersAtHash(blockHash).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"clique_getSignersAtHash\",\"params\":"
                + "[\"0x98b2ddb5106b03649d2d337d42154702796438b3c74fd25a5782940e84237a48\"]"
                + ",\"id\":1}");
    }

    @Test
    public void testClicquePropose() throws Exception {
        final String signerAddress = "0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73";
        aiManj.cliquePropose(signerAddress, true).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"clique_propose\","
                + "\"params\":[\"0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73\",true],\"id\":1}");
    }

    @Test
    public void testClicqueProposals() throws Exception {
        aiManj.cliqueProposals().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"clique_proposals\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testDebugTraceTransaction() throws Exception {
        final String transactionHash = "0xc171033d5cbff7175f29dfd3a63dda3d6f8f385e";

        Map<String, Boolean> options = new HashMap<>();
        options.put("disableStorage", false);
        options.put("disableStack", false);
        options.put("disableMemory", true);

        aiManj.debugTraceTransaction(transactionHash, options).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"debug_traceTransaction\","
                + "\"params\":[\"0xc171033d5cbff7175f29dfd3a63dda3d6f8f385e\","
                + "{\"disableMemory\":true,"
                + "\"disableStorage\":false,"
                + "\"disableStack\":false}],"
                + "\"id\":1}");
        //CHECKSTYLE:ON
    }

}
