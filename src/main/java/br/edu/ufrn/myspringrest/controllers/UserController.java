package br.edu.ufrn.myspringrest.controllers;

import br.edu.ufrn.myspringrest.annotations.params.BodyParam;
import br.edu.ufrn.myspringrest.annotations.params.PathParam;
import br.edu.ufrn.myspringrest.enums.HttpMethod;

import java.util.HashMap;

import br.edu.ufrn.myspringrest.annotations.RequestMapping;
import br.edu.ufrn.myspringrest.annotations.RestController;

// temporary file, just for tests purposes

@RestController
public class UserController {
    
    @RequestMapping(path = "/users")
    public void retrieveUsers() {}

    @RequestMapping(path = "/users/{id}")
    public void retrieveUser(@PathParam int id) {}

    @RequestMapping(path = "/users", method = HttpMethod.POST)
    public void createUser(
        @BodyParam("email") String email,
        @BodyParam("first_name") String firstName,
        @BodyParam("last_name") String lastName,
        @BodyParam("age") int age,
        @BodyParam("staff") boolean staff
    ) {}

    @RequestMapping(path = "/users/{id}", method = HttpMethod.DELETE)
    public void deleteUser(@PathParam int id) {}

    @RequestMapping(path = "/users/{id}/more")
    public void moreUser(@PathParam int id, @BodyParam("email") String email, @BodyParam("name") String name) {}

}
