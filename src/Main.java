import manager.saveLoadFileServerManager.HttpTaskServer;
import manager.saveLoadFileServerManager.KVServer;
import manager.saveLoadFileServerManager.KVTaskClient;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        new HttpTaskServer().start();

        }
    }


