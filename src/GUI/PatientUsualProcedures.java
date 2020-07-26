package GUI;

import Classes.Drug;
import Classes.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class PatientUsualProcedures {
    private JButton backButton;
    private JTable regularDrugsTable;
    private JPanel Main;
    private JComboBox drugIdComboBox;
    private JButton prescribeButton;
    private Patient patient;
    private Connection conn;
    private JFrame frame;
    private Drug drug;

    public PatientUsualProcedures(){

    }


    public PatientUsualProcedures(Patient patient, Connection conn, JFrame frame){
        this.patient = patient;
        this.conn = conn;
        this.frame = frame;
        createListiners();
        createRegularDrugsTable();
    }

    public static void main(Patient patient, Connection conn) {

        JFrame frame = new JFrame("Main");
        frame.setPreferredSize(new Dimension(1000,800));
        frame.setContentPane(new PatientUsualProcedures(patient,conn,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    private void createRegularDrugsTable(){
        Drug drug = new Drug(conn);
        this.drug = drug;
        regularDrugsTable.setModel(drug.getPatientRegularDrugsModel(patient,conn));
        drugIdComboBox.setModel(drug.getComboBox().getModel());


    }

    private void createListiners(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        prescribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet myRs = null;
                myRs = drug.getDrugInformation( drugIdComboBox.getSelectedItem().toString());
                prescribeDrug pres = new prescribeDrug();
                try{
                pres.main(patient.getFirstName(), patient.getLastName(), patient.getId(),myRs.getString("drugCompany"),myRs.getString("drugBrand"),
                        myRs.getString("drugName"),myRs.getString("cost"));}

                catch (Exception exc){
                    exc.printStackTrace(); }

            }
        });
    }
}
