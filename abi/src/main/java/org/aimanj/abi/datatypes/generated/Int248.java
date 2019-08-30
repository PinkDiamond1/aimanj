package org.aimanj.abi.datatypes.generated;

import java.math.BigInteger;
import org.aimanj.abi.datatypes.Int;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.aimanj.codegen.AbiTypesGenerator in the
 * <a href="https://github.com/aiManj/aiManj/tree/master/codegen">codegen module</a> to update.
 */
public class Int248 extends Int {
    public static final Int248 DEFAULT = new Int248(BigInteger.ZERO);

    public Int248(BigInteger value) {
        super(248, value);
    }

    public Int248(long value) {
        this(BigInteger.valueOf(value));
    }
}
