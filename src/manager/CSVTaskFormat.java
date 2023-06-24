package manager;

import task.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class CSVTaskFormat {


        public static String toString(Task task) {
            return task.getId() + "," + task.getTaskType() + "," + task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + "," + (task.getTaskType().equals(TaskType.SUBTASK) ? ((Subtask)task).getEpicId() : "");
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
                final int epicId = Integer.parseInt(values[5]);
                return new Subtask(id, name, description, status, type, epicId);
            }
            if(type == TaskType.EPIC) {
                return new Epic(id, name, description, status, type);
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
            return "id,type,name,status,description,epic";
        }
}
