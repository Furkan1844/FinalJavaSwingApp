package models;

import java.util.List;

public class Lecture{
    private int lecture_code;
    private String lecture_name;
    private int lecture_term;
    private Teacher lecture_teacher = new Teacher();

    public int getLecture_id() {
        return lecture_code;
    }
    public void setLecture_id(int lecture_id) {
        this.lecture_code = lecture_id;
    }


    public String getLectureName(){
        return lecture_name;
    }
    public void setLectureName(String newName){
        this.lecture_name = newName;
    }


    public int getLecture_term() {
        return lecture_term;
    }
    public void setLecture_term(int lecture_term) {
        this.lecture_term = lecture_term;
    }
}
