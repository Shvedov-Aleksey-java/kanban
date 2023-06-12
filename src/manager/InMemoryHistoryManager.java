package manager;

import task.Task;

import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();
    private Map<Integer, Node> historyMap = new HashMap<>();

    private Node data;

    public void removeNode(Node node){
        if (node == null){
            return;
        }
        Node next = node.next;
        Node prev = node.prev;
        if (prev == null && next != null) {
            next.addPrev(null);
        } else if (prev != null && next == null) {
            prev.addNext(null);
        } else {
            next.addPrev(prev);
            prev.addNext(next);
        }
    }

    @Override
    public void add(Task task) {
        if (historyMap.isEmpty()) {
            Node node = new Node(null, task, null);
            data = node;
            historyMap.put(task.getId(), data);
        } else {
            if (historyMap.containsKey(task.getId())){
                Node node = new Node(data, task, null);
                data.addNext(node);
                remove(task.getId());
                historyMap.put(task.getId(), node);
                data = node;

            } else {
                Node node = new Node(data, task, null);
                data.addNext(node);
                historyMap.put(task.getId(), node);
                data = node;
            }
        }
    }

    @Override
    public void remove(int id) {
        Node node = historyMap.get(id);
        removeNode(node);
        historyMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        history.clear();
        for (int i : historyMap.keySet()) {
            history.add(historyMap.get(i).data);
        }
        return history;
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

        public void addNext(Node node) {
            this.next = node;
        }

        public void addPrev(Node node){
            this.prev = node;
        }




    }


}


