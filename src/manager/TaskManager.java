package manager;

import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import java.util.HashMap;


public class TaskManager {
    private int generatorId = 1;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public int addTask(Task task) {
        if (task != null){
            int id = ++generatorId;
            task.setStatus(Progress.NEW);
            task.setId(id);
            tasks.put(id, task);
            return id;
        }
        return 0;
    }

    public int addEpic(Epic epic) {
        if (epic != null){
            int id = ++generatorId;
            epic.setStatus(Progress.NEW);
            epic.setId(id);
            epics.put(id, epic);
            return id;
        }
        return 0;
    }

    public Integer addNewSubtask(Subtask subtask) {
        if (subtask != null){
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

    public void deleteTasks(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
       epics.remove(id);
    }

    public void deleteSubtask(int id) {
        int idUpdate = getSubtask(id).getEpicId();
        subtasks.remove(id);
        updateEpicStatus(idUpdate);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    public void clearTask() {
        tasks.clear();
    }

    public void clearEpic(){epics.clear();}

    public void clearSubtask(){subtasks.clear();}

    public String listTask() {
        String strings = "";
        for (int i: tasks.keySet()){
            strings += tasks.get(i).toString() + "\n";
        }
        return strings;
    }

    public String listEpic() {
        String strings = "";
        for (int i: epics.keySet()){
            strings += epics.get(i).toString() + "\n";
        }
        return strings;
    }

    public String listSubtask() {
        String strings = "";
        for (int i: subtasks.keySet()){
            strings += subtasks.get(i).toString() + "\n";
        }
        return strings;
    }

    public String listEpicOfSubtask(Epic epic) {
        String strings = "";
        if (epic != null){
            for (int i: epic.getSubtaskIds()){
                strings += subtasks.get(i).toString() + "\n";
            }
        }
        return strings;
    }

    public void setStatus(Subtask subtask, Progress progress) {
        if (subtask != null && progress != null){
            subtask.setStatus(progress);
        }
        updateEpicStatus(subtask.getEpicId());
    }

    private void updateEpicStatus(int id){
        if (epics.get(id).getSubtaskIds().size() != 0){
            int progressDone = epics.get(id).getSubtaskIds().size();
            int progressNew = epics.get(id).getSubtaskIds().size();
            for (int i: epics.get(id).getSubtaskIds()){
                if (subtasks.get(i).getStatus() == Progress.DONE){
                    progressDone--;
                } else if (subtasks.get(i).getStatus() == Progress.NEW){
                    progressNew--;
                }
            }
            if (progressDone == 0){
                getEpic(id).setStatus(Progress.DONE);
            } else if (progressNew == 0){
                getEpic(id).setStatus(Progress.NEW);
            } else {
                getEpic(id).setStatus(Progress.IN_PROGRESS);
            }
        } else {
            epics.get(id).setStatus(Progress.NEW);
        }
    }


}




