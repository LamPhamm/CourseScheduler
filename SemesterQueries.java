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
public class SemesterQueries {
    private static Connection connection;
    private static PreparedStatement addSemester;
    private static PreparedStatement getSemesterList;
    private static ResultSet resultSet;
    
    //Adds a semester to the database
    public static void addSemester(String name)
    {
        connection = DBConnection.getConnection();
        try
        {
            //(All database tables are in app directory)
            //Use a prepare statement to insert the semester
            addSemester = connection.prepareStatement("insert into app.semester (Name) values (?)");
            //Sets the name of the semester to the first ? mark entry
            addSemester.setString(1, name);
            //Execute the update to the table to make changes
            addSemester.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    //Returns an arrayList of all semesters in the semesters table
    public static ArrayList<String> getSemesterList()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> semesters = new ArrayList<>();
        try
        {
            //Use a prepare statement to select the name column
            getSemesterList = connection.prepareStatement("select name from app.semester");
            //Get the results from the query
            resultSet = getSemesterList.executeQuery();
            
            //Iterate through the resultSet and continue to add the semesters to the arrayList until the resultSet is empty
            while(resultSet.next())
            {
                //There is only one column in the table
                semesters.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return semesters;
        
    }
    
    
}
