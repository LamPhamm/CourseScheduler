
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamph
 */

public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentId;
    private String status;
    private Timestamp timeStamp;

    public ScheduleEntry(String semester, String courseCode, String studentId, String status, Timestamp timeStamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    //Getter method for semester
    public String getSemester() {
        return semester;
    }

    //Getter method for courseCode
    public String getCourseCode() {
        return courseCode;
    }

    //Getter method for studentId
    public String getStudentId() {
        return studentId;
    }

    //Getter method for status
    public String getStatus() {
        return status;
    }

    //Getter method for timeStamp
    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    
    
    
}
