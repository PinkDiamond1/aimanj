package org.aimanj.protocol.core.methods.response;

import java.util.Collections;
import java.util.List;

import org.aimanj.abi.FunctionReturnDecoder;
import org.aimanj.abi.TypeReference;
import org.aimanj.abi.datatypes.Type;
import org.aimanj.abi.datatypes.Utf8String;
import org.aimanj.abi.datatypes.generated.AbiTypes;
import org.aimanj.protocol.core.Response;

/**
 * man_call.
 */
public class ManCall extends Response<String> {

    // Numeric.toHexString(Hash.sha3("Error(string)".getBytes())).substring(0, 10)
    private static final String errorMethodId = "0x08c379a0";

    @SuppressWarnings("unchecked")
    private static final List<TypeReference<Type>> revertReasonType = Collections.singletonList(
            TypeReference.create((Class<Type>) AbiTypes.getType("string")));

    public String getValue() {
        return getResult();
    }

    public boolean reverts() {
        return getValue() != null && getValue().startsWith(errorMethodId);
    }

    public String getRevertReason() {
        if (reverts()) {
            String hexRevertReason = getValue().substring(errorMethodId.length());
            List<Type> decoded = FunctionReturnDecoder.decode(hexRevertReason, revertReasonType);
            Utf8String decodedRevertReason = (Utf8String) decoded.get(0);
            return decodedRevertReason.getValue();
        }
        return null;
    }
}