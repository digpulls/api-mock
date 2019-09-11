package helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SerializationHelper {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> String serialize(T requestType) throws JsonProcessingException {
        return mapper.writeValueAsString(requestType);
    }

    public static <T> T deserialize(String json, Class<T> toType) throws IOException {
        return mapper.readValue(json, toType);
    }

    public static <T> T deserialize(String json, TypeReference toType) throws IOException {
        return mapper.readValue(json, toType);
    }
}
