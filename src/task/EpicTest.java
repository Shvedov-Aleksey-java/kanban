package task;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EpicTest {



    @Test
    void getSubtaskIdsEmptyList() {
        TaskManager managers = Managers.getDefault();
        int epic = managers.addEpic(new Epic("a", "b"));
        Assertions.assertEquals(Progress.NEW, managers.getEpic(epic).getStatus());
    }
    @Test
    void getSubtaskIdsAllProgressNEW() {
        TaskManager managers = Managers.getDefault();
        int epic = managers.addEpic(new Epic("a", "b"));
        int sub = managers.addNewSubtask(new Subtask("2", "1", epic));
        int sub1 = managers.addNewSubtask(new Subtask("2", "2", epic));

        Assertions.assertEquals(Progress.NEW, managers.getEpic(epic).getStatus());
    }
    @Test
    void getSubtaskIdsAllProgressDONE() {
        TaskManager managers = Managers.getDefault();
        int epic = managers.addEpic(new Epic("a", "b"));
        int subtask = managers.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = managers.addNewSubtask(new Subtask("2", "2", epic));
        managers.updateStatusSubtask(subtask, Progress.DONE);
        managers.updateStatusSubtask(subtask1, Progress.DONE);

        Assertions.assertEquals(Progress.DONE, managers.getEpic(epic).getStatus());
    }
    @Test
    void getSubtaskIdsAllProgressNEWendDONE() {
        TaskManager managers = Managers.getDefault();
        int epic = managers.addEpic(new Epic("a", "b"));
        int subtask = managers.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = managers.addNewSubtask(new Subtask("2", "2", epic));
        managers.updateStatusSubtask(subtask, Progress.NEW);
        managers.updateStatusSubtask(subtask1, Progress.DONE);

        Assertions.assertEquals(Progress.IN_PROGRESS, managers.getEpic(epic).getStatus());
    }
    @Test
    void getSubtaskIdsAllProgressIN_Progress() {
        TaskManager managers = Managers.getDefault();
        int epic = managers.addEpic(new Epic("a", "b"));
        int subtask = managers.addNewSubtask(new Subtask("a", "b", epic));
        int subtask1 = managers.addNewSubtask(new Subtask("2", "2", epic));
        managers.updateStatusSubtask(subtask, Progress.IN_PROGRESS);
        managers.updateStatusSubtask(subtask1, Progress.IN_PROGRESS);

        Assertions.assertEquals(Progress.IN_PROGRESS, managers.getEpic(epic).getStatus());
    }


}