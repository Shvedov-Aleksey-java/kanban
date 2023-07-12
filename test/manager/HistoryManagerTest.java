package test;

import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.*;

class HistoryManagerTest<T extends HistoryManager> {

    protected T historyManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    @Test
    void addDuplicate() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "a", "s", Progress.NEW, TaskType.TASK);
        Task task2 = new Task(0, "b", "c", Progress.NEW, TaskType.TASK);
        manager.add(task);
        manager.add(task2);
        Task i = manager.getHistory().get(0);
        int size = manager.getHistory().size();
        Assertions.assertEquals(i, task2);
        Assertions.assertEquals(size, 1);


    }
    @Test
    void removeThe() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "a", "s", Progress.NEW, TaskType.TASK);
        Task task2 = new Task(1, "b", "c", Progress.NEW, TaskType.TASK);
        Task task3 = new Task(2, "v", "k", Progress.NEW, TaskType.TASK);
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        manager.remove(0);
        int size = manager.getHistory().size();
        Task gTask1 = manager.getHistory().get(0);
        Task gTask2 = manager.getHistory().get(1);
        Assertions.assertEquals(gTask1, task2);
        Assertions.assertEquals(gTask2, task3);
        Assertions.assertEquals(size, 2);


    }
    @Test
    void removeCenter() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "a", "s", Progress.NEW, TaskType.TASK);
        Task task2 = new Task(1, "b", "c", Progress.NEW, TaskType.TASK);
        Task task3 = new Task(2, "v", "k", Progress.NEW, TaskType.TASK);
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        manager.remove(1);
        int size = manager.getHistory().size();
        Task gTask1 = manager.getHistory().get(0);
        Task gTask2 = manager.getHistory().get(1);
        Assertions.assertEquals(gTask1, task);
        Assertions.assertEquals(gTask2, task3);
        Assertions.assertEquals(size, 2);

    }
    @Test
    void removeEnd() {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "a", "s", Progress.NEW, TaskType.TASK);
        Task task2 = new Task(1, "b", "c", Progress.NEW, TaskType.TASK);
        Task task3 = new Task(2, "v", "k", Progress.NEW, TaskType.TASK);
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        manager.remove(2);
        int size = manager.getHistory().size();
        Task gTask1 = manager.getHistory().get(0);
        Task gTask2 = manager.getHistory().get(1);
        Assertions.assertEquals(gTask1, task);
        Assertions.assertEquals(gTask2, task2);
        Assertions.assertEquals(size, 2);

    }
    @Test
    void getHistoryIsEmpty() {
        HistoryManager manager = Managers.getDefaultHistory();
        Assertions.assertTrue(manager.getHistory().isEmpty(), "список не пустой");
    }
}