package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;

/**
 * @program: base
 * @description:
 * @author: Li.Jie
 * @create: 2019-07-19 11:04
 **/
public class MatrixCoinConfig {
    public String CoinRange;
    public String CoinType;
    public BigInteger PackNum;
    public String CoinUnit;
    public String CoinTotal;
    public String CoinAddress;


    public MatrixCoinConfig(String coinRange, String coinType, BigInteger packNum, String coinUnit, String coinTotal, String coinAddress) {
        CoinRange = coinRange;
        CoinType = coinType;
        PackNum = packNum;
        CoinUnit = coinUnit;
        CoinTotal = coinTotal;
        CoinAddress = coinAddress;
    }


    public String getCoinRange() {
        return CoinRange;
    }

    public void setCoinRange(String coinRange) {
        CoinRange = coinRange;
    }

    public String getCoinType() {
        return CoinType;
    }

    public void setCoinType(String coinType) {
        CoinType = coinType;
    }

    public BigInteger getPackNum() {
        return PackNum;
    }

    public void setPackNum(BigInteger packNum) {
        PackNum = packNum;
    }

    public String getCoinUnit() {
        return CoinUnit;
    }

    public void setCoinUnit(String coinUnit) {
        CoinUnit = coinUnit;
    }

    public String getCoinTotal() {
        return CoinTotal;
    }

    public void setCoinTotal(String coinTotal) {
        CoinTotal = coinTotal;
    }

    public String getCoinAddress() {
        return CoinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        CoinAddress = coinAddress;
    }
}
