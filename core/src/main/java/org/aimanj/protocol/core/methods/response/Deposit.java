package org.aimanj.protocol.core.methods.response;

import java.math.BigInteger;
import java.util.Objects;


public class Deposit {

    private String Address;
    private String SignAddress;
    private BigInteger Deposit;
    private BigInteger WithdrawH;
    private BigInteger OnlineTime;
    private BigInteger Role;

    public Deposit(String address, String signAddress, BigInteger deposit, BigInteger withdrawH, BigInteger onlineTime, BigInteger role) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposit)) return false;
        Deposit deposit = (Deposit) o;
        return Objects.equals(getAddress(), deposit.getAddress()) &&
                Objects.equals(getSignAddress(), deposit.getSignAddress()) &&
                Objects.equals(getDeposit(), deposit.getDeposit()) &&
                Objects.equals(getWithdrawH(), deposit.getWithdrawH()) &&
                Objects.equals(getOnlineTime(), deposit.getOnlineTime()) &&
                Objects.equals(getRole(), deposit.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getSignAddress(), getDeposit(), getWithdrawH(), getOnlineTime(), getRole());
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "Address='" + Address + '\'' +
                ", SignAddress='" + SignAddress + '\'' +
                ", Deposit=" + Deposit +
                ", WithdrawH=" + WithdrawH +
                ", OnlineTime=" + OnlineTime +
                ", Role=" + Role +
                '}';
    }
}
