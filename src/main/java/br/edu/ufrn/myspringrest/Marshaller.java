package br.edu.ufrn.myspringrest;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

public class Marshaller {

    public static String encode(Object object) {
        String encoded;
        
        if (object == null) {
            encoded = "";
        } else if (object.getClass().isArray()) {
            encoded = new JSONArray(object).toString();
        } else {
            encoded = new JSONObject(object).toString();
        }

        return encoded;
    }

    public static Map<String, Object> decode(String object) {
        if (object == null) {
            object = "";
        }

        Map<String, Object> decoded = new JSONObject(object).toMap();

        return decoded;
    }
}
