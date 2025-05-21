package br.edu.ufrn.myspringrest.models;

import java.util.HashMap;

public class HttpResponse {
    private int statusCode;
    private String statusCodeName;
    private HashMap headers;
    private HashMap body;

    public HttpResponse() {
        
    }
}
