package org.aimanj.protocol.gman;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.Flowable;

import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.admin.JsonRpc2_0Admin;
import org.aimanj.protocol.admin.methods.response.BooleanResponse;
import org.aimanj.protocol.admin.methods.response.PersonalSign;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManSubscribe;
import org.aimanj.protocol.core.methods.response.MinerStartResponse;
import org.aimanj.protocol.gman.response.PersonalEcRecover;
import org.aimanj.protocol.gman.response.PersonalImportRawKey;
import org.aimanj.protocol.websocket.events.PendingTransactionNotification;
import org.aimanj.protocol.websocket.events.SyncingNotfication;

/**
 * JSON-RPC 2.0 factory implementation for Gman.
 */
public class JsonRpc2_0Gman extends JsonRpc2_0Admin implements Gman {

    public JsonRpc2_0Gman(AiManjService aiManjService) {
        super(aiManjService);
    }
    
    @Override
    public Request<?, PersonalImportRawKey> personalImportRawKey(
            String keydata, String password) {
        return new Request<>(
                "personal_importRawKey",
                Arrays.asList(keydata, password),
                aiManjService,
                PersonalImportRawKey.class);
    }

    @Override
    public Request<?, BooleanResponse> personalLockAccount(String accountId) {
        return new Request<>(
                "personal_lockAccount",
                Arrays.asList(accountId),
                aiManjService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, PersonalSign> personalSign(
            String message, String accountId, String password) {
        return new Request<>(
                "personal_sign",
                Arrays.asList(message,accountId,password),
                aiManjService,
                PersonalSign.class);
    }

    @Override
    public Request<?, PersonalEcRecover> personalEcRecover(
            String hexMessage, String signedMessage) {
        return new Request<>(
                "personal_ecRecover",
                Arrays.asList(hexMessage,signedMessage),
                aiManjService,
                PersonalEcRecover.class);
    }

    @Override
    public Request<?, MinerStartResponse> minerStart(int threadCount) {
        return new Request<>(
                "miner_start",
                Arrays.asList(threadCount),
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

    public Flowable<PendingTransactionNotification> newPendingTransactionsNotifications() {
        return aiManjService.subscribe(
                new Request<>(
                        "man_subscribe",
                        Arrays.asList("newPendingTransactions"),
                        aiManjService,
                        ManSubscribe.class),
                "man_unsubscribe",
                PendingTransactionNotification.class
        );
    }

    @Override
    public Flowable<SyncingNotfication> syncingStatusNotifications() {
        return aiManjService.subscribe(
                new Request<>(
                        "man_subscribe",
                        Arrays.asList("syncing"),
                        aiManjService,
                        ManSubscribe.class),
                "man_unsubscribe",
                SyncingNotfication.class
        );
    }
}
