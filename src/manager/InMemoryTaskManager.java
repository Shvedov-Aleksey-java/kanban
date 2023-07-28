package manager;

import manager.exeption.TaskValidationException;
import task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {
    protected int generatorId = 1;

    protected final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();


    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Integer addTask(Task task) {
        if (task != null) {
            int id = ++generatorId;
            task.setStatus(Progress.NEW);
            task.setTaskType(TaskType.TASK);
            task.setId(id);
            tasks.put(id, task);
            add(task);
            return id;
        }
        return null;
    }

    @Override
    public Integer addEpic(Epic epic) {
        if (epic != null) {
            Epic epic1 = epic;
            int id = ++generatorId;
            epic1.setStatus(Progress.NEW);
            epic1.setTaskType(TaskType.EPIC);
            epic1.setId(id);
            epics.put(id, epic1);
            return id;
        }
        return null;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        if (subtask == null) {
            return null;
        }
        final int id = ++generatorId;
        subtask.setId(id);
        subtask.setStatus(Progress.NEW);
        subtask.setTaskType(TaskType.SUBTASK);
        subtasks.put(id, subtask);
        if (subtask.getEpicId() == null) {
            return id;
        }
        final Integer epicId = subtask.getEpicId();
        epics.get(epicId).addSubtaskIds(id);
        updateEpic(id);
        add(subtask);
        return id;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            System.out.println(tasks.values());
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask1 = subtasks.get(id);
        Epic epic = epics.get(subtask1.getEpicId());
        if (epic != null) {
            epic.removeSubtask(id);
            updateEpic(id);
            historyManager.remove(id);
        }
        subtasks.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        savedTask.setName(task.getName());
        savedTask.setDescription(task.getDescription());
        add(task);
        tasks.put(id, savedTask);
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
        epics.put(id, savedEpic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) {
            return;
        }
        int id = subtask.getId();
        Subtask savedSubtask = subtasks.get(id);
        if (savedSubtask == null) return;
        savedSubtask.setName(subtask.getName());
        savedSubtask.setDescription(subtask.getDescription());
        add(subtask);
        subtasks.put(id, savedSubtask);
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
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public void load() {

    }

    private void add(Task task) {
        if (task == null) {
            return;
        }
        final LocalDateTime startTime = task.getStartTime();
        final LocalDateTime endTime = task.getEndTime();
        if (startTime == null && endTime == null) {
            prioritizedTasks.add(task);
            return;
        }
        for (Task t : prioritizedTasks) {
            final LocalDateTime existStart = t.getStartTime();
            final LocalDateTime existEnd = t.getEndTime();
            if (!endTime.isAfter(existStart)) {
                continue;
            }
            if (!existEnd.isAfter(startTime)) {
                continue;
            }
            try {
                throw new TaskValidationException("Задача пересекаются с id=" + t.getId() + " c " + existStart + " по " + existEnd);
            } catch (TaskValidationException e) {
                throw new RuntimeException(e);
            }
        }
        prioritizedTasks.add(task);
    }


    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> tasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null || epic.getSubtaskIds().isEmpty()) {
            return null;
        }
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subtasks.get(id));
        }
        return tasks;
    }

    private void updateEpicTime(int epicID) {
        Epic epic = epics.get(epicID);
        if (epic == null || getEpicSubtasks(epicID) == null) {
            return;
        }
        epic.setDuration(Duration.ZERO);
        List<Subtask> subtasks = getEpicSubtasks(epicID).stream()
                .filter(o1 -> o1.getEndTime() != null)
                .peek(o1 -> epic.setDuration(epic.getDuration().plus(o1.getDuration())))
                .sorted((o1, o2) -> o1.getStartTime().compareTo(o2.getEndTime()))
                .collect(Collectors.toList());
        if (subtasks.isEmpty()) return;

        epic.setStartTime(subtasks.get(0).getStartTime());
        epic.setEndTime(subtasks.get(subtasks.size() - 1).getEndTime());
        epics.put(epicID, epic);
    }

    private void updateEpicStatus(int id) {
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
        epics.put(id, epic);
    }

    protected void updateEpic(int epicId) {
        updateEpicStatus(epicId);
        updateEpicTime(epicId);
    }

}




