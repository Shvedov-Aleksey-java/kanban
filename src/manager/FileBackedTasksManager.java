package manager;


import task.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }


    protected void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(CSVTaskFormat.getHeader());
            writer.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                final Task task = entry.getValue();
                writer.write(CSVTaskFormat.toString(task));
                writer.newLine();
            }
            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                final Task task = entry.getValue();
                writer.write(CSVTaskFormat.toString(task));
                writer.newLine();
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                final Task task = entry.getValue();
                writer.write(CSVTaskFormat.toString(task));
                writer.newLine();
            }
            writer.newLine();
            writer.write(CSVTaskFormat.toString(historyManager));
            writer.newLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Can't save to file: " + file.getName(), e);
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        try {
            final String csv = Files.readString(file.toPath());
            final String[] lines = csv.split(System.lineSeparator());



            int generatorId = 0;
            List<Integer> history = Collections.emptyList();
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    history = CSVTaskFormat.historyFromString(lines[i + 1]);
                    break;
                }
                final Task task = CSVTaskFormat.taskFromString(line);
                final int id = task.getId();
                if (id > generatorId) {
                    generatorId = id;
                }
                taskManager.addAnyTask(task);
            }
            for (Map.Entry<Integer, Subtask> e : taskManager.subtasks.entrySet()) {
                final Subtask subtask = e.getValue();
                final Epic epic = taskManager.epics.get(subtask.getEpicId());
                epic.getSubtaskIds().add(subtask.getId());
            }
            for (Integer taskId : history) {
                taskManager.historyManager.add(taskManager.findTask(taskId));
            }
            taskManager.generatorId = generatorId;
        } catch (IOException e) {
            throw new ManagerSaveException("Can't read form file: " + file.getName(), e);
        }
        return taskManager;
    }

    protected void addAnyTask(Task task) {
        if (task == null) {
            return;
        }
        final int id = task.getId();
        switch (task.getTaskType()) {
            case TASK:
                tasks.put(id, task);
                break;
            case SUBTASK:
                subtasks.put(id, (Subtask) task);
                break;
            case EPIC:
                epics.put(id, (Epic) task);
                break;
        }
    }
    protected Task findTask(Integer id) {
        final Task task = tasks.get(id);
        if (task != null) {
            return task;
        }
        final Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            return subtask;
        }
        return epics.get(id);
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
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

    @Override
    public void updateStatusEpic(int epic, Progress process) {
        super.updateStatusEpic(epic, process);
        save();
    }

    @Override
    public void updateStatusSubtask(int subtask, Progress process) {
        super.updateStatusSubtask(subtask, process);
        save();
    }

    @Override
    public void updateStatusTask(int task, Progress process) {
        super.updateStatusTask(task, process);
        save();
    }

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        int task1 = manager.addTask(new Task("a", "a"));
        int epic1 = manager.addEpic(new Epic("sa", "sa"));
        int epic2 = manager.addEpic(new Epic("as", "as"));
        int subtask = manager.addNewSubtask(new Subtask("s", "s").setEpicId(epic1));
        int subtask1 = manager.addNewSubtask(new Subtask("s1", "s1").setEpicId(epic2));
        manager.getEpic(epic1);
        manager.getEpic(epic2);
        manager.getSubtask(subtask1);
        manager.getSubtask(subtask);
        System.out.println(manager.getTasks().toString());
        System.out.println(manager.getEpics().toString());
        System.out.println(manager.getSubtasks().toString());
        System.out.println(manager.getHistory().toString());

        File file1 = new File("C:\\Новая папка\\qwe.csv");
        TaskManager manager1 = FileBackedTasksManager.loadFromFile(file1);
        System.out.println(manager1.getTasks().toString());
        System.out.println(manager1.getEpics().toString());
        System.out.println(manager1.getSubtasks().toString());
        System.out.println(manager1.getHistory().toString());
    }

    }
