package org.aimanj.protocol.infura;

import java.util.Collections;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.aimanj.protocol.infura.InfuraHttpService.buildClientVersionHeader;

public class InfuraHttpServiceTest {

    @Test
    public void testBuildHeader() {
        assertTrue(buildClientVersionHeader("", false).isEmpty());
        assertTrue(buildClientVersionHeader(null, false).isEmpty());

        assertThat(buildClientVersionHeader("gman 1.4.19", true),
                equalTo(Collections.singletonMap(
                        "Infura-Matrix-Preferred-Client",
                        "gman 1.4.19")));

        assertThat(buildClientVersionHeader("gman 1.4.19", false),
                is(Collections.singletonMap(
                        "Infura-Matrix-Preferred-Client",
                        "gman 1.4.19; required=false")));
    }
}
