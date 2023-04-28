public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();
        manager.put(new Task("задача 1", "описание 1"));
        manager.put(new Task("задача 2", "описание 2"));
        manager.put(manager.get(1), new Epic("эпик1", "описание 1"));
        manager.put(manager.get(2), new Epic("эпик2", "описание 1"));
        manager.put(manager.get(3), new Subtask("подзадача 1", "описание 1"));
        manager.put(manager.get(3), new Subtask("подзадача 2", "описание 2"));
        manager.put(manager.get(4), new Subtask("подзадача 3", "описание 3"));
        System.out.println(manager.listOfTask());
        manager.setStatus(manager.get(5), Progress.IN_PROGRESS);
        System.out.println(manager.listOfTask());
        manager.remove(3);
        System.out.println(manager.listOfTask());

        }
    }


