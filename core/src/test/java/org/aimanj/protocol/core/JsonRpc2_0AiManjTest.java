package org.aimanj.protocol.core;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

import org.aimanj.protocol.AiManj;
import org.junit.Test;

import org.aimanj.protocol.AiManjService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JsonRpc2_0AiManjTest {

    private ScheduledExecutorService scheduledExecutorService
            = mock(ScheduledExecutorService.class);
    private AiManjService service = mock(AiManjService.class);

    private AiManj aiManj = AiManj.build(service, 10, scheduledExecutorService);

    @Test
    public void testStopExecutorOnShutdown() throws Exception {
        aiManj.shutdown();

        verify(scheduledExecutorService).shutdown();
        verify(service).close();
    }

    @Test(expected = RuntimeException.class)
    public void testExceptionOnServiceClosure() throws Exception {
        doThrow(new IOException("Failed to close"))
                .when(service).close();

        aiManj.shutdown();
    }
}