package org.aimanj.utils;

import org.junit.Test;

import static org.aimanj.utils.Assertions.verifyPrecondition;

public class AssertionsTest {

    @Test
    public void testVerifyPrecondition() {
        verifyPrecondition(true, "");
    }

    @Test(expected = RuntimeException.class)
    public void testVerifyPreconditionFailure() {
        verifyPrecondition(false, "");
    }
}
