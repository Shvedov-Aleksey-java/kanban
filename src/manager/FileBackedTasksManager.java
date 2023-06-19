package manager;


import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager, Serializable {



    public void save() {
        List<Task> tasks = super.getTasks();
        List<Epic> epics = super.getEpics();
        List<Subtask> subtasks = super.getSubtasks();
        String file = "";

        for (Task task : tasks) {
            file += task.getId() + "," + task.getClass().getTypeName() + "," + task.getName() + ","
                    + task.getStatus() + "," + task.getDescription() + "\n";
        }

        for (Epic task : epics) {
            file += task.getId() + "," + task.getClass().getTypeName() + "," + task.getName() + ","
                    + task.getStatus() + "," + task.getDescription() + "\n";
        }

        for (Subtask task : subtasks) {
            file += task.getId() + "," + task.getClass().getTypeName() + "," + task.getName() + ","
                    + task.getStatus() + "," + task.getDescription() + ((Subtask) task).getEpicId() + "\n";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Новая папка\\qwe.csv"))) {
            writer.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadFromFile(String filePath) {
        Map<Integer, Task> tasks = new HashMap<>();
        Map<Integer, Epic> epics = new HashMap<>();
        Map<Integer, Subtask> subtasks = new HashMap<>();

        try {
            List<String> file = Files.readAllLines(Path.of(filePath));
            Map<Integer, List<Integer>> listEpicId = new HashMap<>();
            for (String s : file) {
                String[] strings = s.split(",");
                String[] type = strings[1].split("\\.");
                String classType = type[1];
                String p = strings[3];
                String name = strings[2];
                String description = strings[4];
                Progress progress;
                int id = Integer.parseInt(strings[0]);
                if (p.equals("NEW")) {
                    progress = Progress.NEW;
                } else if (p.equals("DONE")) {
                    progress = Progress.DONE;
                } else {
                    progress = Progress.IN_PROGRESS;
                }
                if (strings.length == 5) {
                    if (classType.equals("Task")) {
                        Task task = new Task(name, description);
                        task.setStatus(progress);
                        task.setId(id);
                        tasks.put(id, task);
                    } else {
                        Epic epic = new Epic(name, description);
                        epic.setStatus(progress);
                        epic.setId(id);
                        epics.put(id, epic);
                    }
                } else {
                    int epicId = Integer.parseInt(strings[5]);
                    Subtask subtask = new Subtask(name, description);
                    subtask.setStatus(progress);
                    subtask.setDescription(description);
                    subtask.setEpicId(epicId);
                    subtask.setId(id);
                    if (listEpicId.get(epicId) == null) {
                        listEpicId.put(epicId, new ArrayList<>());
                        listEpicId.get(epicId).add(id);
                    } else {
                        listEpicId.get(epicId).add(id);
                    }
                    subtasks.put(id, subtask);
                }
            }
            for (int i : listEpicId.keySet()) {
                epics.get(i).setSubtaskIds(listEpicId.get(i));
            }
            super.setTasks(tasks);
            super.setEpics(epics);
            super.setSubtasks(subtasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Integer addTask(Task task) {
        int id = super.addTask(task);
        save();
        return id;

    }

    @Override
    public Integer addEpic(Epic epic) {
        int id = super.addEpic(epic);
        save();
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        int id = super.addNewSubtask(subtask);
        save();
        return id;
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }

    public static void main(String[] args) {
        FileBackedTasksManager manager = new FileBackedTasksManager();
        manager.addTask(new Task("1", "1"));
        FileBackedTasksManager manager1 = new FileBackedTasksManager();
        manager1.loadFromFile("C:\\Новая папка\\qwe.csv");
        System.out.println(manager1.getTask(2).toString());
    }


}
