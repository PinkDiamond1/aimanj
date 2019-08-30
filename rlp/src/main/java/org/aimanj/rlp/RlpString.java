package org.aimanj.rlp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aimanj.utils.Numeric;

/**
 * RLP string type.
 */
public class RlpString implements RlpType {
    private static final byte[] EMPTY = new byte[]{ };

    private final byte[] value;

    private RlpString(byte[] value) {
        this.value = value;
    }

    public byte[] getBytes() {
        return value;
    }

    public BigInteger asPositiveBigInteger() {
        if (value.length == 0) {
            return BigInteger.ZERO;
        }
        return new BigInteger(1, value);
    }

    public String asString() {
        return Numeric.toHexString(value);
    }

    public String asNormalString() {
        return new String(value);
    }
    public static RlpString create(byte[] value) {
        return new RlpString(value);
    }

    public static RlpList create(List list){
        RlpList rlpList = new RlpList(new ArrayList<>());
        RlpList resultRlpList = new RlpList(new ArrayList<>());
        if(list.size() > 0) {
            rlpList.getValues().add(create(((BigInteger)((List)((List)list.get(0)).get(0)).get(0))));
            rlpList.getValues().add(create(((BigInteger)((List)((List)list.get(0)).get(0)).get(1))));
            List tos = (List)((List)((List)list.get(0)).get(0)).get(2);
            if (tos.size() > 0) {
                RlpList toList = new RlpList(new ArrayList<>());
                for (int i = 0, length = tos.size(); i < length; i++) {
                    RlpList tempList = new RlpList(new ArrayList<>());
                    List itemList = ((List)tos.get(i));
                    if (itemList.size() > 0) {
                        tempList.getValues().add(create((String)(itemList.get(0))));
                        tempList.getValues().add(create((BigInteger)(itemList.get(1))));
                        tempList.getValues().add(create((BigInteger)itemList.get(2)));
                    }
                    toList.getValues().add(tempList);
                }
                rlpList.getValues().add(toList);
            } else {
                RlpList toList = new RlpList(new ArrayList<>());
                rlpList.getValues().add(toList);
            }
            resultRlpList.getValues().add(rlpList);
        }
        return resultRlpList;
    }

    public static RlpString create(byte value) {
        return new RlpString(new byte[]{ value });
    }

    public static RlpString create(BigInteger value) {
        // RLP encoding only supports positive integer values
        if (value.signum() < 1) {
            return new RlpString(EMPTY);
        } else {
            byte[] bytes = value.toByteArray();
            if (bytes[0] == 0) {  // remove leading zero
                return new RlpString(Arrays.copyOfRange(bytes, 1, bytes.length));
            } else {
                return new RlpString(bytes);
            }
        }
    }

    public static RlpString create(long value) {
        return create(BigInteger.valueOf(value));
    }

    public static RlpString create(String value) {
        return new RlpString(value.getBytes());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RlpString rlpString = (RlpString) o;

        return Arrays.equals(value, rlpString.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
