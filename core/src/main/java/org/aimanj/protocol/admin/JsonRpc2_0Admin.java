package org.aimanj.protocol.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.admin.methods.response.NewAccountIdentifier;
import org.aimanj.protocol.admin.methods.response.PersonalListAccounts;
import org.aimanj.protocol.admin.methods.response.PersonalUnlockAccount;
import org.aimanj.protocol.core.JsonRpc2_0AiManj;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;

/**
 * JSON-RPC 2.0 factory implementation for common Parity and Gman.
 */
public class JsonRpc2_0Admin extends JsonRpc2_0AiManj implements Admin {

    public JsonRpc2_0Admin(AiManjService aiManjService) {
        super(aiManjService);
    }
    
    public JsonRpc2_0Admin(AiManjService aiManjService, long pollingInterval,
                           ScheduledExecutorService scheduledExecutorService) {
        super(aiManjService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, PersonalListAccounts> personalListAccounts() {
        return new Request<>(
                "personal_listAccounts",
                Collections.<String>emptyList(),
                aiManjService,
                PersonalListAccounts.class);
    }

    @Override
    public Request<?, NewAccountIdentifier> personalNewAccount(String password) {
        return new Request<>(
                "personal_newAccount",
                Arrays.asList(password),
                aiManjService,
                NewAccountIdentifier.class);
    }   

    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password,
            BigInteger duration) {
        List<Object> attributes = new ArrayList<>(3);
        attributes.add(accountId);
        attributes.add(password);
        
        if (duration != null) {
            // Parity has a bug where it won't support a duration
            // See https://github.com/mancore/parity/issues/1215
            attributes.add(duration.longValue());
        } else {
            // we still need to include the null value, otherwise Parity rejects request
            attributes.add(null);
        }
        
        return new Request<>(
                "personal_unlockAccount",
                attributes,
                aiManjService,
                PersonalUnlockAccount.class);
    }
    
    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password) {
        
        return personalUnlockAccount(accountId, password, null);
    }
    
    @Override
    public Request<?, ManSendTransaction> personalSendTransaction(
            Transaction transaction, String passphrase) {
        return new Request<>(
                "personal_sendTransaction",
                Arrays.asList(transaction, passphrase),
                aiManjService,
                ManSendTransaction.class);
    }
    
}
