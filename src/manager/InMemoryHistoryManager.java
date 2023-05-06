package manager;

import task.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    private List<Task> history = inMemoryTaskManager.getHistory();

    @Override
    public void add(Task task) {
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
