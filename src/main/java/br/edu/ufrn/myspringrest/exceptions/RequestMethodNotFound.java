package br.edu.ufrn.myspringrest.exceptions;

public class RequestMethodNotFound extends Exception {
    public RequestMethodNotFound(String message) {
        super(message);
    }
}
