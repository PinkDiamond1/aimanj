package org.aimanj.protocol.pantheon;

import java.util.Map;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.admin.methods.response.BooleanResponse;
import org.aimanj.protocol.core.DefaultBlockParameter;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManAccounts;
import org.aimanj.protocol.core.methods.response.MinerStartResponse;
import org.aimanj.protocol.pantheon.response.PantheonManAccountsMapResponse;
import org.aimanj.protocol.pantheon.response.PantheonFullDebugTraceResponse;

public interface Pantheon extends AiManj {
    static Pantheon build(AiManjService aiManjService) {
        return new JsonRpc2_0Pantheon(aiManjService);
    }

    Request<?, MinerStartResponse> minerStart();

    Request<?, BooleanResponse> minerStop();

    Request<?, BooleanResponse> clicqueDiscard(String address);

    Request<?, ManAccounts> clicqueGetSigners(DefaultBlockParameter defaultBlockParameter);

    Request<?, ManAccounts> clicqueGetSignersAtHash(String blockHash);

    Request<?, BooleanResponse> cliquePropose(String address, Boolean signerAddition);

    Request<?, PantheonManAccountsMapResponse> cliqueProposals();

    Request<?, PantheonFullDebugTraceResponse> debugTraceTransaction(
            String transactionHash, Map<String, Boolean> options);
}
