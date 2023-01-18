package study.entity;


import java.time.LocalDate;

/*Объект смены*/
public class WorkingShift {

    private int id;
    private String fullName;
    private LocalDate shift;
    private String time;

    public WorkingShift() {
    }

    public WorkingShift(String fullName, LocalDate shift) {
        this.fullName = fullName;
        this.shift = shift;
    }

    public WorkingShift(String fullName, LocalDate shift, String time) {
        this.fullName = fullName;
        this.shift = shift;
        this.time = time;
    }

    @Override
    public String toString() {
        return "WorkingShift{" +
                "fullName='" + fullName + '\'' +
                ", shift=" + shift +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getShift() {
        return shift;
    }

    public void setShift(LocalDate shift) {
        this.shift = shift;
    }
}
