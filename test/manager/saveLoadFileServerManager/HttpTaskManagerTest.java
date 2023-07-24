package manager.saveLoadFileServerManager;

import com.google.gson.Gson;
import manager.saveLaodFileManager.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends FileBackedTasksManager {
    @Test
    void testSave() {
    }

    @Test
    void testGetTask() {
    }

    @Test
    void testGetEpic() {
    }

    @Test
    void testGetSubtask() {
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
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void testAddEpic() {
    }

    @Test
    void testAddNewSubtask() {
    }

    @Test
    void testDeleteTask() {
    }

    @Test
    void testDeleteEpic() {
    }

    @Test
    void testDeleteSubtask() {
    }

    @Test
    void testUpdateTask() {
    }

    @Test
    void testUpdateEpic() {
    }

    @Test
    void testUpdateSubtask() {
    }

    @Test
    void testClearTasks() {
    }

    @Test
    void testClearEpics() {
    }

    @Test
    void testClearSubtasks() {
    }

    @Test
    void testMain() {
    }

    @Test
    void testGetTasks() {
    }

    @Test
    void testGetEpics() {
    }

    @Test
    void testGetSubtasks() {
    }

    @Test
    void testGetHistory() {
    }

    @Test
    void testAddTask1() {
    }

    @Test
    void testAddEpic1() {
    }

    @Test
    void testAddNewSubtask1() {
    }

    @Test
    void testGetTask1() {
    }

    @Test
    void testGetEpic1() {
    }

    @Test
    void testGetSubtask1() {
    }

    @Test
    void testDeleteTask1() {
    }

    @Test
    void testDeleteEpic1() {
    }

    @Test
    void testDeleteSubtask1() {
    }

    @Test
    void testUpdateTask1() {
    }

    @Test
    void testUpdateEpic1() {
    }

    @Test
    void testUpdateSubtask1() {
    }

    @Test
    void testClearTasks1() {
    }

    @Test
    void testClearEpics1() {
    }

    @Test
    void testClearSubtasks1() {
    }

    @Test
    void testGetPrioritizedTasks() {
    }

    @Test
    void testGetEpicSubtasks() {
    }

    @Test
    void testUpdateEpic2() {
    }

    @Test
    void load() {
    }
}