package manager;


import task.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    private final CSVTaskFormat format = new CSVTaskFormat();

    public FileBackedTasksManager(File file) {
        this.file = file;
    }


    public void save() {
        List<Task> tasks = super.getTasks();
        List<Epic> epics = super.getEpics();
        List<Subtask> subtasks = super.getSubtasks();
        String file = "";

        for (Task task : tasks) {
            file += format.toString(task);
        }

        for (Epic task : epics) {
            file += format.toString(task);
        }

        for (Subtask task : subtasks) {
            file += format.toString(task);
        }

        String history = CSVTaskFormat.historyToString(super.historyManager);

        if (history.equals("\n")) {
        } else {
            file += history;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file))) {
            writer.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadFromFile(String filePath) {
        try {
            Map<Integer, List<Integer>> listEpicId = new HashMap<>();
            int newId = 0;
            List<String> file = Files.readAllLines(Path.of(filePath));
            for (int s = 0; s < file.size() - 2; s++) {

                Object o = format.fromString(file.get(s));
                if (((Task) o).getId() > newId) {
                    newId = ((Task) o).getId();
                }

                if (o instanceof Task) {
                    super.tasks.put(((Task) o).getId(), (Task) o);
                } else if (o instanceof Epic) {
                    super.epics.put(((Epic) o).getId(), (Epic) o);
                } else if (o instanceof Subtask) {
                    int epicId = ((Subtask) o).getEpicId();
                    int id = ((Subtask) o).getId();
                    if (listEpicId.get(epicId) == null) {
                        listEpicId.put(epicId, new ArrayList<>());
                        listEpicId.get(epicId).add(id);
                    } else {
                        listEpicId.get(epicId).add(id);
                    }
                    super.subtasks.put(((Subtask) o).getId(), (Subtask) o);
                }
            }
            List<Integer> ids = CSVTaskFormat.historyFromString(file.get(file.size() - 1));
            if (!(ids.isEmpty())) {
                for (int i: ids) {
                    getTask(i);
                    getEpic(i);
                    getSubtask(i);
                }
                generatorId = newId;
                for (int i : listEpicId.keySet()) {
                    epics.get(i).setSubtaskIds(listEpicId.get(i));
                }

            }




        } catch (IOException e) {
            try {
                throw new ManagerSaveException();
            } catch (ManagerSaveException ex) {
                ex.printStackTrace();
            }
        }
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
        System.out.println(manager.getHistory());


        TaskManager manager1 = Managers.getDefault();
        manager1.loadFromFile("C:\\Новая папка\\qwe.csv");
        System.out.println(manager1.getHistory());
    }

    public static class CSVTaskFormat {




        public String toString(Task task) {
            String toString = "";
            if (task.getTaskType() == TaskType.SUBTASK) {
                toString += task.getId() + "," + task.getTaskType() + "," + task.getName() + ","
                        + task.getStatus() + "," + task.getDescription() + "," + ((Subtask) task).getEpicId() + "\n";
            } else if (task.getTaskType() == TaskType.TASK || task.getTaskType() == TaskType.EPIC){
                toString += task.getId() + "," + task.getTaskType() + "," + task.getName() + ","
                        + task.getStatus() + "," + task.getDescription() + "," + "\n";
            }
            return toString;
        }

        Task fromString(String value) {
            final String[] split = value.split(",");
            final int id = Integer.parseInt(split[0]);
            final TaskType type = TaskType.valueOf(split[1]);
            final String name = split[2];
            final Progress status = Progress.valueOf(split[3]);
            final String description = split[4];
            if (type == TaskType.TASK) {
                Task task = new Task(name, description);
                task.setId(id);
                task.setStatus(status);
                task.setTaskType(type);
                return task;
            } else if (type == TaskType.EPIC) {
                Epic task = new Epic(name, description);
                task.setId(id);
                task.setStatus(status);
                task.setTaskType(type);
                return task;
            } else if (type == TaskType.SUBTASK) {
                int epicId = Integer.parseInt(split[5]);
                Subtask task = new Subtask(name, description);
                task.setId(id);
                task.setStatus(status);
                task.setTaskType(type);
                task.setEpicId(epicId);
                return task;
            }
            return null;
        }

        static String historyToString(HistoryManager manager) {
            List<Task> history = manager.getHistory();
            String toString = "";
            for (Task task : history) {
                toString += task.getId() + ",";
            }
            return "\n" + toString;
        }

        static List<Integer> historyFromString(String value) {
            final String[] strings = value.split(",");
            final List<Integer> inl = new ArrayList<>();
            for (String s : strings) {
                inl.add(Integer.parseInt(s));
            }
            return inl;
        }


    }


}
