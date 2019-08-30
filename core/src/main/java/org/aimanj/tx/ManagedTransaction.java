package org.aimanj.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.aimanj.ens.EnsResolver;
import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.ManGasPrice;
import org.aimanj.protocol.core.methods.response.TransactionReceipt;
import org.aimanj.protocol.exceptions.TransactionException;


/**
 * Generic transaction manager.
 */
public abstract class ManagedTransaction {

    /**
     * @deprecated use ContractGasProvider
     * @see org.aimanj.tx.gas.DefaultGasProvider
     */
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);

    protected AiManj aiManj;

    protected TransactionManager transactionManager;

    protected EnsResolver ensResolver;

    protected ManagedTransaction(AiManj aiManj, TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.aiManj = aiManj;
        this.ensResolver = new EnsResolver(aiManj);
    }

    /**
     * This should only be used in case you need to get the {@link EnsResolver#syncThreshold}
     * parameter, which dictates the threshold in milliseconds since the last processed block
     * timestamp should be to considered in sync the blockchain.
     *
     * <p>It is currently experimental and only used in ENS name resolution, but will probably
     * be made available for read calls in the future.
     *
     * @return sync threshold value in milliseconds
     */
    public long getSyncThreshold() {
        return ensResolver.getSyncThreshold();
    }

    /**
     * This should only be used in case you need to modify the {@link EnsResolver#syncThreshold}
     * parameter, which dictates the threshold in milliseconds since the last processed block
     * timestamp should be to considered in sync the blockchain.
     *
     * <p>It is currently experimental and only used in ENS name resolution, but will probably
     * be made available for read calls in the future.
     *
     * @param syncThreshold the sync threshold in milliseconds
     */
    public void setSyncThreshold(long syncThreshold) {
        ensResolver.setSyncThreshold(syncThreshold);
    }

    /**
     * Return the current gas price from the matrix node.
     * <p>
     *     Note: this method was previously called {@code getGasPrice} but was renamed to
     *     distinguish it when a bean accessor method on {@link Contract} was added with that name.
     *     If you have a Contract subclass that is calling this method (unlikely since those
     *     classes are usually generated and until very recently those generated subclasses were
     *     marked {@code final}), then you will need to change your code to call this method
     *     instead, if you want the dynamic behavior.
     * </p>
     * @return the current gas price, determined dynamically at invocation
     * @throws IOException if there's a problem communicating with the matrix node
     */
    public BigInteger requestCurrentGasPrice() throws IOException {
        ManGasPrice manGasPrice = aiManj.manGasPrice().send();

        return manGasPrice.getGasPrice();
    }

    protected TransactionReceipt send(
            String to, String data, String currency, BigInteger value, BigInteger gasPrice, BigInteger gasLimit, BigInteger txEnterType, BigInteger IsEntrustTx, BigInteger commitTime, List extra_to)
            throws IOException, TransactionException {

        return transactionManager.executeTransaction(
                gasPrice, gasLimit, to, data, currency, value, txEnterType, IsEntrustTx, commitTime, extra_to);
    }
}
