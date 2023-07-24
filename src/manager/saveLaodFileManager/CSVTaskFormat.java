package manager.saveLaodFileManager;

import manager.HistoryManager;
import task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CSVTaskFormat {


        public static String toString(Task task) {
            return task.getId() + "," + task.getTaskType() + "," + task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + (task.getTaskType().equals(TaskType.SUBTASK) ? "," + ((Subtask)task).getEpicId() : "")
                    + (task.getEndTime() != null ? ("," + task.getStartTime().toString() + "," + task.getDuration().toString()
                    + "," + task.getEndTime().toString()) : "");
        }
        public static Task taskFromString(String value) {
            final String[] values = value.split(",");
            final int id = Integer.parseInt(values[0]);
            final TaskType type = TaskType.valueOf(values[1]);
            final String name = values[2];
            final Progress status = Progress.valueOf(values[3]);
            final String description = values[4];
            if (type == TaskType.TASK) {
                return new Task(id, name, description, status, type);
            }
            if (type == TaskType.SUBTASK) {
                if (values.length == 6) {
                    final int epicId = Integer.parseInt(values[5]);
                    return new Subtask(id, name, description, status, type, epicId);
                } else if (values.length == 5) {
                    return new Subtask(id, name, description, status, type);
                } else if (values.length == 8) {
                    final LocalDateTime start = LocalDateTime.parse(values[5]);
                    final Duration duration = Duration.parse(values[6]);
                    return new Subtask(id, name, description, status, type, start, duration);

                } else if (values.length == 9) {
                    final LocalDateTime start = LocalDateTime.parse(values[6]);
                    final Duration duration = Duration.parse(values[7]);
                    final int epicId = Integer.parseInt(values[5]);
                    return new Subtask(id, name, description, status, type, epicId, start, duration);
                }

            }
            if(type == TaskType.EPIC) {
                if (values.length == 8) {
                    final LocalDateTime start = LocalDateTime.parse(values[5]);
                    final Duration duration = Duration.parse(values[6]);
                    final LocalDateTime end = LocalDateTime.parse(values[7]);
                    return new Epic(id, name, description, status, type, start, duration, end);
                } else {
                    return new Epic(id, name, description, status, type);
                }
            }
            return null;
        }
        public static String toString(HistoryManager historyManager) {
            final List<Task> history = historyManager.getHistory();
            if (history.isEmpty()) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(history.get(0).getId());
            for (int i = 1; i < history.size(); i++) {
                Task task = history.get(i);
                sb.append(",");
                sb.append(task.getId());
            }
            return sb.toString();
        }
        public static List<Integer> historyFromString(String value) {
            final String[] values = value.split(",");
            final ArrayList<Integer> ids = new ArrayList<>(values.length);
            for (String id : values) {
                ids.add(Integer.parseInt(id));
            }
            return ids;
        }
        public static String getHeader() {
            return "id,type,name,status,description,epic,startTime,Duration,EndTime";
        }
}
