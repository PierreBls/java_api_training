package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class LauncherTest {
    @Test
    void Test_args_error() {
        Launcher launcher = new Launcher();
        String[] args = new String[1];
        args[0] = "-9876";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            launcher.main(args);});
    }

    @Test
    void Test_api() throws IOException, InterruptedException {
        Launcher launcher = new Launcher();
        String[] args = new String[1];
        args[0] = "9876";
        launcher.main(args);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestPost = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876" + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                "    \"id\": \"0c575465-21f6-43c9-8a2d-bc64c3ae6241\",\n" +
                "    \"url\": \"http://localhost:8795\",\n" +
                "    \"message\": \"I will crush you!\"\n" +
                "}"))
            .build();

        HttpResponse response = client.send(requestPost, )

        Assertions.assertEquals(400, requestPost.);
    }

    @Test
    void Test_unknown_methode_api() throws IOException, InterruptedException {
        Launcher launcher = new Launcher();
        String[] args = new String[1];
        args[0] = "9876";
        launcher.main(args);

        URL url = new URL("http://localhost:9876/api/game/start");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();
        Assertions.assertEquals(404, code);
    }

    @Test
    void Test_response_code_simple() throws IOException {
        Launcher launcher = new Launcher();
        String[] args = new String[1];
        args[0] = "9876";
        launcher.main(args);

        URL url = new URL("http://localhost:9876/ping");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();
        Assertions.assertEquals(200, code);
    }
}
