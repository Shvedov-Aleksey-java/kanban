package task;

import java.util.ArrayList;
import java.util.Objects;

public class Tasks {
    private String name;
    private String description;
    private Progress status;
    private int idNumber = 0;
    private int keyId;
    private ArrayList<Integer> personalIds = new ArrayList<>();

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public ArrayList<Integer> getPersonalIds() {
        return personalIds;
    }

    public void setPersonalIds(ArrayList<Integer> personalIds) {
        this.personalIds = personalIds;
    }

    public Tasks(String name, String description) {
        this.name = name;
        this.description = description;
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

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return idNumber == tasks.idNumber && keyId == tasks.keyId && Objects.equals(name, tasks.name) && Objects.equals(description, tasks.description) && status == tasks.status && Objects.equals(personalIds, tasks.personalIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, idNumber, keyId, personalIds);
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", idNumber=" + idNumber +
                ", keyId=" + keyId +
                ", personalIds=" + personalIds +
                '}';
    }
}
