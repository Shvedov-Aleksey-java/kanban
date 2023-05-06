package manager;

import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    private int generatorId = 1;

    private ArrayList<Task> tenLastViewedIssues = new ArrayList<>();

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getHistory() {
        return tenLastViewedIssues;
    }

    @Override
    public Integer addTask(Task task) {
        if (task != null) {
            int id = ++generatorId;
            task.setStatus(Progress.NEW);
            task.setId(id);
            tasks.put(id, task);
            return id;
        }
        return null;
    }

    @Override
    public Integer addEpic(Epic epic) {
        if (epic != null) {
            int id = ++generatorId;
            epic.setStatus(Progress.NEW);
            epic.setId(id);
            epics.put(id, epic);
            return id;
        }
        return null;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        if (subtask != null) {
            final int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            if (epic == null) {
                return null;
            }
            final int id = ++generatorId;
            subtask.setId(id);
            subtask.setStatus(Progress.NEW);
            subtasks.put(id, subtask);
            epic.getSubtaskIds().add(id);
            return id;
        }
        return null;
    }

    @Override
    public Task getTasks(int id) {
        if (tasks.containsKey(id)) {
            if (tenLastViewedIssues.size() == 10) tenLastViewedIssues.clear();
            tenLastViewedIssues.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            if (tenLastViewedIssues.size() == 10) tenLastViewedIssues.clear();
            tenLastViewedIssues.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.containsKey(id)) {
            if (tenLastViewedIssues.size() == 10) tenLastViewedIssues.clear();
            tenLastViewedIssues.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        epics.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtask(id); // стоит сделать метод removeSubtask в Epic для простого удаления id из списка
        updateEpicStatus(epic.getId());
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        savedTask.setName(savedTask.getName());
        savedTask.setDescription(savedTask.getDescription());
    }

    @Override
    public void updateEpic(Epic epic) {
        int id = epic.getId();
        Epic savedEpic = epics.get(id);
        if (savedEpic == null) {
            return;
        }
        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask savedSubtask = subtasks.get(id);
        if (savedSubtask == null) {
            return;
        }
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subtasks.put(id, savedSubtask);
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }

    @Override
    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearSubtasks() {
        subtasks.clear();
        for (int i : epics.keySet()) {
            epics.get(i).setStatus(Progress.NEW);
            epics.get(i).getSubtaskIds().clear();
        }
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> tasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subtasks.get(id));
        }
        return tasks;
    }

    public void updateEpicStatus(int id) {
        Epic epic = epics.get(id);
        if (epic == null) return;
        int epicSubtasksSize = epic.getSubtaskIds().size();
        if (epicSubtasksSize != 0) {
            int progressDone = epicSubtasksSize;
            int progressNew = epicSubtasksSize;
            for (int i : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(i);
                if (subtask.getStatus() == Progress.DONE) {
                    progressDone--;
                } else if (subtask.getStatus() == Progress.NEW) {
                    progressNew--;
                }
            }
            if (progressDone == 0) {
                epic.setStatus(Progress.DONE);
            } else if (progressNew == 0) {
                epic.setStatus(Progress.NEW);
            } else {
                epic.setStatus(Progress.IN_PROGRESS);
            }

        } else {
            epic.setStatus(Progress.NEW);

        }
    }


}



