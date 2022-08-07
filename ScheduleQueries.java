/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static ResultSet resultSet;
    
    //Adds a ScheduleEntry to the database
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            //(All database tables are in app directory)
            //Use a prepared statement to insert the schedule entry into the Schedule table
            addScheduleEntry = connection.prepareStatement("insert into app.Schedule (Semester,CourseCode,StudentID,Status,TimeStamp) values (?,?,?,?,?)");
            //Sets the values to fill in the ?s
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentId());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimeStamp());
            //Execute the update to the table to make changes
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    //Returns the schedule of a student's semester
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester,String studentId)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntries=new ArrayList<>();
 
        try
        {
            //Use a preparedStatement to select a schedule based on the studentId and semester
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where StudentId = ? and Semester = ?");
            //Use setString to fill in the ?
            getScheduleByStudent.setString(1, studentId);
            getScheduleByStudent.setString(2, semester);
            //Get the results from the query
            resultSet = getScheduleByStudent.executeQuery();
            
            //Iterate through the resultSet until it is empty
            while (resultSet.next())
            {
                //Get the results of each row from the table
                String thisSemester=resultSet.getString(1);
                String thisCourseCode=resultSet.getString(2);
                String thisStudentId=resultSet.getString(3);
                String thisStatus=resultSet.getString(4);
                Timestamp thisTime=resultSet.getTimestamp(5);
                //Create a ScheduleEntry object
                ScheduleEntry thisEntry=new ScheduleEntry(thisSemester,thisCourseCode,thisStudentId,thisStatus,thisTime);
                //Add the entry to the arrayList
                scheduleEntries.add(thisEntry);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleEntries;
        
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        //Initialize the number of rows(This will be the return value,it repersents the students the students in the class in the semester)
        int numberOfRows=0;
        try
        {
            //(All database tables are in app directory)
            //Use a prepared statement to count the number of rows given the semester and courseCode
            getScheduledStudentCount = connection.prepareStatement("select * from app.Schedule where Semester = ? and CourseCode = ?");
            //Use setString to fill in the ?
            getScheduledStudentCount.setString(1, semester);
            getScheduledStudentCount.setString(2, courseCode);
            //Get the results from the query
            resultSet = getScheduledStudentCount.executeQuery();
            
            //Iterate through the resultSet until it is empty
            while (resultSet.next())
            {
                //Increment the number of rows by 1
                numberOfRows+=1;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return numberOfRows;
    }
    
    //Reutrns an ArrayList of scheduleEntries of students scheduled in a course
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester,String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> ScheduledStudentsByCourse=new ArrayList<>();
        try
        {
            //Use a prepareStatement to select scheduleEntries based on the semester, courseCode, and a status of S
            getScheduledStudentsByCourse=connection.prepareStatement("select * from app.Schedule where Semester = ? and CourseCode = ? and status = ?");
            //Use setString to fill in the ?
            getScheduledStudentsByCourse.setString(1,semester);
            getScheduledStudentsByCourse.setString(2,courseCode);
            getScheduledStudentsByCourse.setString(3,"S");
            //Get the query
            resultSet=getScheduledStudentsByCourse.executeQuery();
            
            //Iterate through the resultSet until it is empty
            while (resultSet.next())
            {
                //Create a ScheduleEntry object
                ScheduleEntry entry=new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                //Add the entry to the ArrayList
                ScheduledStudentsByCourse.add(entry);
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return ScheduledStudentsByCourse;
    }
    
    //Reutrns an ArrayList of scheduleEntries of students waitlisted in a course
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester,String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudentsByCourse=new ArrayList<>();
        try
        {
            //Use a prepareStatement to select scheduleEntries based on the semester, courseCode, and a status of W order by TimeStamp ascending order
            getWaitlistedStudentsByCourse=connection.prepareStatement("select * from app.Schedule where Semester = ? and CourseCode = ? and status = ? order by TimeStamp asc");
            //Use setString to fill in the ?
            getWaitlistedStudentsByCourse.setString(1,semester);
            getWaitlistedStudentsByCourse.setString(2,courseCode);
            getWaitlistedStudentsByCourse.setString(3,"W");
            //Get the query
            resultSet=getWaitlistedStudentsByCourse.executeQuery();
            
            //Iterate through the resultSet until it is empty
            while (resultSet.next())
            {
                //Create a ScheduleEntry object
                ScheduleEntry entry=new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                //Add the entry to the ArrayList
                waitlistedStudentsByCourse.add(entry);
            }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudentsByCourse;
    }
    
    //Drops a course for a student 
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            //Use a prepareStatement to drop a course for a student in the schedule table
            dropStudentScheduleByCourse=connection.prepareStatement("delete from app.Schedule where Semester = ? and studentID = ? and CourseCode = ?");
            //Use setString to fill the ?
            dropStudentScheduleByCourse.setString(1,semester);
            dropStudentScheduleByCourse.setString(2,studentID);
            dropStudentScheduleByCourse.setString(3,courseCode);
            //Execute the update
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    //Drops schedule entries from the table
    public static void dropScheduleByCourse(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        try
        {
            //Use a prepare statement to delete rows based on the semester and courseCode
            dropScheduleByCourse=connection.prepareStatement("delete from app.Schedule where Semester = ? and CourseCode = ?");
            //Use setString to fill in the ?
            dropScheduleByCourse.setString(1,semester);
            dropScheduleByCourse.setString(2,courseCode);
            //Execute the update
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    //Updates the Schedule table
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) 
    {
        connection = DBConnection.getConnection();
        try
        {
            //Get the courseCode, and id from the entry object
            String entryCourseCode=entry.getCourseCode();
            String entryStudentId=entry.getStudentId();
            
            if (entry.getStatus().equals("S"))
            {
                //Use a prepareStatement to update the status based on the entry
                updateScheduleEntry=connection.prepareStatement("Update app.Schedule set Status = ? where Semester = ? and CourseCode = ? and StudentID = ?");
                //Use setString to fill in the ?
                updateScheduleEntry.setString(1,"W");
            }
            else
            {
                //Use a prepareStatement to update the status based on the entry
                updateScheduleEntry=connection.prepareStatement("Update app.Schedule set Status = ? where Semester = ? and CourseCode = ? and StudentID = ?");
                //Use setString to fill in the ?
                updateScheduleEntry.setString(1,"S");
            }
            //Use setString to fill in the ?
            updateScheduleEntry.setString(2,semester);
            updateScheduleEntry.setString(3,entryCourseCode);
            updateScheduleEntry.setString(4,entryStudentId);
            //Execute the update
            updateScheduleEntry.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}

