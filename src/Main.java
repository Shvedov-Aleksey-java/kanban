import manager.TaskManager;
import task.Epic;
import task.Progress;
import task.Subtask;
import task.Tasks;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager manager = new TaskManager();
        manager.addTasks(new Tasks("задача 1", "описание 1"));
        manager.addTasks(new Tasks("задача 2", "описание 2"));
        manager.addEpic(manager.getTasks(1).getIdNumber(), new Epic("эпик1", "описание 1"));
        manager.addEpic(manager.getTasks(2).getIdNumber(), new Epic("эпик2", "описание 1"));
        manager.addSubtask(manager.getEpic(3).getIdNumber(), new Subtask("подзадача 1", "описание 1"));
        manager.addSubtask(manager.getEpic(3).getIdNumber(), new Subtask("подзадача 2", "описание 2"));
        manager.addSubtask(manager.getEpic(4).getIdNumber(), new Subtask("подзадача 3", "описание 3"));
        System.out.println(manager.listOfTask());
        manager.setStatus(manager.getSubtask(5), Progress.IN_PROGRESS);
        System.out.println(manager.listOfTask());
        manager.deleteEpic(3);
        System.out.println(manager.listOfTask());

        }
    }


