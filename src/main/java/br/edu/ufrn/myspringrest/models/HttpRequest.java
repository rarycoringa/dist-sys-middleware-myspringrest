package br.edu.ufrn.myspringrest.models;

import java.util.Map;

import br.edu.ufrn.myspringrest.enums.HttpMethod;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private String version;
    private Map<String, String> headers;
    private String body;

    public HttpRequest() {}

    public HttpRequest(
        HttpMethod method,
        String path,
        String version,
        Map<String, String> headers,
        String body
    ) {
        this.method = method;
        this.path = path;
        this.version = version;
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

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String,String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String,String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
}
