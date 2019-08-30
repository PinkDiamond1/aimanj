package org.aimanj.protocol.admin;

import java.math.BigInteger;
import java.util.concurrent.ScheduledExecutorService;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.admin.methods.response.NewAccountIdentifier;
import org.aimanj.protocol.admin.methods.response.PersonalListAccounts;
import org.aimanj.protocol.admin.methods.response.PersonalUnlockAccount;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;

/**
 * JSON-RPC Request object building factory for common Parity and Gman.
 */
public interface Admin extends AiManj {

    static Admin build(AiManjService aiManjService) {
        return new JsonRpc2_0Admin(aiManjService);
    }
    
    static Admin build(
            AiManjService aiManjService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Admin(aiManjService, pollingInterval, scheduledExecutorService);
    }

    public Request<?, PersonalListAccounts> personalListAccounts();
    
    public Request<?, NewAccountIdentifier> personalNewAccount(String password);
    
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase, BigInteger duration);
    
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase);
    
    public Request<?, ManSendTransaction> personalSendTransaction(
            Transaction transaction, String password);

}   
