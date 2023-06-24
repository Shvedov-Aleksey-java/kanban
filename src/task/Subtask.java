package task;

import java.io.Serializable;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description) {
        super(name, description);
    }
    public Subtask(int id, String name, String description, Progress progress, TaskType type,int epicId) {
        super(id, name, description, progress, type);
        this.epicId = epicId;
    }

    public int getEpicId() {
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
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", status=" + super.getStatus() +
                ", id=" + super.getId() +
                '}';
    }
}
