package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.io.Serializable;
import java.util.List;

public interface TaskManager extends Serializable {

    Task getTask(int id);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

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
