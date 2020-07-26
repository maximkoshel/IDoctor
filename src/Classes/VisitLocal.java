package Classes;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class VisitLocal extends  Visit {

    private String caseId;


    public VisitLocal(Connection conn){
        this.conn = conn;
    }

    // Creates new appointment

     public boolean createNewAppoitment(String date,String time,Patient patient){
         this.patient =patient;
         PreparedStatement ps = null;
         try {
             ps = conn.prepareStatement("UPDATE  zzz? SET patientId = ?  where openDate = ? AND start = ?");
             ps.setInt(1,Integer.parseInt(patient.getFamiliyDocotrId()));
             ps.setString(2, patient.getId());
             ps.setString(3, date);
             ps.setString(4, time);
             ps.executeUpdate();
             return true;
         }
         catch (Exception exc){
             exc.printStackTrace();
             return false;
         }

     }

     // Return resultSet of all patient's appointments
     public ResultSet getPatientsLocalVisitInformation(Patient patient){
         this.patient =patient;
         ResultSet myRs = null;
         PreparedStatement ps =null;

         try {
             ps = conn.prepareStatement("SELECT openDate,start FROM zzz? where patientId = ?");
             ps.setInt(1, Integer.parseInt(patient.getFamiliyDocotrId()));
             ps.setString(2,patient.getId());
             myRs = ps.executeQuery();

         }
         catch (Exception exc){
             exc.printStackTrace();
         }
         return myRs;
     }


    // Return Model of all patient's appointments
    public DefaultTableModel getAllPatientsLocalVisits(Patient patient){
        this.patient =patient;
        ResultSet myRs = getPatientsLocalVisitInformation(patient);
         DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Appointment time"}, 0);

         try {
             while (myRs.next())
                 model.addRow(new Object[]{myRs.getString(1),myRs.getString(2)});
         }
         catch (Exception exc){
             exc.printStackTrace();
         }
         return model;
     }


     // Create new appointment with doctor
     public void createNewLocalVisit(Patient patient, Connection conn,String message,String diagnosis,String procedure){
        this.patient = patient;
        Doctor doctor = new Doctor(patient.getFamiliyDocotrId());

         Timestamp ts = createTimeStamp();
         String timeStamp = ts.toString();
         PreparedStatement ps = null;
         try {
             ps = conn.prepareStatement("INSERT INTO visitLocal(caseId,patientId,doctorId,patientMessage,diagnosis,procedureDone,appoitmentTime,closeOpen) VALUES (?,?,?,?,?,?,?,?)");
             ps.setString(1, patient.getCaseID());
             ps.setString(2, patient.getId());
             ps.setString(3, doctor.getId());
             ps.setString(4, message);
             ps.setString(5, diagnosis);
             ps.setString(6, procedure);
             ps.setString(7, timeStamp);
             ps.setString(8, "CLOSE");
             ps.executeUpdate();
             patient.upOneCaseId();
             JOptionPane.showMessageDialog(null,"Message Sent");

         }
         catch (Exception exc){
             exc.printStackTrace();
         }
     }

     // Returns result set of all doctor's appointments

     public ResultSet getDoctorsSchedualeResultSet(Doctor doctor){
         ResultSet myRs = null;
         PreparedStatement ps =null;

         try {
             ps = conn.prepareStatement("SELECT * from zzz?");
             ps.setInt(1,Integer.parseInt( doctor.getId()));
             myRs = ps.executeQuery();

         }
         catch (Exception exc){
             exc.printStackTrace();
         }
         return myRs;
     }

    // Returns Model of all doctor's appointments

     public DefaultTableModel getDoctorsSchedualeModel(Doctor doctor){
         ResultSet myRs = getDoctorsSchedualeResultSet( doctor);

         DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Appointment time","Patient ID"}, 0);
         try {
             while (myRs.next())
                 model.addRow(new Object[]{myRs.getString(1),myRs.getString(2),myRs.getString(4)});
         }
         catch (Exception exc){
             exc.printStackTrace();
         }
         return model;
     }



}
