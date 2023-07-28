import manager.http.KVServer;
import manager.http.KVTaskClient;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        //new HttpTaskServer().start();
        KVTaskClient client = new KVTaskClient();
        client.saveState("epic", "dcsdc");
        System.out.println(client.loadState("epic"));
        }
    }


