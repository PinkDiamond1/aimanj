package org.aimanj.utils;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.DefaultBlockParameterNumber;
import org.aimanj.protocol.core.methods.request.Transaction;
import org.aimanj.protocol.core.methods.response.ManBlock;
import org.aimanj.protocol.core.methods.response.ManCall;
import org.aimanj.protocol.core.methods.response.ManGetTransactionCount;
import org.aimanj.protocol.core.methods.response.ManTransaction;
import org.aimanj.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class testaaa {
    public static void main(String[] args) throws Exception {
        // man_call
        AiManj aiManj =  AiManj.build(new HttpService("https://testnet.matrix.io"));
        ManGetTransactionCount manGetTransactionCount = aiManj
                .manGetTransactionCount("MAN.JLuY5HhAiEW7KB8bh1EbN41gPCqT", new DefaultBlockParameterNumber(1000000000)).send();
        System.out.println(manGetTransactionCount);
        //        List list = new ArrayList();
//        ManGetTransactionCount count = aiManj
//                .manGetTransactionCount("MAN.5xYzBHrJfXeJi9yQ8Qq8hvm19bU4", DefaultBlockParameterName.LATEST).send();
//        Transaction transaction = new Transaction("MAN.aR54tfdEWDfsfHhdsy6BkRVGRYLj",count.getTransactionCount(),
//                new BigInteger("18000000000"), new BigInteger("210000"), "MAN.aR54tfdEWDfsfHhdsy6BkRVGRYLj", BigInteger.ZERO, "","MAN",BigInteger.ZERO,BigInteger.ZERO, BigInteger.ZERO, list);
//        ManCall manCall = aiManj.manCall(transaction, DefaultBlockParameterName.LATEST).send();
//        System.out.println(manCall.getValue());
    }

}
