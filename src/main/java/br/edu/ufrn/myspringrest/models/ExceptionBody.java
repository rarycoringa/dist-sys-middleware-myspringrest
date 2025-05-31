package br.edu.ufrn.myspringrest.models;

public class ExceptionBody {
    private String detail;

    public ExceptionBody(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
