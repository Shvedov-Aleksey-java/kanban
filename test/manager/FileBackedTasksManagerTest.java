package test;

import manager.FileBackedTasksManager;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;

class FileBackedTasksManagerTest<T extends FileBackedTasksManager> {
    protected T fileBackedTasksManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;
    TaskManager manager1;

    @BeforeEach
    void getDefault(){
        File file1 = new File("C:\\Новая папка\\qwe.csv");
        manager1 = FileBackedTasksManager.loadFromFile(file1);
    }


    @Test
    void loadFromFileEmptyList() {
        Assertions.assertTrue(manager1.getTasks().isEmpty());
        Assertions.assertTrue(manager1.getEpics().isEmpty());
        Assertions.assertTrue(manager1.getSubtasks().isEmpty());
    }

    @Test
    void loadFromHistoryList() {
        Assertions.assertTrue(manager1.getHistory().isEmpty());
    }

    @Test
    void loadFromEpic() {
        int epic = manager1.addEpic(new Epic("a", "s"));
        Epic epic1 = manager1.getEpic(epic);
        Assertions.assertEquals(epic1.getId(), epic);
    }

    @Test
    void saveEmptyList() {
        Assertions.assertTrue(manager1.getTasks().isEmpty());
        Assertions.assertTrue(manager1.getEpics().isEmpty());
        Assertions.assertTrue(manager1.getSubtasks().isEmpty());

    }
    @Test
    void saveEmptyHistoryList() {
        Assertions.assertTrue(manager1.getHistory().isEmpty());
    }
    @Test
    void saveEpic() {
        int epic = manager1.addEpic(new Epic("a", "s"));
        Epic epic1 = manager1.getEpic(epic);
        Assertions.assertEquals(epic1.getId(), epic);
    }
}