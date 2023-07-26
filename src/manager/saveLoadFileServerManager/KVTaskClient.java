package manager.saveLoadFileServerManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private int port = 8078;
    private String host = "localhost";
    private String token;

    public KVTaskClient() {
        try {
            String uriStr = String.format("http://%s:%d/register", host, port);
            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create(uriStr);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            token = response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveState(String key, String value) {
        try {
            String uriStr = String.format("http://%s:%s/save%s?API_TOKEN=%s", host, port, key, token);
            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create(uriStr);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(value))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public String loadState(String key) {
        try {
            String uriStr = String.format("http://%s:%d/save%s?API_TOKEN=%s", host, port, key, token);
            HttpClient client = HttpClient.newHttpClient();
            URI uri = new URI(uriStr);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
