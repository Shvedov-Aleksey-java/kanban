import manager.Managers;
import manager.TaskManager;
import manager.saveLoadFileServerManager.HttpTaskManager;
import manager.saveLoadFileServerManager.HttpTaskServer;
import manager.saveLoadFileServerManager.KVServer;
import task.Epic;
import task.Progress;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        new HttpTaskServer().start();

        }
    }


