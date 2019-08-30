package org.aimanj.utils;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.DefaultBlockParameter;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManBlock;
import org.aimanj.protocol.core.methods.response.ManBlockNumber;
import org.aimanj.protocol.core.methods.response.Transaction;
import org.aimanj.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanChain {
    public static AiManj aiManj =  AiManj.build(new HttpService("https://testnet.matrix.io"));
    public static int count = 0;
    public static int selectedCount = 0;
    public static Boolean status = false;
    public static List<Map<String, String>> list = new ArrayList<>();

    public static List scan (String to, String value, String data, BigInteger blockNumber) throws Exception {
        count = count + 1;
        if (status) {
            selectedCount = selectedCount + 1;
        }
        System.out.println(blockNumber);
        ManBlock manBlock = aiManj.manGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send();
        List transactions = manBlock.getBlock().getTransactions();
        Map<String, String> map = new HashMap<>();
        for(int i = 0, length = transactions.size(); i < length; i++) {
            Transaction transaction = (Transaction) transactions.get(i);
            if (transaction.getInput().equals(data) && transaction.getTo().equals(to)) {
                status = true;
                map.put("data", transaction.getInput());
                map.put("hash", transaction.getHash());
                list.add(map);
            }
        }
        if (count == 2 || selectedCount == 1) {
            return list;
        } else {
            BigInteger bi2 = new BigInteger("1");
            return scan(to, value, data, blockNumber.subtract(bi2));
        }
    }


    public static void main(String[] args) throws Exception {
        Request<?, ManBlockNumber> request = aiManj.manBlockNumber();
        BigInteger blockNumber = request.send().getBlockNumber();
        List content = scan("MAN.4cWdQKn5L6uhdUjvVWj9PVfQ5vJU", "0xd37896e2583cf3", "0x", blockNumber);
    }
}
