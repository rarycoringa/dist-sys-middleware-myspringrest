package br.edu.ufrn.myspringrest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import br.edu.ufrn.myspringrest.enums.HttpMethod;
import br.edu.ufrn.myspringrest.exceptions.HttpException;
import br.edu.ufrn.myspringrest.models.HttpRequest;
import br.edu.ufrn.myspringrest.models.HttpResponse;

public class ServerRequestHandler {
    private static final Logger logger = Logger.getLogger(ServerRequestHandler.class.getName());

    public static int port = 8000;
    public static InetAddress address;
    public static ServerSocket serverSocket;

    static {
        try {
            address = InetAddress.getLocalHost();
            serverSocket = new ServerSocket(port);
        } catch (UnknownHostException exception) { 
            throw new RuntimeException(exception);
        } catch (IOException exception) { 
            throw new RuntimeException(exception);
        }
    }

    public Socket accept() {
        Socket socket;

        try {
            socket = serverSocket.accept();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        return socket;
    }

    public static HttpRequest buildHttpRequest(
        Socket socket
    ) throws HttpException {
        HttpRequest httpRequest;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            StringBuilder requestBuilder = new StringBuilder();
            
            String line;
            
            while (!(line = in.readLine()).isEmpty()) {
                requestBuilder.append(line).append("\n");
            }
            
            String headersWithHeadline = requestBuilder.toString();
            
            String headlineRaw = headersWithHeadline.split("\n")[0];

            String[] headlineParts = headlineRaw.split(" ");
            HttpMethod method = HttpMethod.valueOf(headlineParts[0]);
            String path = headlineParts[1];
            String version = headlineParts[2];

            String headersRaw = Arrays.stream(headersWithHeadline.split("\n"))
                .skip(1)
                .takeWhile(l -> !l.trim().isEmpty())
                .collect(Collectors.joining("\n"));

            Map<String, String> headers = Arrays.stream(headersRaw.split("\n"))
                .map(String::trim)
                .filter(l -> l.contains(":"))
                .map(l -> l.split(":", 2))
                .collect(Collectors.toMap(
                    parts -> parts[0].toLowerCase(),
                    parts -> parts[1].trim()
                ));

            int contentLength = Integer.valueOf(headers.get("content-length"));

            char[] bodyChars = new char[contentLength];

            if (contentLength > 0) {
                in.read(bodyChars);
            }

            String body = new String(bodyChars);

            httpRequest = new HttpRequest(
                method,
                path,
                version,
                headers,
                body
            );
        } catch (Exception exception) {
            throw new HttpException(400, "Bad Request", exception.getMessage());
        }

        return httpRequest;
    }

    public static HttpResponse buildHttpResponse(
        int statusCode,
        String statusCodeName,
        Object bodyObject
    ) {
        String body = Marshaller.encode(bodyObject);

        Map<String, String> headers = new HashMap<String, String>();
        
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        String date = formatter.format(now);
        headers.put("Date", date);

        headers.put("Server", "Java");
        
        headers.put("Connection", "closed");
        
        headers.put("Content-Type", "application/json");
        
        String contentLenght = String.valueOf(body.getBytes().length);
        headers.put("Content-Lenght", contentLenght);

        HttpResponse httpResponse = new HttpResponse(
            statusCode,
            statusCodeName,
            headers,
            body
        );

        return httpResponse;
    }

    public static void sendHttpResponse(
        Socket socket,
        HttpResponse httpResponse
    ) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        StringBuilder responseBuilder = new StringBuilder();

        responseBuilder.append(
            "HTTP/1.1 " + String.valueOf(httpResponse.getStatusCode())
            + " " + httpResponse.getStatusCodeName().toUpperCase() + "\r\n"
        );

        for (Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()) {
            responseBuilder.append(
                entry.getKey() + ": " + entry.getValue() + "\r\n"
            );
        }

        responseBuilder.append("\r\n");

        if (httpResponse.getBody() != null) {
            responseBuilder.append(httpResponse.getBody());
        }

        String response = responseBuilder.toString();

        out.write(response);
        out.flush();
    }

    public static void close(
        Socket socket
    ) throws IOException {
        socket.close();
    }
}
