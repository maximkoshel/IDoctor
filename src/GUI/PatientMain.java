package GUI;

import Classes.Patient;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class PatientMain {
    private String id;
    private Connection conn;
    private  boolean type; // True = Doctor
    private JButton meetWithTheFamilyButton;
    private JPanel Main;
    private JButton laboratoriesButton;
    private JButton writeToFamilyDocotorButton;
    private JButton personalInformationButton;
    private JButton regularDrugsButton;
    private Patient patient;

    public PatientMain() {


    }


    public PatientMain(String id, Connection conn) {
        this.patient = new Patient(id);
        this.conn = conn;

        personalInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPersonalDate();
            }
        });

        writeToFamilyDocotorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showVisitOnline();
            }
        });

        laboratoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPublicClinics();
            }
        });
        meetWithTheFamilyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showVisitLocal();
            }
        });
        regularDrugsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegularDrugs();
            }
        });
    }


    public static void main(String id, Connection conn) {

        // Creates the Patient's framework
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new PatientMain(id,conn).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void showPersonalDate(){
        patientPersonal mainPatientPanel = new patientPersonal();
        patientPersonal.main(patient,conn);

    }
    private void showVisitOnline(){
        PatientWriteToDoctor mainPatientWriteToDoctor = new PatientWriteToDoctor();
        mainPatientWriteToDoctor.main(patient,conn);
    }

    private void showPublicClinics(){
        Laboratorie mainPublicClinics = new Laboratorie();
        mainPublicClinics.main(patient,conn);
    }
    private void showVisitLocal(){
        PatientAppoitmentWithDoctor mainPatientAppoitmentWithDoctor = new PatientAppoitmentWithDoctor();
        mainPatientAppoitmentWithDoctor.main(patient,conn);
    }
    private void showRegularDrugs(){
        PatientUsualProcedures mainAppoitmentWithDoctor = new PatientUsualProcedures();
        PatientUsualProcedures.main(patient,conn);
    }

}

