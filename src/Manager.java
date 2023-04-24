import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Manager {
    private int id = 1;

    HashMap<Integer, Task> task = new HashMap<>();
    HashMap<Task, HashMap<Integer, Epic>> epic = new HashMap<>();
    HashMap<Epic, HashMap<Integer, Subtask>> subtask = new HashMap<>();


    void put(Object o){
        if (o instanceof Task){
            if (((Task) o).getIdNumber() == 0){
                ((Task) o).setIdNumber(id);
                ((Task) o).setStatus(Progress.NEW);
                task.put(id, (Task) o);
                id++;
            } else {
                Task t = task.get(((Task) o).getIdNumber());
                HashMap<Integer, Epic> map = epic.get(t);
                ((Task) o).setStatus(t.getStatus());
                epic.remove(t);
                task.put(id, (Task) o);
                epic.put((Task) o, map);
            }

        }
    }

    void put(Object o, Object o1){
        if (o instanceof Task && o1 instanceof Epic){
            if (task.containsValue(o)){
                if (((Epic) o1).getIdNumber() == 0){
                    ((Epic) o1).setIdNumber(id);
                    ((Epic) o1).setStatus(Progress.NEW);
                    epic.put((Task) o, new HashMap<>());
                    epic.get(o).put(id, (Epic) o1);
                    id++;
                    ((Task) o).setStatus(Progress.IN_PROGRESS);
                }else {
                    for (int i: epic.get(o).keySet()){
                        Epic e = epic.get(o).get(i);
                        HashMap<Integer, Subtask> map = subtask.get(e);
                        ((Epic) o1).setStatus(e.getStatus());
                        subtask.remove(e);
                        subtask.put((Epic) o1, map);
                    }
                    ((Epic) o1).setStatus(epic.get(o).get(((Epic) o1).getIdNumber()).getStatus());
                    epic.get(o).put(((Epic) o1).getIdNumber(), (Epic) o1);

                }
            }
        } else if (o instanceof Epic && o1 instanceof Subtask){
            boolean is = false;
            for (Task i: epic.keySet()){
                if (epic.get(i).containsValue(o))is = true;
            }
            if (is){
                if (((Subtask) o1).getIdNumber() == 0){
                    ((Subtask) o1).setIdNumber(id);
                    ((Subtask) o1).setStatus(Progress.NEW);
                    subtask.put((Epic) o, new HashMap<>());
                    subtask.get(o).put(id, (Subtask) o1);
                    id++;
                    ((Epic) o).setStatus(Progress.IN_PROGRESS);
                } else {
                    ((Subtask) o1).setStatus(subtask.get(o).get(((Subtask) o1).getIdNumber()).getStatus());
                    subtask.get(o).put(((Subtask) o1).getIdNumber(), (Subtask) o1);
                }
            }
        }
    }

    Object get(int id){
        for (int i: task.keySet()){
            if (id == i){
                return task.get(id);
            }
        }
        for (Task i: epic.keySet()){
            for (int j: epic.get(i).keySet()){
                if (j == id){
                    return epic.get(i).get(j);
                }
            }
        }
        for (Epic i: subtask.keySet()){
            for (int j: subtask.get(i).keySet()){
                if (j == id){
                    return subtask.get(i).get(j);
                }
            }
        }
        return null;
    }

    void remove(int id){
        if (task.containsKey(id)){
                Task t = task.get(id);
                if (epic.containsKey(t)){
                    for (int i:epic.get(t).keySet()){
                        Epic e = epic.get(t).get(i);
                        subtask.remove(e);
                    }
                    epic.remove(t);
                }
                task.remove(id);
        }

        for (Task i: epic.keySet()){
            for (int j:epic.get(i).keySet()){
                Epic e = epic.get(i).get(j);
                if (e.getIdNumber() == id){
                    subtask.remove(e);
                    epic.remove(i);
                }
            }
        }

        for (Epic i: subtask.keySet()){
            for (int j: subtask.get(i).keySet()){
                Subtask s = subtask.get(i).get(j);
                if (s.getIdNumber() == id){
                    subtask.get(i).remove(j);
                }
            }
            }

        for (Task i: epic.keySet()){
            int idT = epic.get(i).size();
            for (Epic j:subtask.keySet()){
                int idS = subtask.get(j).size();
                for (int l: subtask.get(j).keySet()){
                    if (subtask.get(j).get(l).getStatus() == Progress.DONE)idS--;
                }
                if (idS == 0){
                    j.setStatus(Progress.DONE);
                    idT--;
                }
            }
            if (idT == 0)i.setStatus(Progress.DONE);
        }
        }

    void clear(){
        subtask.clear();
        epic.clear();
        task.clear();
    }

    List<String> listOfTask(){
        List<String> listOfTask = new ArrayList<>();
        for (int i: task.keySet()){
            Task t = task.get(i);
            listOfTask.add(t.getName());
            if (epic.containsKey(t)){
                for (int j:epic.get(t).keySet()){
                    Epic e = epic.get(t).get(j);
                    listOfTask.add(e.getName());
                    if (subtask.containsKey(e)){
                        for (int l: subtask.get(e).keySet()){
                            Subtask s = subtask.get(e).get(l);
                            listOfTask.add(s.getName());
                        }
                    }
                }
            }
        }
        return listOfTask;
    }

    List<String> listOfTask(Object o){
        List<String> listOfTask = new ArrayList<>();
        if (o instanceof Epic && subtask.containsKey(o)){
            for (Epic i: subtask.keySet()){
                if (i.equals(o)){
                    for (int j:subtask.get(i).keySet()){
                        listOfTask.add(subtask.get(i).get(j).getName());
                    }
                }
            }
        }
        return listOfTask;
    }

    void setStatus(Object o, Progress p){
        if (o instanceof Subtask && p != null) {
            ((Subtask) o).setStatus(p);


            for (Task i: epic.keySet()){
                int idT = epic.get(i).size();
                for (Epic j:subtask.keySet()){
                    int idS = subtask.get(j).size();
                    for (int l: subtask.get(j).keySet()){
                        if (subtask.get(j).get(l).getStatus() == Progress.DONE)idS--;
                    }
                    if (idS == 0){
                        j.setStatus(Progress.DONE);
                        idT--;
                    }
                }
                if (idT == 0)i.setStatus(Progress.DONE);
            }
        }

    }


    }




