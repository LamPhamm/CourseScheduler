/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamph
 */
public class StudentEntry {
    private String id;
    private String firstName;
    private String lastName;
    
    public StudentEntry(String id,String firstName,String lastName)
    {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
    }

    //Getter method for id
    public String getId() {
        return id;
    }

    //Getter method for firstName
    public String getFirstName() {
        return firstName;
    }

    //Getter method for lastName
    public String getLastName() {
        return lastName;
    }

    //Setter method for id
    public void setId(String id) {
        this.id = id;
    }
    
    //Setter method for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //Setter method for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    

    @Override
    public String toString() {
        return firstName + " " + lastName + ": " + id;
    }
    
    

    
    
    
}
