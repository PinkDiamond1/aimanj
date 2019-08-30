package org.aimanj.protocol.parity.methods.response;

import java.util.ArrayList;

import org.aimanj.protocol.core.Response;

/**
 * parity_listAccounts
 * parity_getGetDappAddresses
 * parity_getGetNewDappsAddresses
 * parity_importGmanAccounts
 * parity_listGmanAccounts.
 */
public class ParityAddressesResponse extends Response<ArrayList<String>> {
    public ArrayList<String> getAddresses() {
        return getResult();
    }
}
