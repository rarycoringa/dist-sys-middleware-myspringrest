package br.edu.ufrn.myspringrest;

import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

public class Marshaller {

    public static String encode(Object object) {
        String encoded;
        
        if (object == null) {
            encoded = "";
        }

        if (object.getClass().isArray()) {
            encoded = new JSONArray(object).toString();
        } else {
            encoded = new JSONObject(object).toString();
        }

        return encoded;
    }

    public static Map<String, Object> decode(String object) {
        return new JSONObject(object).toMap();
    }
}
