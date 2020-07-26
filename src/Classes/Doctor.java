package Classes;

import jdbmysql.Driver;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor  {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String id;
    private String city;
    private String street;
    private String phone;
    private String jobTitle;
    private String university;
    private Connection conn;

    public Doctor(String id){

        this.id = id;
        Driver myDriver = Driver.getInstance();
        // Establish connection with database
        Connection conn = myDriver.getMyConn();
        this.conn = conn;
        ResultSet myRs = null;
        PreparedStatement ps =null;

        try {
            ps = conn.prepareStatement("SELECT * FROM doctors where id = ?");
            ps.setString(1, id);
            myRs = ps.executeQuery();
            while(myRs.next())
            {
                this.firstName = myRs.getString("first_name");
                this.lastName = myRs.getString("last_name");
                this.email = myRs.getString("email");
                this.gender = myRs.getString("gender");
                this.city = myRs.getString("city");
                this.street = myRs.getString("street");
                this.phone = myRs.getString("phone");
                this.jobTitle = myRs.getString("job_title");
                this.university = myRs.getString("university");
            }
        }

        catch (Exception exc){
            exc.printStackTrace();
        }

    }

    public String getStreet() {
        return street;
    }

    public String getPhone() {
        return phone;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getUniversity() {
        return university;
    }


}

