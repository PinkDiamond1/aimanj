package org.aimanj.protocol.core.methods.request;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.aimanj.utils.Numeric;

import java.util.*;

/**
 * Transaction request object used the below methods.
 * <ol>
 *     <li>man_call</li>
 *     <li>man_sendTransaction</li>
 *     <li>man_estimateGas</li>
 * </ol>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    // default as per https://github.com/Matrix/wiki/wiki/JSON-RPC#man_sendtransaction
    public static final BigInteger DEFAULT_GAS = BigInteger.valueOf(9000);

    private String from;
    private String to;
    private BigInteger gas;
    private BigInteger gasPrice;
    private BigInteger value;
    private String data;
    public String currency;
    private BigInteger nonce;  // nonce field is not present on man_call/man_estimateGas
    private BigInteger txEnterType;
    private BigInteger IsEntrustTx;
    private BigInteger commitTime;
    private List extra_to;

    public Transaction(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                       String to, BigInteger value, String data, String currency, BigInteger txEnterType, BigInteger IsEntrustTx
            , BigInteger commitTime, List extra_to) {
        this.from = from;
        this.to = to;
        this.gas = gasLimit;
        this.gasPrice = gasPrice;
        this.value = value;
        this.txEnterType = txEnterType;
        this.IsEntrustTx = IsEntrustTx;
        this.commitTime = commitTime;
        this.extra_to = extra_to;
        this.currency = currency;

        if (data != null) {
            this.data = Numeric.prependHexPrefix(data);
        }

        this.nonce = nonce;
    }

    public static Transaction createContractTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
            BigInteger value, String init, String currency, BigInteger txEnterType, BigInteger IsEntrustTx
            , BigInteger commitTime, List extra_to) {

        return new Transaction(from, nonce, gasPrice, gasLimit, null, value, init, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public static Transaction createContractTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, String init, String currency, BigInteger txEnterType, BigInteger IsEntrustTx
            , BigInteger commitTime, List extra_to) {

        return createContractTransaction(from, nonce, gasPrice, null, null, init, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public static Transaction createManTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data, String currency, BigInteger txEnterType, BigInteger IsEntrustTx
            , BigInteger commitTime, List extra_to) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public static Transaction createFunctionCallTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data, String currency, BigInteger txEnterType, BigInteger IsEntrustTx
            , BigInteger commitTime, List extra_to) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public static Transaction createFunctionCallTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, String currency, BigInteger txEnterType, BigInteger IsEntrustTx
            , BigInteger commitTime, List extra_to) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, null, data, currency, txEnterType, IsEntrustTx, commitTime, extra_to);
    }

    public static Transaction createManCallTransaction(String from, String to, String data, String currency) {
        return new Transaction(from, null, null, null, to, null, data, currency,null, null, null, null);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getGas() {
        return convert(gas);
    }

    public String getGasPrice() {
        return convert(gasPrice);
    }

    public String getValue() {
        return convert(value);
    }

    public String getData() {
        return data;
    }

    public String getNonce() {
        return convert(nonce);
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null;  // we don't want the field to be encoded if not present
        }
    }
}
