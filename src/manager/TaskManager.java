package manager;

import task.Epic;
import task.Progress;
import task.Subtask;
import task.Tasks;

import java.util.HashMap;


public class TaskManager {
    private int generatorId = 1;

    private HashMap<Integer, Tasks> tasks = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, Epic>> epic = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, Subtask>> subtask = new HashMap<>();

    public void addTasks(Tasks tasks) {
        if (tasks == null) return;
        if (tasks.getIdNumber() == 0) {
            tasks.setIdNumber(generatorId);
            tasks.setStatus(Progress.NEW);
            this.tasks.put(generatorId, tasks);
            generatorId++;
        } else {
            return;
        }
        statusUpdates();
    }

    public void addEpic(int personalIdTask, Epic epic) {
        if (epic == null) return;
        if (this.tasks.containsKey(personalIdTask) && epic.getIdNumber() == 0) {
            epic.setIdNumber(generatorId);
            epic.setStatus(Progress.NEW);
            epic.setKeyId(personalIdTask);
            this.tasks.get(personalIdTask).getPersonalIds().add(generatorId);
            if (this.epic.containsKey(personalIdTask)) {
                this.epic.get(personalIdTask).put(generatorId, epic);
                generatorId++;
            } else {
                this.epic.put(personalIdTask, new HashMap<>());
                this.epic.get(personalIdTask).put(generatorId, epic);
                generatorId++;
            }
        } else {
            return;
        }
        statusUpdates();
    }

    public void addSubtask(int personalIdEpic, Subtask subtask) {
        if (subtask == null) return;
        for (int i : this.epic.keySet()) {
            if (this.epic.get(i).containsKey(personalIdEpic) && subtask.getIdNumber() == 0) {
                subtask.setIdNumber(generatorId);
                subtask.setStatus(Progress.NEW);
                subtask.setKeyId(personalIdEpic);
                this.epic.get(i).get(personalIdEpic).getPersonalIds().add(generatorId);
                if (this.subtask.containsKey(personalIdEpic)) {
                    this.subtask.get(personalIdEpic).put(generatorId, subtask);
                    generatorId++;
                } else {
                    this.subtask.put(personalIdEpic, new HashMap<>());
                    this.subtask.get(personalIdEpic).put(generatorId, subtask);
                    generatorId++;
                }
            }
        }
        statusUpdates();

    }

    public Tasks getTasks(int id) {
        if (this.tasks.containsKey(id)) {
            return this.tasks.get(id);
        }
        return null;
    }

    public Epic getEpic(int id) {
        for (int i : epic.keySet()) {
            if (epic.get(i).containsKey(id)) return epic.get(i).get(id);
        }
        return null;
    }

    public Subtask getSubtask(int id) {
        for (int i : subtask.keySet()) {
            if (subtask.get(i).containsKey(id)) return subtask.get(i).get(id);
        }
        return null;
    }

    public void deleteTasks(int id) {
        if (tasks.containsKey(id)) {
            if (epic.containsKey(id)) {
                for (int i : epic.get(id).keySet()) {
                    if (subtask.containsKey(i)) subtask.remove(i);
                }
                epic.remove(id);
            }
            tasks.remove(id);
        }
        statusUpdates();
    }

    public void deleteEpic(int id) {
        for (int i : epic.keySet()) {
            if (epic.get(i).containsKey(id)) {
                if (subtask.containsKey(id)) subtask.remove(id);
                epic.get(i).remove(id);
            }
        }
        statusUpdates();
    }

    public void deleteSubtask(int id) {
        for (int i : subtask.keySet()) {
            if (subtask.get(i).containsKey(id)) subtask.get(i).remove(id);
        }
        statusUpdates();
    }

    public void updateTask(Tasks tasks) {
        if (tasks == null) return;
        if (this.tasks.containsKey(tasks.getIdNumber())) {
            this.tasks.put(tasks.getIdNumber(), tasks);
        }
        statusUpdates();
    }

    public void updateEpic(Epic epic) {
        if (epic == null) return;
        if (this.epic.containsKey(epic.getKeyId()) && this.epic.get(epic.getKeyId()).containsKey(epic.getIdNumber())) {
            this.epic.get(epic.getKeyId()).put(epic.getIdNumber(), epic);
        }
        statusUpdates();
    }

    public void updateSubtask(Subtask subtask) {
        if (subtask == null) return;
        if (this.subtask.containsKey(subtask.getKeyId()) && this.subtask.get(subtask.getKeyId()).containsKey(subtask.getIdNumber())) {
            this.subtask.get(subtask.getKeyId()).put(subtask.getIdNumber(), subtask);
        }
        statusUpdates();
    }

    void clear() {
        subtask.clear();
        epic.clear();
        tasks.clear();
    }

    public String listOfTask() {
        String strings = "";
        if (!(tasks.isEmpty())) {
            for (int i : tasks.keySet()) {
                Tasks t = tasks.get(i);
                strings += "имя: " + t.getName() + " описание: " + t.getDescription() + " статус: " + t.getStatus() + " ид: " + t.getIdNumber() + "\n";
                if (epic.containsKey(i)) {
                    for (int j : epic.get(i).keySet()) {
                        Epic e = epic.get(i).get(j);
                        strings += "имя: " + e.getName() + " описание: " + e.getDescription() + " статус: " + e.getStatus() + " ид: " + e.getIdNumber() + "\n";
                        if (subtask.containsKey(j)) {
                            for (int l : subtask.get(j).keySet()) {
                                Subtask s = subtask.get(j).get(l);
                                strings += "имя: " + s.getName() + " описание: " + s.getDescription() + " статус: " + s.getStatus() + " ид: " + s.getIdNumber() + "\n";
                            }
                        }
                    }
                }
            }
        }


        return strings;
    }

    String listOfTask(Object o) {
        String strings = "";
        if (o instanceof Epic) {
            if (subtask.containsKey(((Epic) o).getIdNumber())) {
                for (int i : subtask.get(((Epic) o).getIdNumber()).keySet()) {
                    Subtask s = subtask.get(((Epic) o).getIdNumber()).get(i);
                    strings += "имя: " + s.getName() + " описание: " + s.getDescription() + " статус: " + s.getStatus() + " ид: " + s.getIdNumber() + "\n";
                }
            }
        }
        return strings;
    }

    public void setStatus(Object o, Progress p) {
        if (o instanceof Subtask && p != null) {
            ((Subtask) o).setStatus(p);
            statusUpdates();
        }

    }

    private void statusUpdates() {
        if (!(this.epic.isEmpty())) {
            for (int i : this.epic.keySet()) {
                for (int j : this.epic.get(i).keySet()) {
                    Epic epic = this.epic.get(i).get(j);
                    if (this.subtask.containsKey(j)) {
                        int isNewLengthMapEpic = this.subtask.get(j).size();
                        int isDoneLengthMapEpic = this.subtask.get(j).size();
                        for (int l : this.subtask.get(j).keySet()) {
                            Subtask subtask = this.subtask.get(j).get(l);
                            if (subtask.getStatus() == Progress.DONE) {
                                isDoneLengthMapEpic--;
                            } else if (subtask.getStatus() == Progress.NEW) {
                                isNewLengthMapEpic--;
                            }
                        }

                        if (isDoneLengthMapEpic == 0) {
                            epic.setStatus(Progress.DONE);
                        } else if (isNewLengthMapEpic == 0) {
                            epic.setStatus(Progress.NEW);
                        } else {
                            epic.setStatus(Progress.IN_PROGRESS);
                        }
                    }


                }
            }
        }
    }


}




