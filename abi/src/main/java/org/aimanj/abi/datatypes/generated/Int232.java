package org.aimanj.abi.datatypes.generated;

import java.math.BigInteger;
import org.aimanj.abi.datatypes.Int;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.aimanj.codegen.AbiTypesGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 */
public class Int232 extends Int {
    public static final Int232 DEFAULT = new Int232(BigInteger.ZERO);

    public Int232(BigInteger value) {
        super(232, value);
    }

    public Int232(long value) {
        this(BigInteger.valueOf(value));
    }
}
