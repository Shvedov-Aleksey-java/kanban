import manager.TaskManager;
import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager manager = new TaskManager();
        int task = manager.addTask(new Task("задача 1", "описание 1"));
        int task1 = manager.addTask(new Task("задача 2", "описание 2"));

        int epic = manager.addEpic(new Epic("эпик1", "описание 1"));
        int epic1 = manager.addEpic(new Epic("эпик2", "описание 1"));

        int subtask = manager.addNewSubtask(new Subtask("подзадача 1", "описание 1").setEpicId(epic));
        int subtask1 = manager.addNewSubtask(new Subtask("подзадача 2", "описание 2").setEpicId(epic));
        int subtask2 = manager.addNewSubtask(new Subtask("подзадача 3", "описание 3").setEpicId(epic1));

        System.out.println(manager.listTask() + manager.listEpic() + manager.listSubtask());
        manager.setStatus(manager.getSubtask(subtask), Progress.DONE);

        System.out.println(manager.listTask() + manager.listEpic() + manager.listSubtask());
        manager.deleteEpic(epic);

        System.out.println(manager.listTask() + manager.listEpic() + manager.listSubtask());

        }
    }


