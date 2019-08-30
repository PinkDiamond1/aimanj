package org.aimanj.crypto;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.aimanj.crypto.SecureRandomUtils.isAndroidRuntime;
import static org.aimanj.crypto.SecureRandomUtils.secureRandom;

public class SecureRandomUtilsTest {

    @Test
    public void testSecureRandom() {
        secureRandom().nextInt();
    }

    @Test
    public void testIsNotAndroidRuntime() {
        assertFalse(isAndroidRuntime());
    }
}
