package org.aimanj.rlp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RLP list type.
 */
public class RlpList implements RlpType {
    private final List<RlpType> values;

    public RlpList(RlpType... values) {
        this.values = Arrays.asList(values);
    }

    public RlpList(List<RlpType> values) {
        this.values = values;
    }

    public List<RlpType> getValues() {
        return values;
    }
    public List getListValues() {
        List result = new ArrayList();
        List resultList = new ArrayList();
        List temp = new ArrayList();
        temp.add(((RlpString)(((RlpList)this.values.get(0)).getValues().get(0))).asPositiveBigInteger());
        temp.add(((RlpString)(((RlpList)this.values.get(0)).getValues().get(1))).asPositiveBigInteger());
        List toList = ((RlpList)(((RlpList)this.values.get(0)).getValues().get(2))).getValues();
        List toTempList = new ArrayList();
        for (int i = 0, length = toList.size(); i < length; i++) {
            List itemTempList = new ArrayList();
            List itemList = ((RlpList)toList.get(i)).getValues();
            if(itemList.size() > 0) {
                String address = ((RlpString)itemList.get(0)).asNormalString();
                BigInteger value = ((RlpString)itemList.get(1)).asPositiveBigInteger();
                itemTempList.add(address);
                itemTempList.add(value);
                itemTempList.add(BigInteger.ZERO);
            }
            toTempList.add(itemTempList);
        }
        temp.add(toTempList);
        result.add(temp);
        resultList.add(result);
        return resultList;
    }
}
