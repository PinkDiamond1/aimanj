package org.aimanj.protocol.ipc;

import java.io.IOException;

import org.aimanj.protocol.AiManj;
import org.junit.Before;
import org.junit.Test;

import org.aimanj.protocol.core.Request;
import org.aimanj.protocol.core.methods.response.AiManjClientVersion;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IpcServiceTest {

    private IpcService ipcService;
    private IOFacade ioFacade;

    @Before
    public void setUp() {
        ioFacade = mock(IOFacade.class);
        ipcService = new IpcService() {
            @Override
            protected IOFacade getIO() {
                return ioFacade;
            }
        };
    }

    @Test
    public void testSend() throws IOException {
        when(ioFacade.read()).thenReturn(
                "{\"jsonrpc\":\"2.0\",\"id\":1,"
                        + "\"result\":\"Gman/v1.5.4-stable-b70acf3c/darwin/go1.7.3\"}\n");

        ipcService.send(new Request(), AiManjClientVersion.class);

        verify(ioFacade).write("{\"jsonrpc\":\"2.0\",\"method\":null,\"params\":null,\"id\":0}");
    }
}
