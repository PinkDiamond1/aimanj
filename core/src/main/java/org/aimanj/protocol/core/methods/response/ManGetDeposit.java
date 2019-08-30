package org.aimanj.protocol.core.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.aimanj.protocol.ObjectMapperFactory;
import org.aimanj.protocol.core.Response;

import java.io.IOException;
import java.util.*;

/**
 *man_getBalance.
 */
public class ManGetDeposit extends Response<List<Map<String, Object>>> {

    public List<Map<String, Object>> getDeposit() {
        return getResult();
    }
}
