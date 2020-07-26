package GUI;

import Classes.Doctor;
import Classes.Patient;
import Classes.VisitLocal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DoctorLocalAnswer {


    private JPanel Main;
    private JButton backButton;
    private JTextPane patientId;
    private JButton enterButton;
    private JTable patientVisitsTable;
    private JTable patientInformationTable;
    private JTextPane patientDiagnosis;
    private JTextPane patientMessage;
    private JTextPane patientProcedure;
    private JButton addNewCaseButton;
    private JButton drugListButton;
    private Doctor doctor;
    private Connection conn;
    private JFrame frame;
    private VisitLocal visitLocal;
    private Patient patient;
    private String caseId;

    public DoctorLocalAnswer(){


    }

    public DoctorLocalAnswer(Doctor doctor, Connection conn, JFrame frame){
        this.doctor=doctor;
        this.conn=conn;
        this.frame=frame;
        createListeners();

    }

    public static void main(Doctor doctor, Connection conn) {

        // Creates the Patient's framework
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new DoctorLocalAnswer(doctor,conn,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    private void createListeners(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patient = new Patient(patientId.getText());
                visitLocal = new VisitLocal(conn);
                populateInformationTable();
                populateVisitTable();
            }
        });

        addNewCaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateTextLength(patientDiagnosis.getText(),patientProcedure.getText()))
                visitLocal.createNewLocalVisit(patient,conn,patientMessage.getText(),patientDiagnosis.getText(),patientProcedure.getText());
            }
        });
        drugListButton.addActionListener(new ActionListener() {
           @Override
            public void actionPerformed(ActionEvent e) {
                DoctorDrugPrescribtion drug = new DoctorDrugPrescribtion();
                drug.main(conn,patient);
            }
        });
    }
    private void populateVisitTable(){
        patientVisitsTable.setModel(patient.getAllPatientVisitInformation());
    }

    private void populateInformationTable(){
        patientInformationTable.setModel(patient.getPatientModel());
    }

    public boolean validateTextLength (String diagnosis, String procedure){
        if(diagnosis.length()>=2 && diagnosis.length() <=500){
            if(procedure.length()>=2 && procedure.length()<=500){
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null,"Procedure too long or too short");
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Diagnosis Procedure too long or too short");
            return false;
        }
    }


}
