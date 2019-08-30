package org.aimanj.protocol.parity.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * parity_getDappDefaultAddress
 * parity_getNewDappsDefaultAddress.
 */
public class ParityDefaultAddressResponse extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
