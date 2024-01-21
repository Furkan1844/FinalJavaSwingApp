package models;

public class Teacher{
    private int teacher_id;
    private String teacher_name;
    private String teacher_surname;
    private Lecture lectures;


    public int getTeacher_id() {
        return teacher_id;
    }
    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }


    public String getTeacher_name() {
        return teacher_name;
    }
    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }


    public String getTeacher_surname() {
        return teacher_surname;
    }
    public void setTeacher_surname(String teacher_surname) {
        this.teacher_surname = teacher_surname;
    }
}
