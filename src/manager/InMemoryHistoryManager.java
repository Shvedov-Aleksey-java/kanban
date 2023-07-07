package manager;

import task.Progress;
import task.Task;
import task.TaskType;

import java.io.Serializable;
import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {

    private Map<Integer, Node> history = new HashMap<>();

    private Node first;

    private Node last;

    private void linkLast(Task task) {
        final Node node = new Node(last, task, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    private void removeNode(int id) {
        final Node node = history.remove(id);
        if (node == null) {
            return;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
            if (node.next == null) {
                last = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        } else {
            first = node.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
        }
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node node = first;
        while (node != null) {
            tasks.add(node.data);
            node = node.next;
        }
        return tasks;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        final int id = task.getId();
        removeNode(id);
        linkLast(task);
        history.put(id, last);
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    class Node {
        public Task data;
        public Node next;
        public Node prev;

        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public static void main(String[] args) {
        HistoryManager manager = Managers.getDefaultHistory();
        Task task = new Task(0, "a", "s", Progress.NEW, TaskType.TASK);
        Task task2 = new Task(1, "b", "c", Progress.NEW, TaskType.TASK);
        Task task3 = new Task(2, "b", "c", Progress.NEW, TaskType.TASK);
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        manager.remove(0);
        System.out.println(manager.getHistory().toString());
    }
}


