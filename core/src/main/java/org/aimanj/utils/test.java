package org.aimanj.utils;

import org.aimanj.crypto.*;
import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameterName;
import org.aimanj.protocol.core.methods.response.ManGetTransactionCount;
import org.aimanj.protocol.core.methods.response.ManSendTransaction;
import org.aimanj.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class test {

    public static void main(String[] args) throws Exception {
        AiManj aiManj =  AiManj.build(new HttpService("https://testnet.matrix.io"));
        ManGetTransactionCount count = aiManj.manGetTransactionCount("MAN.5xYzBHrJfXeJi9yQ8Qq8hvm19bU4", DefaultBlockParameterName.LATEST).send();

        List list = new ArrayList();
        List listResult = new ArrayList();
        List list1 = new ArrayList();
        list1.add(BigInteger.ZERO);
        list1.add(BigInteger.ZERO);
        List toList = new ArrayList();
        toList.add("MAN.5xYzBHrJfXeJi9yQ8Qq8hvm19bU4"); //  一对一注视
        toList.add(new BigInteger("1")); //  一对一注视
        toList.add(BigInteger.ZERO); //  一对一注视
        List tempList = new ArrayList();
        tempList.add(toList); //  一对一注视
        list1.add(tempList);
        listResult.add(list1);
        list.add(listResult);
        byte chainid = 3;
        RawTransaction rawTransaction = RawTransaction.createTransaction(count.getTransactionCount(), new BigInteger("18000000000"),
                new BigInteger("210000"), "MAN.2nRsUetjWAaYUizRkgBxGETimfUTz", "0x" + str2HexStr("12131"), "MAN", BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, list);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainid, Credentials.create("3ec3678077a79400081e525f516d722bce7d19f80b7288d0992c84c2481c5faa"));
        String hexValue = Numeric.toHexString(signedMessage);
        SignedRawTransaction rawTransaction1 = (SignedRawTransaction) TransactionDecoder.decode(hexValue);
        System.out.println(rawTransaction1.getFrom());
        HashMap map = new HashMap();
        map.put("nonce", Numeric.toHexStringWithPrefix(rawTransaction1.getNonce()));
        map.put("v", "0x" + byteToHex(rawTransaction1.getSignatureData().getV()));
        map.put("r", Numeric.toHexString(rawTransaction1.getSignatureData().getR()));
        map.put("s", Numeric.toHexString(rawTransaction1.getSignatureData().getS()));
        map.put("to", rawTransaction1.getTo());
        map.put("currency", rawTransaction1.getTo().split("\\.")[0]);
        map.put("gasPrice", Numeric.toHexStringWithPrefix(rawTransaction1.getGasPrice()));
        map.put("gas", Numeric.toHexStringWithPrefix(rawTransaction1.getGasLimit()));
        map.put("commitTime", rawTransaction1.getCommitTime());
        map.put("isEntrustTx", rawTransaction1.getIsEntrustTx());
        map.put("txEnterType", rawTransaction1.getTxEnterType());
        List extraToList = (List)((List)((List)rawTransaction1.getExtra_to().get(0)).get(0)).get(2);
        List extraTo = new ArrayList();
        for (int i = 0, length = extraToList.size();i < length;i++) {
            HashMap<String, String> toMap = new HashMap<String, String>();
            toMap.put("to", (String) ((List)extraToList.get(0)).get(0));
            toMap.put("value", Numeric.toHexStringWithPrefix((BigInteger) ((List)extraToList.get(0)).get(1)));
            extraTo.add(toMap);
        }
        map.put("extra_to", extraTo);
        map.put("data", "0x" + rawTransaction1.getData());
        map.put("txType", ((List)((List)rawTransaction1.getExtra_to().get(0)).get(0)).get(0));
        map.put("lockHeight", ((List)((List)rawTransaction1.getExtra_to().get(0)).get(0)).get(1));
        map.put("value", Numeric.toHexStringWithPrefix(rawTransaction1.getValue()));
        ManSendTransaction transactionResponse = aiManj.manSendRawTransaction(map).sendAsync().get();
        String hash = transactionResponse.getTransactionHash();
        System.out.println(hash);
    }
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }
}
