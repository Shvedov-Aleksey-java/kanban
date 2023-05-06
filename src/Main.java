import manager.InMemoryTaskManager;
import manager.Managers;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        InMemoryTaskManager manager = new InMemoryTaskManager();
        int task = manager.addTask(new Task("задача 1", "описание 1"));
        int task1 = manager.addTask(new Task("задача 2", "описание 2"));

        int epic = manager.addEpic(new Epic("эпик1", "описание 1"));
        int epic1 = manager.addEpic(new Epic("эпик2", "описание 1"));

        int subtask = manager.addNewSubtask(new Subtask("подзадача 1", "описание 1").setEpicId(epic));
        int subtask1 = manager.addNewSubtask(new Subtask("подзадача 2", "описание 2").setEpicId(epic));
        int subtask2 = manager.addNewSubtask(new Subtask("подзадача 3", "описание 3").setEpicId(epic1));

        List<Task> tasks = manager.getTasks();
        List<Epic> epics = manager.getEpics();
        List<Subtask> subtasks = manager.getSubtasks();

        for (Task i: tasks){
            System.out.println(i.toString());
        }
        for (Epic i: epics){
            System.out.println(i.toString());
        }
        for (Subtask i: subtasks){
            System.out.println(i.toString());
        }
        manager.deleteEpic(epic);
        for (Task i: tasks){
            System.out.println(i.toString());
        }
        for (Epic i: epics){
            System.out.println(i.toString());
        }
        for (Subtask i: subtasks){
            System.out.println(i.toString());
        }

        }
    }


