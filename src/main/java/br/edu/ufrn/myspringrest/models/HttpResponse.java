package br.edu.ufrn.myspringrest.models;

import java.util.Map;

public class HttpResponse {
    private int statusCode;
    private String statusCodeName;
    private Map<String, String> headers;
    private String body;

    public HttpResponse() {}

    public HttpResponse(
        int statusCode,
        String statusCodeName,
        Map<String, String> headers,
        String body
    ) {
        this.statusCode = statusCode;
        this.statusCodeName = statusCodeName;
        this.headers = headers;
        this.body = body;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCodeName() {
        return this.statusCodeName;
    }

    public void setStatusCodeName(String statusCodeName) {
        this.statusCodeName = statusCodeName;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
