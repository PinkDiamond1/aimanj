package org.aimanj.protocol.parity.methods.response;

import java.util.ArrayList;

import org.aimanj.protocol.core.Response;

/**
 * parity_ListRecentDapps.
 */
public class ParityListRecentDapps extends Response<ArrayList<String>> {
    public ArrayList<String> getDappsIds() {
        return getResult();
    }
}
