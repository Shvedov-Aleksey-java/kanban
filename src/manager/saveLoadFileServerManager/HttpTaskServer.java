package manager.saveLoadFileServerManager;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpTaskServer {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;
    private TaskManager manager = Managers.getDefault();
    private final int PORT = 8080;
    private final HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", this::postsTasks);
        httpServer.createContext("/tasks/subtask", this::methodsClassSubtask);
        httpServer.createContext("/tasks/epic", this::methodsClassEpic);
        gson = new Gson();
    }

    public void start() {
        httpServer.start();
        manager.load();
    }

    public void stop() {
        httpServer.stop(0);
    }

    public void postsTasks(HttpExchange exchange) throws IOException {

        Endpoint endpoint = getEndpoint(exchange);
        String[] url = exchange.getRequestURI().getPath().split("/");
        String idString = exchange.getRequestURI().getQuery();

        Integer id = null;

        try {
            if (idString != null) {
                id = Integer.parseInt(idString.split("=")[1]);
            }
        } catch (NullPointerException e) {
            writeResponse(exchange, "Некорректный идентификатор поста", 400);
        }
        switch (endpoint) {
            case GET:
                if (url.length == 4 && url[2].equals("subtask") && url[3].equals("epic") && idString != null) {
                    try {
                        writeResponse(exchange, gson.toJson(manager.getEpicSubtasks(id)), 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url.length == 2 && url[1].equals("tasks")) {
                    writeResponse(exchange, gson.toJson(manager.getPrioritizedTasks()), 200);
                } else  if (url[2].equals("task") && idString != null) {
                    try {
                        String json = gson.toJson(manager.getTask(id));
                        writeResponse(exchange, json, 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url[2].equals("task")) {
                    String json = gson.toJson(manager.getTasks());
                    writeResponse(exchange, json, 200);
                } else if (url[2].equals("epic") && idString == null) {
                    writeResponse(exchange, gson.toJson(manager.getEpics()), 200);
                } else if (url[2].equals("epic")) {
                    try {
                        writeResponse(exchange, gson.toJson(manager.getEpic(id)), 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url[2].equals("subtask") && idString == null) {
                    writeResponse(exchange, gson.toJson(manager.getSubtasks()), 200);
                } else if (url[2].equals("subtask")) {
                    try {

                        writeResponse(exchange, gson.toJson(manager.getSubtask(id)), 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else  if (url[2].equals("history")) {
                    writeResponse(exchange, gson.toJson(manager.getHistory()), 200);
                }
                break;
            case POST:
                if (url[2].equals("task")) {
                    InputStream stream = exchange.getRequestBody();
                    String body = new String(stream.readAllBytes(), DEFAULT_CHARSET);
                    Task task = gson.fromJson(body, Task.class);
                    int taskId = task.getId();
                    if (taskId == 0) {
                        manager.addTask(task);
                        writeResponse(exchange, "задача добавленна", 200);
                    } else {
                        manager.updateTask(task);
                        writeResponse(exchange, "задача обновлена", 200);
                    }
                    stream.close();
                } else if (url[2].equals("epic")) {
                    InputStream stream = exchange.getRequestBody();
                    String body = new String(stream.readAllBytes(), DEFAULT_CHARSET);
                    Epic task = gson.fromJson(body, Epic.class);
                    if (manager.getEpic(task.getId()) == null) {
                        manager.addEpic(task);
                        writeResponse(exchange, "задача добавленна", 200);
                    } else {
                        manager.updateEpic(task);
                        writeResponse(exchange, "задача добавленна", 200);
                    }
                } else if (url[2].equals("subtask")) {
                    InputStream stream = exchange.getRequestBody();
                    String body = new String(stream.readAllBytes(), DEFAULT_CHARSET);
                    Subtask task = gson.fromJson(body, Subtask.class);
                    if (manager.getSubtask(task.getId()) == null) {
                        manager.addNewSubtask(task);
                        writeResponse(exchange, "задача добавленна", 200);
                    } else {
                        manager.updateSubtask(task);
                        writeResponse(exchange, "задача добавленна", 200);
                    }
                }
                break;
            case DELETE:
                if (url[2].equals("task") && idString == null) {
                    manager.clearTasks();
                    writeResponse(exchange, "Задачи удаленны", 201);
                } else if (url[2].equals("task")) {
                    try {
                        manager.deleteTask(id);
                        writeResponse(exchange, "Задача удаленна", 201);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url[2].equals("epic") && idString == null) {
                    manager.clearEpics();
                    writeResponse(exchange, "Задачи удаленны", 201);
                } else if (url[2].equals("epic")) {
                    try {
                        manager.deleteEpic(id);
                        writeResponse(exchange, "Задача удаленна", 201);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url[2].equals("subtask") && idString == null) {
                    manager.clearSubtasks();
                    writeResponse(exchange, "Задачи удаленны", 201);
                } else if (url[2].equals("subtask")) {
                    try {
                        manager.deleteSubtask(id);
                        writeResponse(exchange, "Задача удаленна", 201);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                }
                break;
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }


    }

    public void methodsClassSubtask(HttpExchange exchange) throws IOException {
        Class clazz = Subtask.class;
        writeResponse(exchange, gson.toJson(Arrays.toString(clazz.getDeclaredMethods())), 200);
    }

    public void methodsClassEpic(HttpExchange exchange) throws IOException {
        Class clazz = Epic.class;
        writeResponse(exchange, gson.toJson(Arrays.toString(clazz.getDeclaredMethods())), 200);
    }


    private Endpoint getEndpoint(HttpExchange exchange) {
        switch (exchange.getRequestMethod()) {
            case "GET":
                return Endpoint.GET;
            case "POST":
                return Endpoint.POST;
            case "DELETE":
                return Endpoint.DELETE;
            default:
                return Endpoint.UNKNOWN;
        }
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    enum Endpoint {GET, POST, DELETE, UNKNOWN}


}