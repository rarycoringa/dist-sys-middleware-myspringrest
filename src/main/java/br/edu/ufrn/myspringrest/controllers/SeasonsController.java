package br.edu.ufrn.myspringrest.controllers;

import br.edu.ufrn.myspringrest.annotations.params.BodyParam;
import br.edu.ufrn.myspringrest.annotations.params.PathParam;
import br.edu.ufrn.myspringrest.annotations.RequestMapping;
import br.edu.ufrn.myspringrest.annotations.RestController;

// temporary file, just for tests purposes

@RestController
public class SeasonsController {
    
    @RequestMapping
    public void retrieveSeasons() {}

    // @GetMapping("/{id}")
    // public void retrieveSeason(@PathParam int id) {}

    // @PostMapping
    // public void createUser(@BodyParam User user) {}

    // @DeleteMapping("/{id}")
    // public void deleteUser(@PathParam int id) {}
}
