package org.aimanj.utils;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.methods.response.*;
import org.aimanj.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.Optional;

public class testApi {
    public static void main(String[] args) throws Exception {
        AiManj aiManj =  AiManj.build(new HttpService("https://api85.matrix.io"));
        ManBlock block = aiManj.manGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
//        ManBlock.TransactionObject aa = (ManBlock.TransactionObject)block.getBlock().getTransactions().get(0);
        System.out.println(block);
    }
}
