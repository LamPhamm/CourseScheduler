/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
 
    
    //Adds a Course to the database
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            //(All database tables are in app directory)
            //Use a prepared statement to insert the date into the Course table
            addCourse = connection.prepareStatement("insert into app.Course (Semester, CourseCode, Description, Seats) values (?,?,?,?)");
            
            //Sets the values of the course attributes to the ? mark entries
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getMaxStudents());
            
            //Execute the update to the table to make changes
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
 
    //Returns an arrayList of all courses for a single semester
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<>();
        try
        {
            //Use a prepareStatement to get the the courses in the semester
            getCourseList = connection.prepareStatement("select * from app.Course where Semester = ?");
            //Sets the ? to be the semester
            getCourseList.setString(1,semester);
            //Get the results from the query
            resultSet = getCourseList.executeQuery();
            
            //Iterate through the resultSet and continue to add the courses to the arrayList until the resultSet is empty
            while(resultSet.next())
            {
                //Add a new CourseEntry object to the courses arrayList based on the columns of the resultSet
                courses.add(new CourseEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
    
    //Returns an arrayList of all courseCodes in a single semester
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            //Use a prepareStatement go select the coursecodes from the table based on the semester
            getCourseCodes = connection.prepareStatement("select CourseCode from app.Course where Semester = ?");
            //Sets the ? to be the semester
            getCourseCodes.setString(1,semester);
            //Get the results from the query
            resultSet = getCourseCodes.executeQuery();
            
            //Iterate through the resultSet and continue to add the courses to the arrayList until the resultSet is empty
            while(resultSet.next())
            {
                //Adds the courseCode string to the arrayList and MaxStudents is in entry 1 in the query
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    //Returns the number of courseSeats in a given semester in a course
    public static int getCourseSeats(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
       //Initialize the seats variable so it can be returned
        int seats=0;
        try
        {
            //Use a prepareStatement to get the seats from the Course table based on the courseCode and Semester
            getCourseSeats = connection.prepareStatement("select Seats from app.Course where CourseCode = ? and Semester = ?");
            //Use setString to fill in the ?
            getCourseSeats.setString(1, courseCode);
            getCourseSeats.setString(2, semester);
            //Get the results from the query
            resultSet = getCourseSeats.executeQuery();
            //Get the number of seats from the resultSet
            resultSet.next(); //Go to the first row of the resultSet
            seats=resultSet.getInt(1);
   
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
    }
    
    //Drops a course from the table
    public static void dropCourse(String semester,String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            //Use a prepareStatement to delete a course from the Course table based on the semester and courseCode
            dropCourse=connection.prepareStatement("delete from app.Course where Semester = ? and CourseCode = ?");
            //Use setString to fill in the ?
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            //Execute the update
            dropCourse.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
}


