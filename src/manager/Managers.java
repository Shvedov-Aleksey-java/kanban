package manager;


import manager.saveLaodFileManager.FileBackedTasksManager;
import manager.saveLoadFileServerManager.HttpTaskManager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        return new HttpTaskManager(new File("http://localhost:8080"));
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
