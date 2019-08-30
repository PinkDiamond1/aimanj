package org.aimanj.protocol.scenarios;

import java.math.BigInteger;

import org.junit.Test;

import org.aimanj.crypto.Hash;
import org.aimanj.crypto.RawTransaction;
import org.aimanj.crypto.TransactionEncoder;
import org.aimanj.protocol.core.methods.response.ManSign;
import org.aimanj.utils.Convert;
import org.aimanj.utils.Numeric;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Sign transaction using Matrix node.
 */
public class SignTransactionIT extends Scenario {

    @Test
    public void testSignTransaction() throws Exception {
        boolean accountUnlocked = unlockAccount();
        assertTrue(accountUnlocked);

        RawTransaction rawTransaction = createTransaction();

        byte[] encoded = TransactionEncoder.encode(rawTransaction);
        byte[] hashed = Hash.sha3(encoded);

        ManSign manSign = aiManj.manSign(ALICE.getAddress(), Numeric.toHexString(hashed))
                .sendAsync().get();

        String signature = manSign.getSignature();
        assertNotNull(signature);
        assertFalse(signature.isEmpty());
    }

    private static RawTransaction createTransaction() {
        BigInteger value = Convert.toWei("1", Convert.Unit.MAN).toBigInteger();

        return RawTransaction.createManTransaction(
                BigInteger.valueOf(1048587), BigInteger.valueOf(500000), BigInteger.valueOf(500000),
                "0x9C98E381Edc5Fe1Ac514935F3Cc3eDAA764cf004","", "MAN",
                value, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, null);
    }
}
