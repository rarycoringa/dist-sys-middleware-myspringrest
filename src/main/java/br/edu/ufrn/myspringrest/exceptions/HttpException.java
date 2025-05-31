package br.edu.ufrn.myspringrest.exceptions;

public class HttpException extends Exception {
    int statusCode;
    String statusCodeName;

    public HttpException(int statusCode, String statusCodeName, String message) {
        super(message);
        this.statusCode = statusCode;
        this.statusCodeName = statusCodeName;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusCodeName() {
        return statusCodeName;
    }
}
