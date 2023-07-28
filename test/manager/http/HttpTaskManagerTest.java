package manager.http;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class HttpTaskManagerTest{

    @Test
    void testGetTask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Task task1 = gson.fromJson(str, Task.class);
            Assertions.assertNotNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetEpic() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson = new Gson();
            Epic newTask = new Epic("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Epic task1 = gson.fromJson(str, Epic.class);
            Assertions.assertNotNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetSubtask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/subtask/");
            Gson gson = new Gson();
            Subtask newTask = new Subtask("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Subtask task1 = gson.fromJson(str, Subtask.class);
            Assertions.assertNotNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testAddTask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Task task1 = gson.fromJson(str, Task.class);
            Assertions.assertNotNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddEpic() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson = new Gson();
            Epic newTask = new Epic("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Epic task1 = gson.fromJson(str, Epic.class);
            Assertions.assertNotNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddNewSubtask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/subtask/");
            Gson gson = new Gson();
            Subtask newTask = new Subtask("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Subtask task1 = gson.fromJson(str, Subtask.class);
            Assertions.assertNotNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDeleteTask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client2 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url1).DELETE().build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

            HttpClient client1 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url2).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Task task1 = gson.fromJson(str, Task.class);

            Assertions.assertNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDeleteEpic() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson = new Gson();
            Epic newTask = new Epic("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client2 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=2");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url1).DELETE().build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

            HttpClient client1 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/epic/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url2).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Epic task1 = gson.fromJson(str, Epic.class);

            Assertions.assertNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDeleteSubtask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/subtask/");
            Gson gson = new Gson();
            Subtask newTask = new Subtask("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client2 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url1).DELETE().build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

            HttpClient client1 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url2).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Subtask task1 = gson.fromJson(str, Subtask.class);

            Assertions.assertNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateTask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Task task1 = gson.fromJson(str, Task.class);


            Assertions.assertEquals(task1.getName(), "a");
            Assertions.assertEquals(task1.getDescription(), "s");

            task1.setName("l");
            task1.setDescription("k");

            String json1 = gson.toJson(task1);
            HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
            HttpClient client2 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/task/");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body1).build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());


            HttpClient client3 = HttpClient.newHttpClient();
            URI url3 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
            HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
            String str3 = response3.body();
            Task task3 = gson.fromJson(str3, Task.class);

            Assertions.assertEquals(task3.getName(), "l");
            Assertions.assertEquals(task3.getDescription(), "k");




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateEpic() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson = new Gson();
            Epic newTask = new Epic("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Epic task1 = gson.fromJson(str, Epic.class);


            Assertions.assertEquals(task1.getName(), "a");
            Assertions.assertEquals(task1.getDescription(), "s");

            task1.setName("l");
            task1.setDescription("k");

            String json1 = gson.toJson(task1);
            HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
            HttpClient client2 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/epic/");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body1).build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());


            HttpClient client3 = HttpClient.newHttpClient();
            URI url3 = URI.create("http://localhost:8080/tasks/epic/?id=2");
            HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
            HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
            String str3 = response3.body();
            Epic task3 = gson.fromJson(str3, Epic.class);

            Assertions.assertEquals(task3.getName(), "l");
            Assertions.assertEquals(task3.getDescription(), "k");




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateSubtask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/subtask/");
            Gson gson = new Gson();
            Subtask newTask = new Subtask("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Subtask task1 = gson.fromJson(str, Subtask.class);


            Assertions.assertEquals(task1.getName(), "a");
            Assertions.assertEquals(task1.getDescription(), "s");

            task1.setName("l");
            task1.setDescription("k");

            String json1 = gson.toJson(task1);
            HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
            HttpClient client2 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/subtask/");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body1).build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());


            HttpClient client3 = HttpClient.newHttpClient();
            URI url3 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
            HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
            HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
            String str3 = response3.body();
            Subtask task3 = gson.fromJson(str3, Subtask.class);

            Assertions.assertEquals(task3.getName(), "l");
            Assertions.assertEquals(task3.getDescription(), "k");




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testClearTasks() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client2 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url1).DELETE().build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

            HttpClient client1 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url2).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Task task1 = gson.fromJson(str, Task.class);

            Assertions.assertNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testClearEpics() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson = new Gson();
            Epic newTask = new Epic("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client2 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/epic/");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url1).DELETE().build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

            HttpClient client1 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/epic/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url2).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Epic task1 = gson.fromJson(str, Epic.class);

            Assertions.assertNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testClearSubtasks() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/subtask/");
            Gson gson = new Gson();
            Subtask newTask = new Subtask("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client2 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/subtask/");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url1).DELETE().build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

            HttpClient client1 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/subtask/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url2).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Subtask task1 = gson.fromJson(str, Subtask.class);

            Assertions.assertNull(task1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetTasks() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Assertions.assertFalse(str.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetEpics() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson = new Gson();
            Epic newTask = new Epic("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/epic/");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Assertions.assertFalse(str.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetSubtasks() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/subtask/");
            Gson gson = new Gson();
            Subtask newTask = new Subtask("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/subtask/");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Assertions.assertFalse(str.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetHistory() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();

            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());


            HttpClient client3 = HttpClient.newHttpClient();
            URI url3 = URI.create("http://localhost:8080/tasks/history/");
            HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
            HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
            String str = response3.body();
            Assertions.assertFalse(str.isEmpty());



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetPrioritizedTasks() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();

            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            Task newTask = new Task("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());


            HttpClient client3 = HttpClient.newHttpClient();
            URI url3 = URI.create("http://localhost:8080/tasks/");
            HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
            HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
            String str = response3.body();
            Assertions.assertFalse(str.isEmpty());



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetEpicSubtasks() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson = new Gson();
            Epic newTask = new Epic("a", "s");
            String json = gson.toJson(newTask);
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HttpClient client1 = HttpClient.newHttpClient();
            URI url1 = URI.create("http://localhost:8080/tasks/subtask/epic/?id=2");
            HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            String str = response1.body();
            Assertions.assertEquals(str, "null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void methodsClassSubtask() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/subtask/");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String str = response.body();
            Assertions.assertFalse(str.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void methodsClassEpic() {
        try {
            new KVServer().start();
            new HttpTaskServer().start();
            URI url = URI.create("http://localhost:8080/tasks/epic/");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String str = response.body();
            Assertions.assertFalse(str.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}