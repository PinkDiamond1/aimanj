package org.aimanj.crypto;

import java.math.BigInteger;
import java.util.List;

import org.aimanj.utils.Numeric;

/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">
 * yellow paper</a>.
 */
public class RawTransaction {

    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data;
    private BigInteger txEnterType;
    private BigInteger IsEntrustTx;
    private BigInteger commitTime;
    private List<Object> extra_to;

    protected RawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                           BigInteger value, String data, String currency, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime,
                             List extra_to) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        this.value = value;
        this.txEnterType = txEnterType;
        this.IsEntrustTx = IsEntrustTx;
        this.commitTime = commitTime;
        this.extra_to = extra_to;

        if (data != null) {
            this.data = Numeric.cleanHexPrefix(data);
        }
    }

    public static RawTransaction createContractTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, BigInteger value,
            String init, String currency, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime,
            List extra_to) {

        return new RawTransaction(nonce, gasPrice, gasLimit, "", value, init, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public static RawTransaction createManTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data, String currency,
            BigInteger value, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime,
            List extra_to) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);

    }

    public static RawTransaction createTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data, String currency,
            BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime,
            List extra_to) {
        return createTransaction(nonce, gasPrice, gasLimit, to, BigInteger.ZERO, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public static RawTransaction createTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data, String currency, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime,
            List extra_to) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    public BigInteger getTxEnterType() {
        return txEnterType;
    }

    public BigInteger getIsEntrustTx() {
        return IsEntrustTx;
    }

    public BigInteger getCommitTime() {
        return commitTime;
    }

    public List getExtra_to() {
        return extra_to;
    }
}
