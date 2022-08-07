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
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudentList;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    //Adds a student to the database
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            //(All database tables are in app directory)
            //Use a prepareStatement to insert the student
            addStudent = connection.prepareStatement("insert into app.Student (StudentID,FirstName,LastName) values (?,?,?)");
            //Sets the values of the course attributes to the ? mark entries
            addStudent.setString(1, student.getId());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            //Execute the update to the table to make changes
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    //Returns an arrayList of all students in the Student table
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            //Get all of the students from the table
            getStudentList = connection.prepareStatement("select * from app.Student");
            //Get the results from the query
            resultSet = getStudentList.executeQuery();
            
            //Iterate through the resultSet and continue to add the students to the arrayList until the resultSet is empty
            while(resultSet.next())
            {
                //Add a new studentEntry object to the courses arrayList based on the columns of the resultSet
                students.add(new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
    
    //Gets a student in the Student table
    public static StudentEntry getStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        StudentEntry student=new StudentEntry(null,null,null);
        try
        {
            //Use a prepareStatement to select the student with the studentID
            getStudent=connection.prepareStatement("select * from app.Student where studentID = ?");
            //Use setString to fill in the ?
            getStudent.setString(1,studentID);
            //Get the results from the query
            resultSet=getStudent.executeQuery();
            //Move to the first line of the query
            resultSet.next();
            //Modify the student's attributes
            student.setId(resultSet.getString(1));
            student.setFirstName(resultSet.getString(2));
            student.setLastName(resultSet.getString(3));
           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
    }
    
    //Drops a student in the Student table
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try
        {
            //Use a prepareStatement to delete a student in the Student table based on the studentID
            dropStudent=connection.prepareStatement("delete from app.Student where studentID = ?");
            //Use setString to fill in the ?
            dropStudent.setString(1,studentID);
            //Execute the update
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
}

