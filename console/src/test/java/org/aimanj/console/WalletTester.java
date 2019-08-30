package org.aimanj.console;

import org.junit.Before;

import org.aimanj.TempFileProvider;

import static org.mockito.Mockito.mock;
import static org.aimanj.crypto.SampleKeys.PASSWORD;

public abstract class WalletTester extends TempFileProvider {

    static final char[] WALLET_PASSWORD = PASSWORD.toCharArray();

    IODevice console;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        console = mock(IODevice.class);
    }
}
