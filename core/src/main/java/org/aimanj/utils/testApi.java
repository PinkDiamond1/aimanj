package org.aimanj.utils;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.methods.response.*;
import org.aimanj.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.Optional;

public class testApi {
    public static void main(String[] args) throws Exception {
        AiManj aiManj =  AiManj.build(new HttpService("https://testnet.matrix.io"));
        BigInteger b = new BigInteger("0");
        ManTransaction manTransaction = aiManj.manGetTransactionByHash("0xe33e99efdc80a87ce9e85b7757180579ac7fb4ca597b215087748d0fe2f1a7a6").send();
        Optional<Transaction> transaction = manTransaction.getTransaction();
        System.out.println(transaction);
    }
}
