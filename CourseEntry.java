/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamph
 */
public class CourseEntry {
    private String semester;
    private String courseCode;
    private String description;
    private int maxStudents;

    public CourseEntry(String semester, String courseCode, String description, int maxStudents) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.description = description;
        this.maxStudents = maxStudents;
    }

    //Getter method for semester
    public String getSemester() {
        return semester;
    }

    //Getter method for courseCode
    public String getCourseCode() {
        return courseCode;
    }

    //Getter method for description
    public String getDescription() {
        return description;
    }

    //Getter method for maxStudents
    public int getMaxStudents() {
        return maxStudents;
    }

    
    
    
}
