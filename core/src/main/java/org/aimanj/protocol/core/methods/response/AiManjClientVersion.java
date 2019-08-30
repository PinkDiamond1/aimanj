package org.aimanj.protocol.core.methods.response;

import org.aimanj.protocol.core.Response;

/**
 * aiManj_clientVersion.
 */
public class AiManjClientVersion extends Response<String> {

    public String getAiManjClientVersion() {
        return getResult();
    }
}
