package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    Task getTask(int id);

    HashMap<Integer, Epic> epics();

    HashMap<Integer, Subtask> subtasks();

    HashMap<Integer, Task> tasks();

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubtasks();

    InMemoryHistoryManager historyManager();

    List<Task> getHistory();

    Integer addTask(Task task);

    Integer addEpic(Epic epic);

    Integer addNewSubtask(Subtask subtask);

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

    List<Subtask> getEpicSubtasks(int epicId);
}
