import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        //TaskManager manager = Managers.getDefault();

        TaskManager managers = Managers.getDefault();
        int epic = managers.addEpic(new Epic("a", "b"));
        int sub = managers.addNewSubtask(new Subtask("a", "s", epic));
        int sub1 = managers.addNewSubtask(new Subtask("a", "s", epic));
        managers.updateStatusSubtask(sub, Progress.DONE);
        managers.updateStatusSubtask(sub1, Progress.DONE);
        System.out.println(managers.getEpic(epic).getStatus());


/*
        int epic = manager.addEpic(new Epic("1", "1"));
        int epic1 = manager.addEpic(new Epic("1", "2"));
        int subtask = manager.addNewSubtask(new Subtask("2", "1").setEpicId(epic1));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2").setEpicId(epic1));
        manager.getEpic(epic);
        System.out.println(manager.getHistory());
        manager.getEpic(epic1);
        System.out.println(manager.getHistory());
        manager.getSubtask(subtask);
        System.out.println(manager.getHistory());
        manager.getSubtask(subtask1);
        System.out.println(manager.getHistory());
        manager.getEpic(epic1);
        System.out.println(manager.getHistory());
        manager.getSubtask(subtask1);
        System.out.println(manager.getHistory());
        manager.deleteSubtask(subtask1);
        System.out.println(manager.getHistory());
        manager.deleteSubtask(subtask);
        manager.deleteEpic(epic1);
        System.out.println(manager.getHistory());
        String str = "task.Epic";
        Task task = new Task("l", "k");
        String[] r = str.split("\\.");
        System.out.println(manager.getEpic(epic).getStatus());

 */

        }
    }


