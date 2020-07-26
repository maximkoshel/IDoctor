package Classes;

import jdbmysql.Driver;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Patient {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String id;
    private String city;
    private String street;
    private String phone;
    private String billOption;
    private String familiyDocotrId;
    private String billCost;
    private String onlineCaseId;
    private Connection conn;
    private String caseID;


    public Patient(String id){
        this.id = id;
        Driver myDriver = Driver.getInstance();
        // Establish connection with database
        Connection conn = myDriver.getMyConn();
        this.conn = conn;
        ResultSet myRs = null;
        PreparedStatement ps =null;

        try {
            ps = conn.prepareStatement("SELECT * FROM patients where id = ?");
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
                this.billOption = myRs.getString("bill_option");
                this.familiyDocotrId = myRs.getString("family_docotor_id");
                this.billCost= myRs.getString("total_cost");
                this.onlineCaseId = myRs.getString("online_case_id");

            }
        }

        catch (Exception exc){
            exc.printStackTrace();
        }

    }


    public String getId() {
        return id;
    }

    public String getBillCost() {
        return billCost;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getBillOption() {
        return billOption;
    }

    public String getFamiliyDocotrId() {
        return familiyDocotrId;
    }

    public String getOnlineCaseId() {
        return onlineCaseId;
    }

    public void upOneCaseId(){

        int caseint = Integer.parseInt(onlineCaseId);
        caseint +=1;
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("UPDATE  patients SET online_case_id = (?) where id = ?");
            ps.setInt(1,caseint);
            ps.setString(2,getId());
            ps.executeUpdate();
            this.caseID = Integer.toString(caseint);

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public String getCaseID() {
        this.caseID =  id.concat(getOnlineCaseId());
        return caseID;
    }

    public DefaultTableModel getPatientModel(){

        DefaultTableModel model = new DefaultTableModel(new String[]{"Id", "First Name", "Last Name", "Email", "Gender", "City", "Street", "Phone", "Bill Option", "Payment"}, 0);


        model.addRow(new Object[]{id,firstName,lastName,
                email,gender,city, street,
                phone, billOption, billCost});

        return model;
    }

    public DefaultTableModel getAllPatientVisitInformation(){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        DefaultTableModel model = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM (SELECT * from visitOnline UNION SELECT * from visitLocal) as la where patientId = ? order by appoitmentTime ");
            ps.setString(1, id);
            myRs = ps.executeQuery();
             model = new DefaultTableModel(new String[]{ "Patient Id", "Patient Message", "Diagnosis", "Procedure Done","Appointment time"}, 0);
            while(myRs.next())
            {
                model.addRow(new Object[]{myRs.getString(2),myRs.getString(4),
                        myRs.getString(5),myRs.getString(6),myRs.getString(7)});

            }
        }

        catch (Exception exc){
            exc.printStackTrace();
        }

        return model;
    }

    public void changeBillingMethod() {
        PreparedStatement ps = null;
        if (billOption == "MAIL") {
            try {
                ps = conn.prepareStatement("UPDATE patients set bill_option = 'EMAIL' where id = ?");
                ps.setString(1, id);
                ps.executeUpdate();
                billOption = "EMAIL";
            } catch (Exception exc) {
                exc.printStackTrace();
            }

        }
        else {
            try {
                ps = conn.prepareStatement("UPDATE patients set bill_option = 'MAIL' where id =?");
                ps.setString(1, id);
                ps.executeUpdate();
                billOption = "MAIL";
            } catch (Exception exc) {
                exc.printStackTrace();

            }
        }
    }

}
