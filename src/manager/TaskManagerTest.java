package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    @Test
    void getTask() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getTasks().isEmpty());
        assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("a", "d"));
        assertEquals(task, manager.getTask(task).getId());
    }

    @Test
    void getTasks() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getTasks().isEmpty());
        assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("a", "d"));
        assertEquals(task, manager.getTask(task).getId());
    }

    @Test
    void getEpics() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getEpics().isEmpty());
        assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
    }

    @Test
    void getSubtasks() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getSubtasks().isEmpty());
        assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        assertEquals(epic, subtask.getEpicId());

    }

    @Test
    void getHistory() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getHistory().isEmpty());
        int task = manager.addTask(new Task("a", "s"));
        manager.getTask(task);
        assertFalse(manager.getHistory().isEmpty());
        manager.getTask(8);
        assertEquals(manager.getHistory().size(), 1);
    }

    @Test
    void addTask() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getTasks().isEmpty());
        assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("a", "d"));
        assertEquals(task, manager.getTask(task).getId());
    }

    @Test
    void addEpic() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getEpics().isEmpty());
        assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
    }

    @Test
    void addNewSubtask() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getSubtasks().isEmpty());
        assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        assertEquals(epic, subtask.getEpicId());
    }

    @Test
    void getEpic() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getEpics().isEmpty());
        assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
    }

    @Test
    void getSubtask() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getSubtasks().isEmpty());
        assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        assertEquals(epic, subtask.getEpicId());
    }

    @Test
    void deleteTask() {
        TaskManager manager = Managers.getDefault();
        int task = manager.addTask(new Task("a", "d"));
        assertFalse(manager.getTasks().isEmpty());
        assertEquals(task, manager.getTask(task).getId());
        manager.deleteTask(task);
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void deleteEpic() {
        TaskManager manager = Managers.getDefault();
        int epic = manager.addEpic(new Epic("a", "d"));
        assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        assertFalse(manager.getEpics().isEmpty());
        manager.deleteEpic(epic);
        assertNull(manager.getEpic(epic));
        assertTrue(manager.getEpics().isEmpty());

    }

    @Test
    void deleteSubtask() {
        TaskManager manager = Managers.getDefault();
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        assertEquals(epic, subtask.getEpicId());
        assertFalse(manager.getSubtasks().isEmpty());
        manager.deleteSubtask(sub1);
        assertNull(manager.getSubtask(sub1));
        assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void updateTask() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getTasks().isEmpty());
        int task = manager.addTask(new Task("a", "s"));
        Task t = manager.getTask(task);
        String name = t.getName();
        String description = t.getDescription();
        assertEquals(name, "a");
        assertEquals(description, "s");
        manager.updateTask(new Task(10, "k", "l"));
        assertEquals(t, manager.getTask(task));
        manager.updateTask(new Task(task, "c", "v"));
        Task t1 = manager.getTask(task);
        String name1 = t1.getName();
        String description1 = t1.getDescription();
        assertEquals(name1, "c");
        assertEquals(description1, "v");

    }

    @Test
    void updateEpic() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getEpics().isEmpty());
        assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        Epic t = manager.getEpic(epic);
        String name = t.getName();
        String description = t.getDescription();
        assertEquals(name, "a");
        assertEquals(description, "d");
        manager.updateEpic(new Epic(10, "k", "l"));
        assertEquals(t, manager.getEpic(epic));
        manager.updateEpic(new Epic(epic, "c", "v"));
        Epic t1 = manager.getEpic(epic);
        String name1 = t1.getName();
        String description1 = t1.getDescription();
        assertEquals(name1, "c");
        assertEquals(description1, "v");
    }

    @Test
    void updateSubtask() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getSubtasks().isEmpty());
        assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        assertEquals(epic, subtask.getEpicId());
        Subtask t = manager.getSubtask(sub1);
        String name = t.getName();
        String description = t.getDescription();
        assertEquals(name, "l");
        assertEquals(description, "k");
        manager.updateSubtask(new Subtask(10, "k", "l"));
        assertEquals(t, manager.getSubtask(sub1));
        manager.updateSubtask(new Subtask(sub1, "c", "v"));
        Subtask t1 = manager.getSubtask(sub1);
        String name1 = t1.getName();
        String description1 = t1.getDescription();
        assertEquals(name1, "c");
        assertEquals(description1, "v");
    }

    @Test
    void clearTasks() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getTasks().isEmpty());
        int task = manager.addTask(new Task("a", "s"));
        assertFalse(manager.getTasks().isEmpty());
        manager.clearTasks();
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void clearEpics() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getEpics().isEmpty());
        assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        assertFalse(manager.getEpics().isEmpty());
        manager.clearEpics();
        assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void clearSubtasks() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getSubtasks().isEmpty());
        assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        assertEquals(epic, subtask.getEpicId());
        assertFalse(manager.getSubtasks().isEmpty());
        manager.clearSubtasks();
        assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void updateStatusEpic() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getEpics().isEmpty());
        assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        assertEquals(epic, manager.getEpic(epic).getId());
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
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getTasks().isEmpty());
        assertNull(manager.getTask(0));
        int task = manager.addTask(new Task("a", "d"));
        assertEquals(task, manager.getTask(task).getId());
        assertEquals(Progress.NEW, manager.getTask(task).getStatus());
        manager.updateStatusTask(task, Progress.DONE);
        assertEquals(Progress.DONE, manager.getTask(task).getStatus());
    }

    @Test
    void updateStatusSubtask() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getSubtasks().isEmpty());
        assertNull(manager.getSubtask(0));
        int sub = manager.addNewSubtask(new Subtask("l", "k"));
        assertNull(manager.getSubtask(sub).getEpicId());
        int epic = manager.addEpic(new Epic("a", "d"));
        int sub1 = manager.addNewSubtask(new Subtask("l", "k", epic));
        Subtask subtask = manager.getSubtask(sub1);
        assertEquals(epic, subtask.getEpicId());
        manager.updateStatusSubtask(sub1, Progress.DONE);
        assertEquals(manager.getSubtask(sub1).getStatus(), Progress.DONE);
    }

    @Test
    void addEpicSubtaskIds() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getEpics().isEmpty());
        assertNull(manager.getEpic(0));
        int epic = manager.addEpic(new Epic("a", "d"));
        int epic1 = manager.addEpic(new Epic("k", "l"));
        assertEquals(epic, manager.getEpic(epic).getId());
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        manager.updateStatusSubtask(subtask, Progress.DONE);
        manager.updateStatusSubtask(subtask1, Progress.DONE);
        Assertions.assertEquals(Progress.DONE, manager.getEpic(epic).getStatus());
        assertTrue(manager.getEpic(epic1).getSubtaskIds().isEmpty());
        manager.addEpicSubtaskIds(epic1, subtask);
        assertFalse(manager.getEpic(epic1).getSubtaskIds().isEmpty());
    }

    @Test
    void getEpicSubtasks() {
        TaskManager manager = Managers.getDefault();
        int epic = manager.addEpic(new Epic("a", "d"));
        int epic1 = manager.addEpic(new Epic("k", "l"));
        int subtask = manager.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = manager.addNewSubtask(new Subtask("2", "2", epic));
        assertNull(manager.getEpicSubtasks(epic1));
        assertNull(manager.getEpicSubtasks(10));
        assertFalse(manager.getEpicSubtasks(epic).isEmpty());

    }

    @Test
    void updateEpicTime() {
        TaskManager manager = Managers.getDefault();
        int epic = manager.addEpic(new Epic("a", "d"));
        int subtask = manager.addNewSubtask(new Subtask("a", "s", Duration.ofMinutes(30),
                LocalDateTime.of(2023,7, 1, 13, 0)));
        int subtask1 = manager.addNewSubtask(new Subtask("a", "s", Duration.ofMinutes(30),
                LocalDateTime.of(2023,6, 1, 13, 0)));
        int subtask2 = manager.addNewSubtask(new Subtask("a", "s", Duration.ofMinutes(30),
                LocalDateTime.of(2023,8, 1, 13, 0)));
        manager.addEpicSubtaskIds(epic, subtask);
        manager.addEpicSubtaskIds(epic, subtask1);
        manager.addEpicSubtaskIds(epic, subtask2);
        Duration subtaskTime = manager.getSubtask(subtask).getDuration();
        Duration subtaskTime1 = subtaskTime.plus(manager.getSubtask(subtask1).getDuration());
        Duration sumTime = subtaskTime1.plus(manager.getSubtask(subtask2).getDuration());
        assertEquals(sumTime, manager.getEpic(epic).getDuration());
        assertEquals(manager.getEpic(epic).getStartTime(), LocalDateTime.of(2023,6, 1, 13, 0));
        assertEquals(manager.getEpic(epic).getEndTime(), LocalDateTime.of(2023,8, 1, 13, 0)
                .plus(manager.getSubtask(subtask2).getDuration()));
        final ManagerSaveException exception = assertThrows(
                ManagerSaveException.class,
                () -> {
                    int subtask3 = manager.addNewSubtask(new Subtask("a", "s", Duration.ofMinutes(30),
                            LocalDateTime.of(2023,8, 1, 13, 0)));
                }
        );
        assertEquals(exception.getMessage(), "время уже занято");
    }
}
