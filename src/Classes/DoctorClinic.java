package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbmysql.Driver;
public class DoctorClinic {

    String doctorId;
    String city;
    String street;
    String phone;
    String open;
    String close;
    Connection conn;

    public DoctorClinic(String doctorId){
        this.doctorId = doctorId;
        Driver myDriver = Driver.getInstance();
        // Establish connection with database
        Connection conn = myDriver.getMyConn();
        this.conn = conn;

        ResultSet myRs = null;
        PreparedStatement ps =null;

        try {
            ps = conn.prepareStatement("SELECT * FROM docotrsClinic where id = ?");
            ps.setString(1, doctorId);
            myRs = ps.executeQuery();
            while(myRs.next())
            {
                this.city = myRs.getString("city");
                this.street = myRs.getString("street");
                this.phone = myRs.getString("phone");
                this.open = myRs.getString("openHour");
                this.close = myRs.getString("closeHour");
            }
        }

        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getClose() {
        return close;
    }

    public String getOpen() {
        return open;
    }
}
