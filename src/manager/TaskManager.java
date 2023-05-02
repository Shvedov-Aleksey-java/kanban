package manager;

import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
    private int generatorId = 1;

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

    public Task getTasks(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtask(id); // стоит сделать метод removeSubtask в Epic для простого удаления id из списка
        updateEpicStatus(epic.getId());
    }

    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        savedTask.setName(savedTask.getName());
        savedTask.setDescription(savedTask.getDescription());
    }

    public void updateEpic(Epic epic) {
        int id = epic.getId();
        Epic savedEpic = epics.get(id);
        if (savedEpic == null) {
            return;
        }
        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

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

    public void clearTasks() {
        tasks.clear();
    }

    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void clearSubtasks() {
        subtasks.clear();
        for (int i : epics.keySet()) {
            epics.get(i).setStatus(Progress.NEW);
            epics.get(i).getSubtaskIds().clear();
        }
    }

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
            for (int i : epics.get(id).getSubtaskIds()) {
                if (subtasks.get(i).getStatus() == Progress.DONE) {
                    progressDone--;
                } else if (subtasks.get(i).getStatus() == Progress.NEW) {
                    progressNew--;
                }
            }
            if (progressDone == 0) {
                getEpic(id).setStatus(Progress.DONE);
            } else if (progressNew == 0) {
                getEpic(id).setStatus(Progress.NEW);
            } else {
                getEpic(id).setStatus(Progress.IN_PROGRESS);
            }

        } else {
            epics.get(id).setStatus(Progress.NEW);

        }
    }


}




