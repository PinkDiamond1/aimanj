package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * man_uninstallFilter.
 */
public class ManUninstallFilter extends Response<Boolean> {
    public boolean isUninstalled() {
        return getResult();
    }
}
