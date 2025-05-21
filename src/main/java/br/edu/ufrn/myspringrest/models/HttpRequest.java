package br.edu.ufrn.myspringrest.models;

import java.util.HashMap;

import br.edu.ufrn.myspringrest.enums.HttpMethod;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private HashMap<String, String> headers;
    private String body;

    public HttpRequest(
        HttpMethod method,
        String path,
        HashMap<String, String> headers,
        String body
    ) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.body = body;
    }


    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HashMap<String,String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(HashMap<String,String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
}
