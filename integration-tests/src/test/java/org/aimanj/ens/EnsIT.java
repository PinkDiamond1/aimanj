package org.aimanj.ens;

import org.junit.Test;

import org.aimanj.protocol.aiManj;
import org.aimanj.protocol.http.HttpService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EnsIT {

    @Test
    public void testEns() throws Exception {

        aiManj aiManj = aiManj.build(new HttpService());
        EnsResolver ensResolver = new EnsResolver(aiManj);

        assertThat(ensResolver.resolve("aimanj.test"),
                is("0x19e03255f667bdfd50a32722df860b1eeaf4d635"));
    }
}
