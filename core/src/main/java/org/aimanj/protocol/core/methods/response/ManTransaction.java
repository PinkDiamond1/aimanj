package org.aimanj.protocol.core.methods.response;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;

import org.aimanj.protocol.ObjectMapperFactory;
import org.aimanj.protocol.core.Response;

/**
 * Transaction object returned by:
 * <ul>
 * <li>man_getTransactionByHash</li>
 * <li>man_getTransactionByBlockHashAndIndex</li>
 * <li>man_getTransactionByBlockNumberAndIndex</li>
 * </ul>
 *
 * <p>This differs slightly from the request {@link ManSendTransaction} Transaction object.</p>
 *
 * <p>See
 * <a href="https://github.com/Matrix/wiki/wiki/JSON-RPC#man_gettransactionbyhash">docs</a>
 * for further details.</p>
 */
public class ManTransaction extends Response<Transaction> {

    public Optional<Transaction> getTransaction() {
        return Optional.ofNullable(getResult());
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Transaction> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Transaction deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Transaction.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
