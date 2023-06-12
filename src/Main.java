import manager.Managers;
import manager.TaskManager;
import task.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager manager = Managers.getDefault();

        int task = manager.addTask(new Task("1", "1"));
        int task1 = manager.addTask(new Task("1", "2"));

        Task get1 = manager.getTask(task);
        Task get2 = manager.getTask(task1);

        List<Task> h = manager.getHistory();
        System.out.println(h);
        manager.deleteTask(task);
        List<Task> h1 = manager.getHistory();
        System.out.println(h1);
        }
    }


