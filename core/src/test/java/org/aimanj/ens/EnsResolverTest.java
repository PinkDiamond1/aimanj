package org.aimanj.ens;

import java.io.IOException;
import java.math.BigInteger;

import org.aimanj.protocol.AiManj;
import org.aimanj.protocol.core.methods.response.ManBlock;
import org.aimanj.protocol.core.methods.response.ManCall;
import org.junit.Before;
import org.junit.Test;

import org.aimanj.abi.TypeEncoder;
import org.aimanj.abi.datatypes.Utf8String;
import org.aimanj.protocol.AiManjService;
import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.ManSyncing;
import org.aimanj.protocol.core.methods.response.NetVersion;
import org.aimanj.tx.ChainId;
import org.aimanj.utils.Numeric;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.aimanj.ens.EnsResolver.DEFAULT_SYNC_THRESHOLD;
import static org.aimanj.ens.EnsResolver.isValidEnsName;

public class EnsResolverTest {

    private AiManj aiManj;
    private AiManjService aiManjService;
    private EnsResolver ensResolver;

    @Before
    public void setUp() {
        aiManjService = mock(AiManjService.class);
        aiManj = AiManj.build(aiManjService);
        ensResolver = new EnsResolver(aiManj);
    }

    @Test
    public void testResolve() throws Exception {
        configureSyncing(false);
        configureLatestBlock(System.currentTimeMillis() / 1000);  // block timestamp is in seconds

        NetVersion netVersion = new NetVersion();
        netVersion.setResult(Byte.toString(ChainId.MAINNET));

        String resolverAddress =
                "0x0000000000000000000000004c641fb9bad9b60ef180c31f56051ce826d21a9a";
        String contractAddress =
                "0x00000000000000000000000019e03255f667bdfd50a32722df860b1eeaf4d635";

        ManCall resolverAddressResponse = new ManCall();
        resolverAddressResponse.setResult(resolverAddress);

        ManCall contractAddressResponse = new ManCall();
        contractAddressResponse.setResult(contractAddress);

        when(aiManjService.send(any(Request.class), eq(NetVersion.class)))
                .thenReturn(netVersion);
        when(aiManjService.send(any(Request.class), eq(ManCall.class)))
                .thenReturn(resolverAddressResponse);
        when(aiManjService.send(any(Request.class), eq(ManCall.class)))
                .thenReturn(contractAddressResponse);

        assertThat(ensResolver.resolve("aimanj.man"),
                is("0x19e03255f667bdfd50a32722df860b1eeaf4d635"));
    }

    @Test
    public void testReverseResolve() throws Exception {
        configureSyncing(false);
        configureLatestBlock(System.currentTimeMillis() / 1000);  // block timestamp is in seconds

        NetVersion netVersion = new NetVersion();
        netVersion.setResult(Byte.toString(ChainId.MAINNET));

        String resolverAddress =
                "0x0000000000000000000000004c641fb9bad9b60ef180c31f56051ce826d21a9a";
        String contractName =
                "0x0000000000000000000000000000000000000000000000000000000000000020"
                + TypeEncoder.encode(new Utf8String("aimanj.man"));
        System.err.println(contractName);

        ManCall resolverAddressResponse = new ManCall();
        resolverAddressResponse.setResult(resolverAddress);

        ManCall contractNameResponse = new ManCall();
        contractNameResponse.setResult(contractName);

        when(aiManjService.send(any(Request.class), eq(NetVersion.class)))
                .thenReturn(netVersion);
        when(aiManjService.send(any(Request.class), eq(ManCall.class)))
                .thenReturn(resolverAddressResponse);
        when(aiManjService.send(any(Request.class), eq(ManCall.class)))
                .thenReturn(contractNameResponse);

        assertThat(ensResolver.reverseResolve("0x19e03255f667bdfd50a32722df860b1eeaf4d635"),
                is("aimanj.man"));
    }

    @Test
    public void testIsSyncedSyncing() throws Exception {
        configureSyncing(true);

        assertFalse(ensResolver.isSynced());
    }

    @Test
    public void testIsSyncedFullySynced() throws Exception {
        configureSyncing(false);
        configureLatestBlock(System.currentTimeMillis() / 1000);  // block timestamp is in seconds

        assertTrue(ensResolver.isSynced());
    }

    @Test
    public void testIsSyncedBelowThreshold() throws Exception {
        configureSyncing(false);
        configureLatestBlock((System.currentTimeMillis() / 1000) - DEFAULT_SYNC_THRESHOLD);

        assertFalse(ensResolver.isSynced());
    }

    private void configureSyncing(boolean isSyncing) throws IOException {
        ManSyncing manSyncing = new ManSyncing();
        ManSyncing.Result result = new ManSyncing.Result();
        result.setSyncing(isSyncing);
        manSyncing.setResult(result);

        when(aiManjService.send(any(Request.class), eq(ManSyncing.class)))
                .thenReturn(manSyncing);
    }

    private void configureLatestBlock(long timestamp) throws IOException {
        ManBlock.Block block = new ManBlock.Block();
        block.setTimestamp(Numeric.encodeQuantity(BigInteger.valueOf(timestamp)));
        ManBlock manBlock = new ManBlock();
        manBlock.setResult(block);

        when(aiManjService.send(any(Request.class), eq(ManBlock.class)))
                .thenReturn(manBlock);
    }

    @Test
    public void testIsEnsName() {
        assertTrue(isValidEnsName("man"));
        assertTrue(isValidEnsName("aiManj.man"));
        assertTrue(isValidEnsName("0x19e03255f667bdfd50a32722df860b1eeaf4d635.man"));

        assertFalse(isValidEnsName("0x19e03255f667bdfd50a32722df860b1eeaf4d635"));
        assertFalse(isValidEnsName("19e03255f667bdfd50a32722df860b1eeaf4d635"));

        assertTrue(isValidEnsName(""));
    }
}
