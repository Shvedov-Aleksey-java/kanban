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

public class HttpTaskServer {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson;
    private final TaskManager manager = Managers.getDefault();
    private final int PORT = 8080;
    private final HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", this::postsTasks);
        gson = new Gson();
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }

    public void postsTasks(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange);
        String[] url = exchange.getRequestURI().getPath().split("/");
        switch (endpoint) {
            case GET:
                if (url.length == 3 && url[2].equals("task")) {
                    writeResponse(exchange, gson.toJson(manager.getTasks()), 200);
                } else if (url.length == 4 && url[3].split("=")[0].equals("?id") && url[2].equals("task")) {
                    try {
                        int id = Integer.parseInt(url[3].split("=")[1]);
                        writeResponse(exchange, gson.toJson(manager.getTask(id)), 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url.length == 3 && url[2].equals("epic")) {
                    writeResponse(exchange, gson.toJson(manager.getEpics()), 200);
                } else if (url.length == 4 && url[3].split("=")[0].equals("?id") && url[2].equals("epic")) {
                    try {
                        int id = Integer.parseInt(url[3].split("=")[1]);
                        writeResponse(exchange, gson.toJson(manager.getEpic(id)), 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url.length == 3 && url[2].equals("subtask")) {
                    writeResponse(exchange, gson.toJson(manager.getSubtasks()), 200);
                } else if (url.length == 4 && url[3].split("=")[0].equals("?id") && url[2].equals("subtask")) {
                    try {
                        int id = Integer.parseInt(url[3].split("=")[1]);
                        writeResponse(exchange, gson.toJson(manager.getSubtask(id)), 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url.length == 2 && url[1].equals("tasks")) {
                    writeResponse(exchange, gson.toJson(manager.getPrioritizedTasks()), 200);
                } else if (url.length == 5 && url[2].equals("subtask") && url[3].equals("epic") && url[4].split("=")[0].equals("?id")) {
                    try {
                        int id = Integer.parseInt(url[4].split("=")[1]);
                        writeResponse(exchange, gson.toJson(manager.getEpicSubtasks(id)), 200);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url.length == 3 && url[2].equals("history")) {
                    writeResponse(exchange, gson.toJson(manager.getHistory()), 200);
                }
            case POST:
                if (url.length == 3 && url[2].equals("task")) {
                    InputStream stream = exchange.getRequestBody();
                    String body = new String(stream.readAllBytes(), DEFAULT_CHARSET);
                    Task task = gson.fromJson(body, Task.class);
                    int taskId = task.getId();
                    if (taskId == 0) {
                        manager.addTask(task);
                    } else {
                        manager.updateTask(task);
                    }
                    stream.close();
                } else if (url.length == 3 && url[2].equals("epic")) {
                    InputStream stream = exchange.getRequestBody();
                    String body = new String(stream.readAllBytes(), DEFAULT_CHARSET);
                    Epic task = gson.fromJson(body, Epic.class);
                    if (manager.getEpic(task.getId()) == null) {
                        manager.addEpic(task);
                    } else {
                        manager.updateEpic(task);
                    }
                } else if (url.length == 3 && url[2].equals("subtask")) {
                    InputStream stream = exchange.getRequestBody();
                    String body = new String(stream.readAllBytes(), DEFAULT_CHARSET);
                    Subtask task = gson.fromJson(body, Subtask.class);
                    if (manager.getSubtask(task.getId()) == null) {
                        manager.addNewSubtask(task);
                    } else {
                        manager.updateSubtask(task);
                    }
                    stream.close();
                }
            case DELETE:
                if (url.length == 3 && url[2].equals("task")) {
                    manager.clearTasks();
                    writeResponse(exchange, "Задачи удаленны", 201);
                } else if (url.length == 4 && url[3].split("=")[0].equals("?id") && url[2].equals("task")) {
                    try {
                        int id = Integer.parseInt(url[3].split("=")[1]);
                        manager.deleteTask(id);
                        writeResponse(exchange, "Задача удаленна", 201);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url.length == 3 && url[2].equals("epic")) {
                    manager.clearEpics();
                    writeResponse(exchange, "Задачи удаленны", 201);
                } else if (url.length == 4 && url[3].split("=")[0].equals("?id") && url[2].equals("epic")) {
                    try {
                        int id = Integer.parseInt(url[3].split("=")[1]);
                        manager.deleteEpic(id);
                        writeResponse(exchange, "Задача удаленна", 201);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                } else if (url.length == 3 && url[2].equals("subtask")) {
                    manager.clearSubtasks();
                    writeResponse(exchange, "Задачи удаленны", 201);
                } else if (url.length == 4 && url[3].split("=")[0].equals("?id") && url[2].equals("subtask")) {
                    try {
                        int id = Integer.parseInt(url[3].split("=")[1]);
                        manager.deleteSubtask(id);
                        writeResponse(exchange, "Задача удаленна", 201);
                    } catch (NumberFormatException e) {
                        writeResponse(exchange, "Некорректный идентификатор поста", 400);
                    }
                }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }


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