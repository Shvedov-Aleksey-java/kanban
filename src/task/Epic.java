package task;



import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }
    public Epic(int id, String name, String description, Progress status, TaskType type) {
        super(id, name, description, status, type);
    }
    public Epic(int id, String name, String description, Progress status, TaskType type, LocalDateTime startTime, Duration duration, LocalDateTime endTime) {
        super(id, name, description, status, type);
        setStartTime(startTime);
        setDuration(duration);
        this.endTime = endTime;
    }
    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskIds(int subtaskId) {
        this.subtaskIds.add(subtaskId);
    }

    public void removeSubtask(int id){

        getSubtaskIds().remove((Integer) id);
    }

    public void setSubtaskIds(List<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds, endTime);
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }



    @Override
    public void setDuration(Duration duration) {
        super.setDuration(duration);
    }
}
