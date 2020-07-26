package Classes;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

public class Drug {
    private Connection conn;
    private JComboBox comboBox;


    public Drug(Connection conn){
        this.conn = conn;
    }

    public ResultSet getAllResultSet(){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM drugs ");
            myRs = ps.executeQuery();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }
    public ResultSet getResultsetByCompany(String letters){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM drugs WHERE drugCompany LIKE ?");
            ps.setString(1,"%"+letters+"%");
            myRs = ps.executeQuery();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }
    public ResultSet getResultsetByBrand(String letters){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM drugs WHERE drugBrand LIKE ?");
            ps.setString(1,"%"+letters+"%");
            myRs = ps.executeQuery();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }
    public ResultSet getResultByName(String letters){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM drugs WHERE drugName LIKE ?");
            ps.setString(1,"%"+letters+"%");
            myRs = ps.executeQuery();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }

    public DefaultTableModel getAllDrugsModel(){
        ResultSet myRs = getAllResultSet();
        DefaultTableModel model = new DefaultTableModel(new String[]{"drug Id","Drug company","Drug Brand","Drug Name","Cost"}, 0);
        try {
            while (myRs.next())
                model.addRow(new Object[]{myRs.getString(1),myRs.getString(2),myRs.getString(3),myRs.getString(4),myRs.getString(5)});
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return model;
    }

    public DefaultTableModel getDrugBysearch(int i,String letters){

        ResultSet myRs = getResultsetByCompany(letters);

        if(i==1)
             myRs = getResultsetByCompany(letters);
        if(i==2)
            myRs = getResultsetByBrand(letters);
        if(i==3)
            myRs = getResultByName(letters);

        DefaultTableModel model = new DefaultTableModel(new String[]{"drug Id","Drug company","Drug Brand","Drug Name","Cost"}, 0);
        try {
            while (myRs.next())
                model.addRow(new Object[]{myRs.getString(1),myRs.getString(2),myRs.getString(3),myRs.getString(4),myRs.getString(5)});
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return model;
    }

    public ResultSet getAllPatientDrugs(Patient patient,Connection conn){
        ResultSet myRs = null;

        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM patientDrugs WHERE patientId = ?");
            ps.setString(1,patient.getId());
            myRs = ps.executeQuery();

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }

    public DefaultTableModel getPatientAllDrugsModel(Patient patient){
        ResultSet myRs = getAllPatientDrugs(patient,conn);
        ResultSet rsDrugs = null;
        PreparedStatement ps =null;

        DefaultTableModel model = new DefaultTableModel(new String[]{"Drug Id","Drug Company","Drug Brand","Drug Name","Regular","Date"}, 0);
        try {
            while (myRs.next())
                try{

                ps = conn.prepareStatement("SELECT * FROM drugs WHERE drugId = ?");
                ps.setString(1,myRs.getString(1));
                rsDrugs =ps.executeQuery();
                rsDrugs.next();
                model.addRow(new Object[]{rsDrugs.getString(1),rsDrugs.getString(2),rsDrugs.getString(3),rsDrugs.getString(4),myRs.getString(5),myRs.getString(6)});
                }
            catch (Exception exc){ exc.printStackTrace();}
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return model;
    }


    public void prescribeNewDrug(Patient patient,String drugId,String caseId,String regular){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        try {
            ps = conn.prepareStatement("insert into patientDrugs (drugId,caseId,patientId,paid,regular,time) VALUES (?,?,?,'NO',?,?)");
            ps.setString(1,drugId);
            ps.setString(2,caseId);
            ps.setString(3,patient.getId());
            ps.setString(4,regular);
            ps.setString(5,ts.toString());
            ps.executeUpdate();

        }
        catch (Exception exc){
            exc.printStackTrace();
        }

    }


    public ResultSet getPatientRegularDrugs(Patient patient,Connection conn){
        ResultSet myRs = null;

        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM patientDrugs WHERE patientId = ? and regular = 'YES' ");
            ps.setString(1,patient.getId());
            myRs = ps.executeQuery();

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }


    public DefaultTableModel getPatientRegularDrugsModel(Patient patient,Connection conn){
        comboBox = new JComboBox();

        ResultSet myRs = getPatientRegularDrugs(patient,conn);
        ResultSet rsDrugs = null;
        PreparedStatement ps =null;

        DefaultTableModel model = new DefaultTableModel(new String[]{"Drug Id","Drug Company","Drug Brand","Drug Name"}, 0);
        try {
            while (myRs.next())
                try{

                    ps = conn.prepareStatement("SELECT * FROM drugs WHERE drugId = ?");
                    comboBox.addItem((myRs.getString(1)));
                    ps.setString(1,myRs.getString(1));
                    rsDrugs =ps.executeQuery();
                    rsDrugs.next();
                    model.addRow(new Object[]{rsDrugs.getString(1),rsDrugs.getString(2),rsDrugs.getString(3),rsDrugs.getString(4),myRs.getString(5)});
                }
                catch (Exception exc){ exc.printStackTrace();}
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return model;
    }

    public void deleteDrug(String caseId,String drugId,Connection conn){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("DELETE FROM patientDrugs WHERE caseId=? and drugId = ?  ");

            ps.setString(1,caseId);
            ps.setString(2,drugId);
            ps.executeUpdate();

        }
        catch (Exception exc){
            exc.printStackTrace();
        }

    }
    public ResultSet getDrugInformation(String id){
        ResultSet myRs = null;
        PreparedStatement  ps =null;

        try {
            ps = conn.prepareStatement("SELECT * FROM drugs WHERE drugId=?");
            ps.setString(1,id);
            myRs = ps.executeQuery();
            myRs.next();
            return myRs;

        }
        catch (Exception exc){
            exc.printStackTrace();
            return null;
        }

    }



    public JComboBox getComboBox() {
        return this.comboBox;
    }
}
