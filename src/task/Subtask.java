package task;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(String name, String description) {
        super(name, description);
    }
    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }
    public Subtask(int id, String name, String description, Progress progress, TaskType type,int epicId) {
        super(id, name, description, progress, type);
        this.epicId = epicId;
    }
    public Subtask(String name, String description, Duration duration, LocalDateTime startTime, int epicId) {
        super(name, description, duration, startTime);
        this.epicId = epicId;
    }
    public Subtask(String name, String description, Duration duration, LocalDateTime startTime) {
        super(name, description, duration, startTime);
    }
    public Subtask(int id, String name, String description) {
        super(id, name, description);
    }
    public Integer getEpicId() {
        return epicId;
    }

    public Subtask setEpicId(int epicId) {
        this.epicId = epicId;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(epicId, subtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                '}';
    }
}
