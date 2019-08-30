package org.aimanj.protocol.pantheon;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.admin.methods.response.BooleanResponse;
import org.aimanj.protocol.core.DefaultBlockParameter;
import org.aimanj.protocol.core.JsonRpc2_0AiManj;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManAccounts;
import org.aimanj.protocol.core.methods.response.MinerStartResponse;
import org.aimanj.protocol.pantheon.response.PantheonManAccountsMapResponse;
import org.aimanj.protocol.pantheon.response.PantheonFullDebugTraceResponse;

public class JsonRpc2_0Pantheon extends JsonRpc2_0AiManj implements Pantheon {
    public JsonRpc2_0Pantheon(AiManjService aiManjService) {
        super(aiManjService);
    }

    @Override
    public Request<?, MinerStartResponse> minerStart() {
        return new Request<>(
                "miner_start",
                Collections.<String>emptyList(),
                aiManjService,
                MinerStartResponse.class);
    }

    @Override
    public Request<?, BooleanResponse> minerStop() {
        return new Request<>(
                "miner_stop",
                Collections.<String>emptyList(),
                aiManjService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, BooleanResponse> clicqueDiscard(String address) {
        return new Request<>(
                "clique_discard",
                Arrays.asList(address),
                aiManjService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, ManAccounts> clicqueGetSigners(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "clique_getSigners",
                Arrays.asList(defaultBlockParameter.getValue()),
                aiManjService,
                ManAccounts.class);
    }

    @Override
    public Request<?, ManAccounts> clicqueGetSignersAtHash(String blockHash) {
        return new Request<>(
                "clique_getSignersAtHash",
                Arrays.asList(blockHash),
                aiManjService,
                ManAccounts.class);
    }

    @Override
    public Request<?, BooleanResponse> cliquePropose(String address, Boolean signerAddition) {
        return new Request<>(
                "clique_propose",
                Arrays.asList(address, signerAddition),
                aiManjService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, PantheonManAccountsMapResponse> cliqueProposals() {
        return new Request<>(
                "clique_proposals",
                Collections.<String>emptyList(),
                aiManjService,
                PantheonManAccountsMapResponse.class);
    }

    @Override
    public Request<?, PantheonFullDebugTraceResponse> debugTraceTransaction(
            String transactionHash, Map<String, Boolean> options) {
        return new Request<>(
                "debug_traceTransaction",
                Arrays.asList(transactionHash, options),
                aiManjService,
                PantheonFullDebugTraceResponse.class);
    }
}
