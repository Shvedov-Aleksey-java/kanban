package manager.http;

import manager.exeption.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private int port = 8078;
    private String host = "localhost";
    private String token;

    public KVTaskClient() {
        token = register(String.format("http://%s:%d/register", host, port));
    }

    private String register(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "register")).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Can't do save request, status code: " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Can't do save request", e);
        }
    }

    public void saveState(String key, String value) {
        try {
            String uriStr = String.format("http://%s:%s/save%s?API_TOKEN=%s", host, port, key, token);
            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create(uriStr);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(value)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Can't do save request", e);
        }
    }


    public String loadState(String key) {
        try {
            String uriStr = String.format("http://%s:%d/load%s?API_TOKEN=%s", host, port, key, token);
            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create(uriStr);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Can't do save request, status code: " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Can't do save request", e);
        }
    }
}
