package com.haalthy.service.common;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @author John
 * @version 1.0 2015/5/18 12:51
 */
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static Map<String, String> fromJson(String json) throws Exception {
        return mapper.readValue(json, Map.class);
    }
}
