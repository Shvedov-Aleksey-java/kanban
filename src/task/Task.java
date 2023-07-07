package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private TaskType taskType;
    private String name;
    private String description;
    private Progress status;
    private int id = 0;
    private Duration duration = Duration.ofMinutes(0);
    private LocalDateTime startTime;


    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
    }
    public Task(int id, String name, String description) {
        this.id = id;
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

    public Subtask setStatus(Progress status) {
        this.status = status;
        return null;
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
    public String toString() {
        return "Task{" +
                "taskType=" + taskType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    public LocalDateTime getEndTime() {
        if (this.startTime == null)return null;
        return this.startTime.plus(this.duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && taskType == task.taskType && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskType, name, description, status, id, duration, startTime);
    }
}
