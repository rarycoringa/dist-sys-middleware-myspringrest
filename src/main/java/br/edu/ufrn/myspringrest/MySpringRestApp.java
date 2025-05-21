package br.edu.ufrn.myspringrest;

import java.util.HashMap;

import br.edu.ufrn.myspringrest.enums.HttpMethod;
import br.edu.ufrn.myspringrest.models.HttpRequest;

public class MySpringRestApp {
    public static void run(Class<?> app) throws Exception {
        Invoker invoker = new Invoker(app.getPackage().getName());

        invoker.register();

        HttpRequest request = new HttpRequest(
            HttpMethod.GET,
            "/users/1/more",
            new HashMap<String,String>(),
            "{\"email\":\"my@ufrn.edu.br\",\"name\":\"Rary Coringa\"}"
        );

        invoker.invoke(request);
    }
}
