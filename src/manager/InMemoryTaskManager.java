package manager;

import task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {
    protected int generatorId = 1;

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
        if (subtask == null) return null;
        if (equalTimeOutSubtask(subtask)) {
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
            updateEpicStatus(epicId);
            return id;
        }
        return null;
    }


    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
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
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtask(id); // стоит сделать метод removeSubtask в Epic для простого удаления id из списка
        updateEpicStatus(epic.getId());
        historyManager.remove(id);
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
        if (savedSubtask == null)return;
        savedSubtask.setName(subtask.getName());
        savedSubtask.setDescription(subtask.getDescription());
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
    public void updateStatusEpic(int epic, Progress process) {
        Epic epic1 = epics.get(epic);
        if (epic1 == null)return;
        epic1.setStatus(process);
        epics.put(epic, epic1);
    }

    @Override
    public void updateStatusTask(int task, Progress process) {
        Task task1 = tasks.get(task);
        if (task1 == null) return;
        task1.setStatus(process);
        tasks.put(task, task1);
    }

    @Override
    public void updateStatusSubtask(int subtask, Progress process) {
        Subtask subtask1 = subtasks.get(subtask);
        if (subtask1 == null)return;
        subtask1.setStatus(process);
        subtasks.put(subtask, subtask1);
        updateEpicStatus(subtask1.getEpicId());
    }

    public Set<Task> getPrioritizedTasks() {
        Set<Task> tasks = new TreeSet<>((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
        tasks.addAll(getEpics());
        tasks.addAll(getSubtasks());
        return tasks;
    }

    private Boolean equalTimeOutSubtask(Subtask subtask) {
        if (subtask == null) return null;
        if (subtask.getStartTime() == null && subtask.getEndTime() == null) return true;
        LocalDateTime startTime = subtask.getStartTime();
        LocalDateTime endTime = subtask.getEndTime();
        if (getSubtasks().isEmpty()) return true;
        List<Subtask> tasksTime = getSubtasks().stream()
                .filter(o1 -> o1.getEndTime() != null)
                .sorted((o1, o2) -> o1.getStartTime().compareTo(o2.getEndTime()))
                .collect(Collectors.toList());
        Boolean newSubtask = false;
        for (int i = 0; i < tasksTime.size(); i++) {
            Subtask task1 = tasksTime.get(i);
            Subtask task2 = null;
            if (tasksTime.size() > 1 && i < tasksTime.size() - 1) {
                task2 = tasksTime.get(i + 1);
            }
            if (i == 0 && endTime.isBefore(task1.getStartTime())) {
                newSubtask = true;
            } else if (i == tasksTime.size() - 1 && task1.getEndTime().isBefore(startTime)) {
                newSubtask = true;
            } else if (i != 0 && i != tasksTime.size() - 1 && task1.getEndTime().isBefore(startTime)
                    && endTime.isBefore(task2.getStartTime())) {
                newSubtask = true;
            }
        }
        if (!(newSubtask)) {
            throw new ManagerSaveException("время уже занято");
        }
        return newSubtask;
    }

    @Override
    public void addEpicSubtaskIds(int epicId, int subtaskId) {
        Integer id = subtaskId;
        if (!(epics.get(epicId).getSubtaskIds().contains(id))) {
            epics.get(epicId).getSubtaskIds().add(subtaskId);
        }
        subtasks.get(subtaskId).setEpicId(epicId);
        updateEpicStatus(epicId);
        updateEpicTime(epicId);
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
        Epic epicOne = epics.get(epicID);
        if (epicOne == null || getEpicSubtasks(epicID) == null)return;
        epicOne.setDuration(Duration.ZERO);
        List<Subtask> subtasks = getEpicSubtasks(epicID).stream()
                .filter(o1 -> o1.getEndTime() != null)
                .peek(o1 -> epicOne.setDuration(epicOne.getDuration().plus(o1.getDuration())))
                .sorted((o1, o2) -> o1.getStartTime().compareTo(o2.getEndTime()))
                .collect(Collectors.toList());
        if (subtasks.isEmpty())return;

        epicOne.setStartTime(subtasks.get(0).getStartTime());
        epicOne.setEndTime(subtasks.get(subtasks.size() - 1).getEndTime());
        epics.put(epicID, epicOne);
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

}




