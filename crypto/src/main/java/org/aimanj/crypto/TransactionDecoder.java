package org.aimanj.crypto;

import java.math.BigInteger;
import java.util.List;

import org.aimanj.rlp.RlpDecoder;
import org.aimanj.rlp.RlpList;
import org.aimanj.rlp.RlpString;
import org.aimanj.utils.Numeric;

public class TransactionDecoder {

    public static RawTransaction decode(String hexTransaction) {
        byte[] transaction = Numeric.hexStringToByteArray(hexTransaction);
        RlpList rlpList = RlpDecoder.decode(transaction);
        RlpList values = (RlpList) rlpList.getValues().get(0);
        BigInteger nonce = ((RlpString) values.getValues().get(0)).asPositiveBigInteger();
        BigInteger gasPrice = ((RlpString) values.getValues().get(1)).asPositiveBigInteger();
        BigInteger gasLimit = ((RlpString) values.getValues().get(2)).asPositiveBigInteger();
        String to = ((RlpString) values.getValues().get(3)).asNormalString();
        String currency = "MAN";
        if (!to.equals("") && to.indexOf(".") > -1) {
            currency = to.split("\\.")[0];
        }
        BigInteger value = ((RlpString) values.getValues().get(4)).asPositiveBigInteger();
        String data = ((RlpString) values.getValues().get(5)).asString();
        if (values.getValues().size() > 10) {
            byte v = ((RlpString) values.getValues().get(6)).getBytes()[0];
            byte[] r = Numeric.toBytesPadded(
                Numeric.toBigInt(((RlpString) values.getValues().get(7)).getBytes()), 32);
            byte[] s = Numeric.toBytesPadded(
                Numeric.toBigInt(((RlpString) values.getValues().get(8)).getBytes()), 32);
            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
            BigInteger txEnterType = ((RlpString) values.getValues().get(9)).asPositiveBigInteger();
            BigInteger IsEntrustTx = ((RlpString) values.getValues().get(10)).asPositiveBigInteger();
            BigInteger commitTime = ((RlpString) values.getValues().get(11)).asPositiveBigInteger();
            List extra_to = ((RlpList) values.getValues().get(12)).getListValues();
            return new SignedRawTransaction(nonce, gasPrice, gasLimit,
                to, value, data, currency, signatureData, txEnterType, IsEntrustTx, commitTime, extra_to);
        } else {
            BigInteger txEnterType = ((RlpString) values.getValues().get(6)).asPositiveBigInteger();
            BigInteger IsEntrustTx = ((RlpString) values.getValues().get(7)).asPositiveBigInteger();
            BigInteger commitTime = ((RlpString) values.getValues().get(8)).asPositiveBigInteger();
            List extra_to = ((RlpList) values.getValues().get(12)).getListValues();
            return RawTransaction.createTransaction(nonce,
                gasPrice, gasLimit, to, value, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
        }
    }
    
}
