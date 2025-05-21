package br.edu.ufrn.myspringrest;

import java.util.Map;

import org.json.JSONObject;

public class Marshaller {

    public static String encode(Object object) {
        return new JSONObject(object).toString();
    }

    public static Map<String, Object> decode(String object) {
        return new JSONObject(object).toMap();
    }
}
