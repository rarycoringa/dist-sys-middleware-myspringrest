package br.edu.ufrn.myspringrest.controllers;

import br.edu.ufrn.myspringrest.annotations.params.BodyParam;
import br.edu.ufrn.myspringrest.annotations.params.PathParam;
import br.edu.ufrn.myspringrest.enums.HttpMethod;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufrn.myspringrest.annotations.RequestMapping;
import br.edu.ufrn.myspringrest.annotations.RestController;

// temporary file, just for tests purposes

@RestController
public class UserController {
    
    @RequestMapping(path = "/users")
    public User[] retrieveUsers() {
        User[] users = new User[] {
            new User("rary@gmail.com", "Rary", "Coringa", 26, true),
            new User("emanuel@gmail.com", "Emanuel", "Coringa", 26, true)
        };

        return users;
    }

    @RequestMapping(path = "/users/{id}")
    public void retrieveUser(@PathParam int id) {}

    @RequestMapping(path = "/users", method = HttpMethod.POST)
    public User createUser(
        @BodyParam("email") String email,
        @BodyParam("first_name") String firstName,
        @BodyParam("last_name") String lastName,
        @BodyParam("age") int age,
        @BodyParam("staff") boolean staff
    ) {
        User user = new User(
            email,
            firstName,
            lastName,
            age,
            staff
        );

        return user;
    }

    @RequestMapping(path = "/users/{id}", method = HttpMethod.DELETE)
    public void deleteUser(@PathParam int id) {}

    @RequestMapping(path = "/users/{id}/more")
    public void moreUser(@PathParam int id, @BodyParam("email") String email, @BodyParam("name") String name) {}

}
