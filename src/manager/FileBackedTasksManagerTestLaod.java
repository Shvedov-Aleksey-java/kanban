package manager;

import org.junit.jupiter.api.Test;
import task.Epic;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTestLaod {


    @Test
    void loadFromFileEmptyList() {
        File file1 = new File("C:\\Новая папка\\qwe.csv");
        TaskManager manager1 = FileBackedTasksManager.loadFromFile(file1);
        assertTrue(manager1.getTasks().isEmpty());
        assertTrue(manager1.getEpics().isEmpty());
        assertTrue(manager1.getSubtasks().isEmpty());
    }

    @Test
    void loadFromHistoryList() {
        File file1 = new File("C:\\Новая папка\\qwe.csv");
        TaskManager manager1 = FileBackedTasksManager.loadFromFile(file1);
        assertTrue(manager1.getHistory().isEmpty());
    }

    @Test
    void loadFromEpic() {
        File file1 = new File("C:\\Новая папка\\qwe.csv");
        TaskManager manager1 = FileBackedTasksManager.loadFromFile(file1);
        int epic = manager1.addEpic(new Epic("a", "s"));
        Epic epic1 = manager1.getEpic(epic);
        assertEquals(epic1.getId(), epic);
    }




}