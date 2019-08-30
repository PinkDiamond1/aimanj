package org.aimanj.protocol.gman;

import io.reactivex.Flowable;

import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.admin.Admin;
import org.aimanj.protocol.admin.methods.response.BooleanResponse;
import org.aimanj.protocol.admin.methods.response.PersonalSign;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.MinerStartResponse;
import org.aimanj.protocol.gman.response.PersonalEcRecover;
import org.aimanj.protocol.gman.response.PersonalImportRawKey;
import org.aimanj.protocol.websocket.events.PendingTransactionNotification;
import org.aimanj.protocol.websocket.events.SyncingNotfication;

/**
 * JSON-RPC Request object building factory for Gman.
 */
public interface Gman extends Admin {
    static Gman build(AiManjService aiManjService) {
        return new JsonRpc2_0Gman(aiManjService);
    }
        
    Request<?, PersonalImportRawKey> personalImportRawKey(String keydata, String password);

    Request<?, BooleanResponse> personalLockAccount(String accountId);
    
    Request<?, PersonalSign> personalSign(String message, String accountId, String password);
    
    Request<?, PersonalEcRecover> personalEcRecover(String message, String signiture);

    Request<?, MinerStartResponse> minerStart(int threadCount);

    Request<?, BooleanResponse> minerStop();

    /**
     * Creates an {@link Flowable} instance that emits a notification when a new transaction is
     * added to the pending state and is signed with a key that is available in the node.
     *
     * @return a {@link Flowable} instance that emits a notification when a new transaction is
     *         added to the pending state
     */
    Flowable<PendingTransactionNotification> newPendingTransactionsNotifications();

    /**
     * Creates an {@link Flowable} instance that emits a notification when a node starts or stops
     * syncing.
     * @return a {@link Flowable} instance that emits changes to syncing status
     */
    Flowable<SyncingNotfication> syncingStatusNotifications();

}
