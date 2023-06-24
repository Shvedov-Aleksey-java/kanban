package task;

import java.io.Serializable;
import java.util.Objects;

public class Task {
    private TaskType taskType;
    private String name;
    private String description;
    private Progress status;
    private int id = 0;


    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Task(int id, String name, String description, Progress progress, TaskType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = progress;
        this.taskType = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Progress getStatus() {
        return status;
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task tasks = (Task) o;
        return id == tasks.id && Objects.equals(name, tasks.name) && Objects.equals(description, tasks.description) && status == tasks.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
