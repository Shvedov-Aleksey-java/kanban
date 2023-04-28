import java.util.HashMap;



public class Manager {
    private int id = 1;

    private HashMap<Integer, Task> task = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, Epic>> epic = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, Subtask>> subtask = new HashMap<>();


    void put(Object o){
        if (o instanceof Task){
            if (((Task) o).getIdNumber() == 0){
                ((Task) o).setIdNumber(id);
                ((Task) o).setStatus(Progress.NEW);
                task.put(id, (Task) o);
                id++;
            } else {
                ((Task) o).setStatus(task.get(((Task) o).getIdNumber()).getStatus());
                task.put(((Task) o).getIdNumber(), (Task) o);
            }
        }
        statusUpdates();
    }

    void put(Object o, Object o1){
        if (o instanceof Task && o1 instanceof Epic){
          if (epic.containsKey(((Task) o).getIdNumber())){
              if (((Epic) o1).getIdNumber() == 0){
                  ((Epic) o1).setIdNumber(id);
                  ((Epic) o1).setStatus(Progress.NEW);
                  ((Task) o).setStatus(Progress.IN_PROGRESS);
                  epic.get(((Task) o).getIdNumber()).put(id, (Epic) o1);
                  id++;
              }else {
                  epic.get(((Epic) o1).getIdNumber()).put(((Epic) o1).getIdNumber(), (Epic) o1);
              }
          }else {
              ((Epic) o1).setIdNumber(id);
              ((Epic) o1).setStatus(Progress.NEW);
              ((Task) o).setStatus(Progress.IN_PROGRESS);
              epic.put(((Task) o).getIdNumber(), new HashMap<>());
              epic.get(((Task) o).getIdNumber()).put(id, (Epic) o1);
              id++;
          }
        } else if (o instanceof Epic && o1 instanceof Subtask){
           if (subtask.containsKey(((Epic) o).getIdNumber())){
               if (((Subtask) o1).getIdNumber() == 0){
                   ((Subtask) o1).setIdNumber(id);
                   ((Subtask) o1).setStatus(Progress.NEW);
                   ((Epic) o).setStatus(Progress.IN_PROGRESS);
                   subtask.get(((Epic) o).getIdNumber()).put(id, (Subtask) o1);
                   id++;
               }else {
                   subtask.get(((Epic) o).getIdNumber()).put(((Subtask) o1).getIdNumber(), (Subtask) o1);
               }
           }else {
               ((Subtask) o1).setIdNumber(id);
               ((Subtask) o1).setStatus(Progress.NEW);
               ((Epic) o).setStatus(Progress.IN_PROGRESS);
               subtask.put(((Epic) o).getIdNumber(), new HashMap<>());
               subtask.get(((Epic) o).getIdNumber()).put(id, (Subtask) o1);
               id++;
           }
            }
        statusUpdates();
        }

    Object get(int id){
        if (task.containsKey(id)){
            return task.get(id);
        }
        for (int i:epic.keySet()){
            if (epic.get(i).containsKey(id))return epic.get(i).get(id);
        }
        for (int i: subtask.keySet()){
            if (subtask.get(i).containsKey(id))return subtask.get(i).get(id);
        }
        return null;
    }

    void remove(int id){
        if (task.containsKey(id)){
            if (epic.containsKey(id)){
                for (int i: epic.get(id).keySet()){
                    if (subtask.containsKey(i))subtask.remove(i);
                }
                epic.remove(id);
            }
            task.remove(id);
        }
        for (int i: epic.keySet()){
            if (epic.get(i).containsKey(id)){
                if (subtask.containsKey(id))subtask.remove(id);
                epic.get(i).remove(id);
            }
        }
        for (int i: subtask.keySet()){
            if (subtask.get(i).containsKey(id)){
                subtask.get(i).remove(id);
            }
        }

        statusUpdates();
        }

    void clear(){
        subtask.clear();
        epic.clear();
        task.clear();
    }

    String listOfTask(){
        String strings = "";
        if (!(task.isEmpty())){
            for (int i: task.keySet()){
                Task t  = task.get(i);
                strings += "имя: " + t.getName() +
                        " описание: " + t.getDescription() +
                        " статус: " + t.getStatus() +
                        " ид: " + t.getIdNumber() +
                        "\n";
                if(epic.containsKey(i)){
                    for (int j: epic.get(i).keySet()){
                        Epic e = epic.get(i).get(j);
                        strings += "имя: " + e.getName() +
                                " описание: " + e.getDescription() +
                                " статус: " + e.getStatus() +
                                " ид: " + e.getIdNumber() +
                                "\n";
                        if (subtask.containsKey(j)){
                            for (int l: subtask.get(j).keySet()){
                                Subtask s = subtask.get(j).get(l);
                                strings += "имя: " + s.getName() +
                                        " описание: " + s.getDescription() +
                                        " статус: " + s.getStatus() +
                                        " ид: " + s.getIdNumber() +
                                        "\n";
                            }
                        }
                    }
                }
            }
        }


        return strings;
    }

    String listOfTask(Object o){
        String strings = "";
        if (o instanceof Epic){
            if (subtask.containsKey(((Epic) o).getIdNumber())){
                for (int i: subtask.get(((Epic) o).getIdNumber()).keySet()){
                    Subtask s = subtask.get(((Epic) o).getIdNumber()).get(i);
                    strings += "имя: " + s.getName() +
                            " описание: " + s.getDescription() +
                            " статус: " + s.getStatus() +
                            " ид: " + s.getIdNumber() +
                            "\n";
                }
            }
        }
        return strings;
    }

    void setStatus(Object o, Progress p){
        if (o instanceof Subtask && p != null) {
            ((Subtask) o).setStatus(p);
            statusUpdates();
        }

    }

    private void statusUpdates(){
       if (!(task.isEmpty())){
           Task t;
           for (int i:task.keySet()){
               t = task.get(i);
               if (epic.containsKey(i)){
                   Epic e;
                   int sd = epic.get(i).size();
                   int dt = epic.get(i).size();
                   if (!(epic.get(i).isEmpty())){
                       for (int j:epic.get(i).keySet()){
                           e = epic.get(i).get(j);
                           if (subtask.containsKey(j)){
                               Subtask s;
                               int q = subtask.get(j).size();
                               int w = subtask.get(j).size();
                               for (int l: subtask.get(j).keySet()){
                                   s = subtask.get(j).get(l);
                                   if (s.getStatus() == Progress.NEW){
                                       q--;
                                   }else if (s.getStatus() == Progress.DONE){
                                       w--;
                                   }
                               }
                               if (q == 0){
                                   e.setStatus(Progress.NEW);
                                   sd--;
                               } else if (w == 0){
                                   e.setStatus(Progress.DONE);
                                   dt--;
                               } else {
                                   e.setStatus(Progress.IN_PROGRESS);
                               }
                           }else {
                               e.setStatus(Progress.NEW);
                           }
                       }
                      if (sd == 0){
                          t.setStatus(Progress.NEW);
                      } else if (dt == 0){
                          t.setStatus(Progress.DONE);
                      }else {
                          t.setStatus(Progress.IN_PROGRESS);
                      }
                   }
               }else {
                   t.setStatus(Progress.NEW);
               }
           }
       }
    }


    }




