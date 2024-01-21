package models;

public class Student{
    private int student_id;
    private String student_name;
    private String student_surname;
    private int student_section;
    private Lecture student_lecture = new Lecture();


    public int getStudent_id() {
        return student_id;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }


    public String getStudent_name() {
        return student_name;
    }
    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }


    public String getStudent_surname() {
        return student_surname;
    }
    public void setStudent_surname(String student_surname) {
        this.student_surname = student_surname;
    }


    public int getStudent_section() {
        return student_section;
    }
    public void setStudent_section(int student_section) {
        this.student_section = student_section;
    }
}
