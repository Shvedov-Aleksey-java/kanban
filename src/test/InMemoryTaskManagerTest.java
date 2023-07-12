package test;

import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest<T extends InMemoryTaskManager> {
    protected T inMemoryTaskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    TaskManager manager;

    @BeforeEach
    void getDefault(){
        manager = Managers.getDefault();
    }

    @Test
    void getTasks() {

        Assertions.assertTrue(manager.getTasks().isEmpty());
        Assertions.assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("a", "d"));
        Assertions.assertEquals(task, manager.getTask(task).getId());
    }

    @Test
    void getEpics() {

        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
    }

    @Test
    void getSubtasks() {

        Assertions.assertTrue(manager.getSubtasks().isEmpty());
        Assertions.assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        Assertions.assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        Assertions.assertEquals(epic, subtask.getEpicId());

    }

    @Test
    void getHistory() {

        Assertions.assertTrue(manager.getHistory().isEmpty());
        int task = manager.addTask(new Task("a", "s"));
        manager.getTask(task);
        Assertions.assertFalse(manager.getHistory().isEmpty());
        manager.getTask(8);
        Assertions.assertEquals(manager.getHistory().size(), 1);
    }

    @Test
    void addTask() {

        Assertions.assertTrue(manager.getTasks().isEmpty());
        Assertions.assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("a", "d"));
        Assertions.assertEquals(task, manager.getTask(task).getId());
    }

    @Test
    void addEpic() {

        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
    }

    @Test
    void addNewSubtask() {

        Assertions.assertTrue(manager.getSubtasks().isEmpty());
        Assertions.assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        Assertions.assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        Assertions.assertEquals(epic, subtask.getEpicId());
    }

    @Test
    void getTask() {
        Assertions.assertTrue(manager.getTasks().isEmpty());
        Assertions.assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("d", "f"));
        assertNotNull(manager.getTask(task));
        assertFalse(manager.getTasks().isEmpty());
    }

    @Test
    void getEpic() {

        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
    }

    @Test
    void getSubtask() {

        Assertions.assertTrue(manager.getSubtasks().isEmpty());
        Assertions.assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        Assertions.assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        Assertions.assertEquals(epic, subtask.getEpicId());
    }

    @Test
    void deleteTask() {

        int task = manager.addTask(new Task("a", "d"));
        Assertions.assertFalse(manager.getTasks().isEmpty());
        Assertions.assertEquals(task, manager.getTask(task).getId());
        manager.deleteTask(task);
        Assertions.assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void deleteEpic() {

        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        Assertions.assertFalse(manager.getEpics().isEmpty());
        manager.deleteEpic(epic);
        Assertions.assertNull(manager.getEpic(epic));
        Assertions.assertTrue(manager.getEpics().isEmpty());

    }

    @Test
    void deleteSubtask() {

        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        Assertions.assertEquals(epic, subtask.getEpicId());
        Assertions.assertFalse(manager.getSubtasks().isEmpty());
        manager.deleteSubtask(sub1);
        Assertions.assertNull(manager.getSubtask(sub1));
        Assertions.assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void updateTask() {

        Assertions.assertTrue(manager.getTasks().isEmpty());
        int task = manager.addTask(new Task("a", "s"));
        Task t = manager.getTask(task);
        String name = t.getName();
        String description = t.getDescription();
        Assertions.assertEquals(name, "a");
        Assertions.assertEquals(description, "s");
        manager.updateTask(new Task(10, "k", "l"));
        Assertions.assertEquals(t, manager.getTask(task));
        manager.updateTask(new Task(task, "c", "v"));
        Task t1 = manager.getTask(task);
        String name1 = t1.getName();
        String description1 = t1.getDescription();
        Assertions.assertEquals(name1, "c");
        Assertions.assertEquals(description1, "v");

    }

    @Test
    void updateEpic() {

        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        Epic t = manager.getEpic(epic);
        String name = t.getName();
        String description = t.getDescription();
        Assertions.assertEquals(name, "a");
        Assertions.assertEquals(description, "d");
        manager.updateEpic(new Epic(10, "k", "l"));
        Assertions.assertEquals(t, manager.getEpic(epic));
        manager.updateEpic(new Epic(epic, "c", "v"));
        Epic t1 = manager.getEpic(epic);
        String name1 = t1.getName();
        String description1 = t1.getDescription();
        Assertions.assertEquals(name1, "c");
        Assertions.assertEquals(description1, "v");
    }

    @Test
    void updateSubtask() {

        Assertions.assertTrue(manager.getSubtasks().isEmpty());
        Assertions.assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        Assertions.assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        Assertions.assertEquals(epic, subtask.getEpicId());
        Subtask t = manager.getSubtask(sub1);
        String name = t.getName();
        String description = t.getDescription();
        Assertions.assertEquals(name, "l");
        Assertions.assertEquals(description, "k");
        manager.updateSubtask(new Subtask(10, "k", "l"));
        Assertions.assertEquals(t, manager.getSubtask(sub1));
        manager.updateSubtask(new Subtask(sub1, "c", "v"));
        Subtask t1 = manager.getSubtask(sub1);
        String name1 = t1.getName();
        String description1 = t1.getDescription();
        Assertions.assertEquals(name1, "c");
        Assertions.assertEquals(description1, "v");
    }

    @Test
    void clearTasks() {

        Assertions.assertTrue(manager.getTasks().isEmpty());
        int task = manager.addTask(new Task("a", "s"));
        Assertions.assertFalse(manager.getTasks().isEmpty());
        manager.clearTasks();
        Assertions.assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void clearEpics() {

        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        Assertions.assertFalse(manager.getEpics().isEmpty());
        manager.clearEpics();
        Assertions.assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void clearSubtasks() {

        Assertions.assertTrue(manager.getSubtasks().isEmpty());
        Assertions.assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        Assertions.assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        Assertions.assertEquals(epic, subtask.getEpicId());
        Assertions.assertFalse(manager.getSubtasks().isEmpty());
        manager.clearSubtasks();
        Assertions.assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void updateStatusEpic() {

        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        manager.updateStatusEpic(epic, Progress.NEW);
        Assertions.assertEquals(Progress.NEW, manager.getEpic(epic).getStatus());
    }

    @Test
    void updateStatusTask() {

        Assertions.assertTrue(manager.getTasks().isEmpty());
        Assertions.assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("a", "d"));
        Assertions.assertEquals(task, manager.getTask(task).getId());
        Assertions.assertEquals(Progress.NEW, manager.getTask(task).getStatus());
        manager.updateStatusTask(task, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getTask(task).getStatus());
    }

    @Test
    void updateStatusSubtask() {

        Assertions.assertTrue(manager.getSubtasks().isEmpty());
        Assertions.assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        Assertions.assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        Assertions.assertEquals(epic, subtask.getEpicId());
        manager.updateStatusSubtask(sub1, Progress.DONE);
        Assertions.assertEquals(manager.getSubtask(sub1).getStatus(), Progress.DONE);
    }

    @Test
    void addEpicSubtaskIds() {

        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        int epic1 = manager.addEpic(new Epic("k", "l"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        Assertions.assertTrue(manager.getEpic(epic1).getSubtaskIds().isEmpty());
        manager.addEpicSubtaskIds(epic1, subtask);
        Assertions.assertFalse(manager.getEpic(epic1).getSubtaskIds().isEmpty());
    }

    @Test
    void getEpicSubtasks() {
        int epic = manager.addEpic(new Epic("a", "d"));
        int epic1 = manager.addEpic(new Epic("k", "l"));
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        Assertions.assertNull(manager.getEpicSubtasks(epic1));
        Assertions.assertNull(manager.getEpicSubtasks(10));
        Assertions.assertFalse(manager.getEpicSubtasks(epic).isEmpty());
    }

    @Test
    void testUpdateEpic() {
        Assertions.assertTrue(manager.getEpics().isEmpty());
        Assertions.assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        Assertions.assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        Epic t = manager.getEpic(epic);
        String name = t.getName();
        String description = t.getDescription();
        Assertions.assertEquals(name, "a");
        Assertions.assertEquals(description, "d");
        manager.updateEpic(new Epic(10, "k", "l"));
        Assertions.assertEquals(t, manager.getEpic(epic));
        manager.updateEpic(new Epic(epic, "c", "v"));
        Epic t1 = manager.getEpic(epic);
        String name1 = t1.getName();
        String description1 = t1.getDescription();
        Assertions.assertEquals(name1, "c");
        Assertions.assertEquals(description1, "v");
    }
}