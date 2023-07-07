package manager;

import org.junit.jupiter.api.Test;
import task.Epic;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTestSave {
    @Test
    void saveEmptyList() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getTasks().isEmpty());
        assertTrue(manager.getEpics().isEmpty());
        assertTrue(manager.getSubtasks().isEmpty());

    }
    @Test
    void saveEmptyHistoryList() {
        TaskManager manager = Managers.getDefault();
        assertTrue(manager.getHistory().isEmpty());
    }
    @Test
    void saveEpic() {
        TaskManager manager = Managers.getDefault();
        int epic = manager.addEpic(new Epic("a", "s"));
        Epic epic1 = manager.getEpic(epic);
        assertEquals(epic1.getId(), epic);
    }
}