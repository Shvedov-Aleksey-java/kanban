package manager.http;

import com.google.gson.Gson;
import manager.saveLaodFileManager.FileBackedTasksManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;
import java.util.*;


public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson;
    private final KVTaskClient taskClient;

    public HttpTaskManager(File file) {
        super(file);
        gson = new Gson();
        taskClient = new KVTaskClient();
    }

    @Override
    protected void save() {
        MapTask mapTask = new MapTask();
        mapTask.setTask(tasks);
        MapEpic mapEpic = new MapEpic();
        mapEpic.setEpic(epics);
        MapSubtask mapSubtask = new MapSubtask();
        mapSubtask.setSubtask(subtasks);
        ListHistory history = new ListHistory();
        history.setHistory(historyManager.getHistory());
        taskClient.saveState("task", gson.toJson(mapTask));
        taskClient.saveState("epic", gson.toJson(mapEpic));
        taskClient.saveState("subtask", gson.toJson(mapSubtask));
        taskClient.saveState("history", gson.toJson(history));

    }
    @Override
    public void load() {
        MapTask mapTask = gson.fromJson(taskClient.loadState("task"), MapTask.class);
        if (mapTask != null) {
            tasks = mapTask.getTask();
        }

        MapEpic mapEpic = gson.fromJson(taskClient.loadState("epic"), MapEpic.class);
        if (mapEpic != null) {
            epics = mapEpic.getEpic();
        }
        MapSubtask mapSubtask = gson.fromJson(taskClient.loadState("subtask"), MapSubtask.class);
        if (mapSubtask != null) {
            subtasks = mapSubtask.getSubtask();
        }
        ListHistory history = gson.fromJson(taskClient.loadState("history"), ListHistory.class);
        if (history != null && !history.getHistory().isEmpty()) {
            for (Task task: history.getHistory()) {
                historyManager.add(task);
            }
        }

    }

    private class MapTask {
        Map<Integer, Task> task = new HashMap<>();

        @Override
        public String toString() {
            return "MapTask{" +
                    "task=" + task +
                    '}';
        }

        public Map<Integer, Task> getTask() {
            return task;
        }

        public void setTask(Map<Integer, Task> task) {
            this.task = task;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapTask mapTask = (MapTask) o;
            return Objects.equals(task, mapTask.task);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task);
        }
    }

    private class MapEpic{
        Map<Integer, Epic> epic = new HashMap<>();

        @Override
        public String toString() {
            return "MapEpic{" +
                    "epic=" + epic +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapEpic mapEpic = (MapEpic) o;
            return Objects.equals(epic, mapEpic.epic);
        }

        @Override
        public int hashCode() {
            return Objects.hash(epic);
        }

        public Map<Integer, Epic> getEpic() {
            return epic;
        }

        public void setEpic(Map<Integer, Epic> epic) {
            this.epic = epic;
        }
    }

    private class MapSubtask{
        Map<Integer, Subtask> subtask = new HashMap<>();

        @Override
        public String toString() {
            return "MapSubtask{" +
                    "subtask=" + subtask +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapSubtask that = (MapSubtask) o;
            return Objects.equals(subtask, that.subtask);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subtask);
        }

        public Map<Integer, Subtask> getSubtask() {
            return subtask;
        }

        public void setSubtask(Map<Integer, Subtask> subtask) {
            this.subtask = subtask;
        }
    }

    private class ListHistory{
        List<Task> history = new ArrayList<>();

        @Override
        public String toString() {
            return "ListHistory{" +
                    "history=" + history +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListHistory that = (ListHistory) o;
            return Objects.equals(history, that.history);
        }

        @Override
        public int hashCode() {
            return Objects.hash(history);
        }

        public List<Task> getHistory() {
            return history;
        }

        public void setHistory(List<Task> history) {
            this.history = history;
        }
    }
}
