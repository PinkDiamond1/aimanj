package org.aimanj.protocol.parity.methods.response;

import org.aimanj.crypto.WalletFile;
import org.aimanj.protocol.core.Response;

/**
 * parity_ExportAccount.
 */
public class ParityExportAccount extends Response<WalletFile> {
    public WalletFile getWallet() {
        return getResult();
    }
}
