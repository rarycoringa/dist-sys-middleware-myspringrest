package br.edu.ufrn.myspringrest.controllers;

public class User {
    private String email;
    private String first_name;
    private String last_name;
    private int age;
    private boolean staff;

    public User(
        String email,
        String first_name,
        String last_name,
        int age,
        boolean staff
    ) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.staff = staff;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getStaff() {
        return this.staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

}
