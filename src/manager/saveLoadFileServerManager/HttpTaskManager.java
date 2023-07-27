package manager.saveLoadFileServerManager;

import com.google.gson.Gson;
import manager.saveLaodFileManager.FileBackedTasksManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpTaskManager extends FileBackedTasksManager {
    Gson gson;
    KVTaskClient taskClient;

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
        taskClient.saveState("task", gson.toJson(mapTask));
        taskClient.saveState("epic", gson.toJson(mapEpic));
        taskClient.saveState("subtask", gson.toJson(mapSubtask));
    }

    protected void load() {

        super.tasks = gson.fromJson(taskClient.loadState("task"), MapTask.class).getTask();
        super.epics = gson.fromJson(taskClient.loadState("epic"), MapEpic.class).getEpic();
        super.subtasks = gson.fromJson(taskClient.loadState("subtask"), MapSubtask.class).getSubtask();

    }

    @Override
    public Task getTask(int id) {
        load();
        Task task = super.getTask(id);
        return task;
    }

    @Override
    public Integer addTask(Task task) {
        int id = super.addTask(task);
        save();
        return id;
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
}
