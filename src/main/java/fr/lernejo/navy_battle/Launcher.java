package fr.lernejo.navy_battle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

import static java.lang.Integer.parseInt;

public class Launcher {

    public static void main(String[] args) {
        try {
            // Cast args to int
            int port = parseInt(args[0]);

            //Create HttpServer which is listening on the given port
            HttpServer server= HttpServer.create(new InetSocketAddress(port), 0);

            //Create a new context for the given context and handler
            HttpContext context = server.createContext("/ping");
            context.setHandler(Launcher::CallHandler);

            //Create a new context for /api/game/start and handler
            HttpContext context1 = server.createContext("/api/game/start");
            context1.setHandler(Launcher::CallApiHandler);



            //Create a default executor
            server.setExecutor(Executors.newSingleThreadExecutor());

            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CallHandler(HttpExchange exchange) throws IOException {
        String response = "Hello";
        exchange.sendResponseHeaders(200, response.length());//response code and length
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(response.getBytes());
        }
    }

    private static void CallApiHandler(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String response = "";
        //Response to POST methode
        if (requestMethod.equalsIgnoreCase("POST")) {
            String body = get_body(exchange);
            //check if body are well format
            if (body == "400"   )
            {
                response = "400 (Bad Request)";
                exchange.sendResponseHeaders(202, response.length());//response code and length
            }
            response = "Accepted (202)";
            exchange.sendResponseHeaders(202, response.length());//response code and length
        }
        else {
            response = "404 (Not Found)";
            exchange.sendResponseHeaders(404, response.length());//response code and length
        }
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(response.getBytes());
        }
    }

    private static String get_body(HttpExchange exchange) throws IOException {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            // From now on, the right way of moving from bytes to utf-8 characters:

            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }

            br.close();
            isr.close();

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();

            Message body = gson.fromJson(buf.toString(), Message.class);
            return body.toString();
        } catch (IOException ioe) {
            return "400";
        }

    }
}
