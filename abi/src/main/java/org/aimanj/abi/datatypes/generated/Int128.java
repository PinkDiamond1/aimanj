package org.aimanj.abi.datatypes.generated;

import java.math.BigInteger;
import org.aimanj.abi.datatypes.Int;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.aimanj.codegen.AbiTypesGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 */
public class Int128 extends Int {
    public static final Int128 DEFAULT = new Int128(BigInteger.ZERO);

    public Int128(BigInteger value) {
        super(128, value);
    }

    public Int128(long value) {
        this(BigInteger.valueOf(value));
    }
}
