package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;

public class ManDepositInfo {
    public String Address;
    public String SignAddress;
    public BigInteger Deposit;
    public BigInteger WithdrawH;
    public BigInteger OnlineTime;
    public BigInteger Role;


    public ManDepositInfo(String address, String signAddress, BigInteger deposit, BigInteger withdrawH, BigInteger onlineTime, BigInteger role) {
        Address = address;
        SignAddress = signAddress;
        Deposit = deposit;
        WithdrawH = withdrawH;
        OnlineTime = onlineTime;
        Role = role;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSignAddress() {
        return SignAddress;
    }

    public void setSignAddress(String signAddress) {
        SignAddress = signAddress;
    }

    public BigInteger getDeposit() {
        return Deposit;
    }

    public void setDeposit(BigInteger deposit) {
        Deposit = deposit;
    }

    public BigInteger getWithdrawH() {
        return WithdrawH;
    }

    public void setWithdrawH(BigInteger withdrawH) {
        WithdrawH = withdrawH;
    }

    public BigInteger getOnlineTime() {
        return OnlineTime;
    }

    public void setOnlineTime(BigInteger onlineTime) {
        OnlineTime = onlineTime;
    }

    public BigInteger getRole() {
        return Role;
    }

    public void setRole(BigInteger role) {
        Role = role;
    }
}
