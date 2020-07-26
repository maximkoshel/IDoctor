package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class VisitOnline extends Visit {
    private String openOrClose;


    public VisitOnline(Connection conn){
        this.conn = conn;
    }


    // Sends to visitOnline database new message

    public void createNewMessage(String message, Patient patient) {
        this.patient = patient;
        doctor = new Doctor(patient.getFamiliyDocotrId());
        Timestamp ts = createTimeStamp();
        String timeStamp = ts.toString();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO visitOnline(caseId,patientId,doctorId,patientMessage,diagnosis,procedureDone,appoitmentTime,closeOpen) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, patient.getCaseID());
            ps.setString(2, patient.getId());
            ps.setString(3, doctor.getId());
            ps.setString(4, message);
            ps.setString(5, "");
            ps.setString(6, "");
            ps.setString(7, timeStamp);
            ps.setString(8, "OPEN");
            ps.executeUpdate();
            patient.upOneCaseId();
            JOptionPane.showMessageDialog(null,"Message Sent");

        }
        catch (Exception exc){
            exc.printStackTrace();
        }

    }

    // Doctor answer question, update database with answer

    public void answerOnlineQuestion(String caseId, String diagnosis, String procedure, Connection conn){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("UPDATE visitOnline set diagnosis = ?,procedureDone = ?,closeOpen = ? where caseId = ? ");
            ps.setString(1,diagnosis);
            ps.setString(2,procedure);
            ps.setString(3,"CLOSE");
            ps.setString(4,caseId);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Question answered");
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }


    // Makes result set of all patient's online messagess
    public ResultSet getPatientsOnlineResultSet(Patient patient){
        this.patient=patient;
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM visitOnline where patientId = ?");
            ps.setInt(1, Integer.parseInt(patient.getId()));
            myRs = ps.executeQuery();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }

    //Returns table model of all patient's online messages
    public DefaultTableModel getPatientModel(Patient patient){
        this.patient = patient;
        ResultSet myRs = getPatientsOnlineResultSet(patient);
        DefaultTableModel model = new DefaultTableModel(new String[]{"Message", "Diagnosis","Procedure","Message Sent","Case"}, 0);

        try {
            while (myRs.next())
                model.addRow(new Object[]{myRs.getString(4),myRs.getString(5),myRs.getString(6),myRs.getString(7),myRs.getString(8)});
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return model;
    }

    public ResultSet getDoctorsOnlineResultSet(Doctor doctor){
        this.doctor = doctor;
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM visitOnline where doctorId = ?");
            ps.setInt(1, Integer.parseInt(doctor.getId()));
            myRs = ps.executeQuery();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }

    // Return Result set of all unanswered messages

    public ResultSet getDoctorsUnasnweredOnlineResultSet(Doctor doctor){
        this.doctor =doctor;
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM visitOnline where doctorId = ? AND closeOpen = 'OPEN'");
            ps.setInt(1, Integer.parseInt(doctor.getId()));
            myRs = ps.executeQuery();
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return myRs;
    }

    // Return Model set of all unanswered messages

    public DefaultTableModel getDoctorsUnasweredModel(Doctor doctor){
        this.doctor = doctor;
        ResultSet myRs = getDoctorsUnasnweredOnlineResultSet(doctor);
        DefaultTableModel model = new DefaultTableModel(new String[]{"CaseId","Patience ID","Message","Send time"}, 0);
        try {
            while (myRs.next())
                model.addRow(new Object[]{myRs.getString(1),myRs.getString(2),myRs.getString(4),myRs.getString(7)});
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return model;
    }



    // Returns patient Id by getting case ID

    public String getPatientIdBycase(String caseId){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT * FROM visitOnline where caseId = ?");
            ps.setString(1, caseId);
            myRs = ps.executeQuery();
            myRs.next();
            return myRs.getString(2);
        }
        catch (Exception exc){
            exc.printStackTrace();
        }

        return "Error";
    }
}
