package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    Integer addTask(Task task);

    Integer addEpic(Epic epic);

    Integer addNewSubtask(Subtask subtask);

    Task getTasks(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic) ;

    void updateSubtask(Subtask subtask);

    void clearTasks();

    void clearEpics();

    void clearSubtasks();

    ArrayList<Subtask> getEpicSubtasks(int epicId);
}
