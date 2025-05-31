package br.edu.ufrn.myspringrest;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import br.edu.ufrn.myspringrest.exceptions.HttpException;
import br.edu.ufrn.myspringrest.models.ExceptionBody;
import br.edu.ufrn.myspringrest.models.HttpRequest;
import br.edu.ufrn.myspringrest.models.HttpResponse;

public class MySpringRestApp {
    private static final Logger logger = Logger.getLogger(MySpringRestApp.class.getName());

    public static void handleConnection(
        Socket connection,
        Invoker invoker
    ) {
        int statusCode;
        String statusCodeName;
        Object bodyObject;
            
        try {
            HttpRequest request = ServerRequestHandler.buildHttpRequest(connection);
            
            bodyObject = invoker.invoke(request);

            statusCode = 200;
            statusCodeName = "Success";

        } catch (HttpException exception) {
            bodyObject = new ExceptionBody(exception.getMessage());
            
            statusCode = exception.getStatusCode();
            statusCodeName = exception.getStatusCodeName();
            
            logger.warning(exception.getMessage());
        } catch (Exception exception) {
            bodyObject = new ExceptionBody(exception.getMessage());
            
            statusCode = 500;
            statusCodeName = "Internal Server Error";
            
            logger.warning(exception.getMessage());
        }

        HttpResponse httpResponse = ServerRequestHandler.buildHttpResponse(
            statusCode,
            statusCodeName,
            bodyObject
        );
            
        try {
            ServerRequestHandler.sendHttpResponse(connection, httpResponse);
            ServerRequestHandler.close(connection);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
    
    public static void run(Class<?> app) throws Exception {
        Invoker invoker = new Invoker(app.getPackage().getName());

        invoker.register();

        ServerRequestHandler server = new ServerRequestHandler();

        logger.info(
            "MySpringRest Server is running...\n"
            + "Host: " + ServerRequestHandler.address.getHostAddress() + "\n"
            + "Port: " + ServerRequestHandler.port + "\n"
        );

        while (true) {
            Socket connection = server.accept();
            new Thread(() -> handleConnection(
                connection,
                invoker
            )).start();
        }
    }
}
