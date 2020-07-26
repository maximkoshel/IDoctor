package GUI;

import Classes.Doctor;
import Classes.Patient;
import Classes.VisitOnline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DoctorOnlineAnswers {


    private JPanel Main;
    private JButton backButton;
    private JTable unansweredTable;
    private JButton showDrugsListButton;
    private JTextPane caseIdPanel;
    private JButton enterButton;
    private JTable patientInformation;
    private JTable patientAllVisits;
    private JTextPane diagnosisPane;
    private JTextPane procedurePane;
    private JButton answerButton;
    private Doctor doctor;
    private Connection conn;
    private JFrame frame;
    private  String caseId;
    private Patient patient;
    private VisitOnline visit;

    public DoctorOnlineAnswers(){


    }

    public DoctorOnlineAnswers(Doctor doctor, Connection conn, JFrame frame){
        this.doctor=doctor;
        this.conn=conn;
        this.frame=frame;
        createListeners();
        createUnasweredTable();
    }

    public static void main(Doctor doctor, Connection conn) {

        // Creates the Patient's framework
        JFrame frame = new JFrame("Main");
        frame.setVisible(true);
        frame.setContentPane(new DoctorOnlineAnswers(doctor,conn,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

    }
    private void createUnasweredTable(){
        VisitOnline visit = new VisitOnline(conn);
        unansweredTable.setModel(visit.getDoctorsUnasweredModel(doctor));
    }
    private void createListeners(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        showDrugsListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoctorDrugPrescribtion drug = new DoctorDrugPrescribtion();
                DoctorDrugPrescribtion.main(conn,patient);
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caseId = caseIdPanel.getText();
                 visit = new VisitOnline(conn);
                patient = new Patient(visit.getPatientIdBycase(caseId));
                createInformationTable();
                createAllVisitTable();
            }
        });

        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateTextLength(diagnosisPane.getText(),procedurePane.getText())) {
                    visit.answerOnlineQuestion(caseId, diagnosisPane.getText(), procedurePane.getText(), conn);
                    createInformationTable();
                    createAllVisitTable();
                    unansweredTable.setModel(visit.getDoctorsUnasweredModel(doctor));
                    caseIdPanel.setText("");
                    diagnosisPane.setText("");
                    procedurePane.setText("");
                }

            }
        });
    }
    private void createInformationTable(){
        patientInformation.setModel(patient.getPatientModel());
    }

    private void createAllVisitTable(){
        patientAllVisits.setModel(patient.getAllPatientVisitInformation());
    }

    public boolean validateTextLength (String diagnosis, String procedure){
        if(diagnosis.length()>=2 && diagnosis.length() <=500){
            if(procedure.length()>=2 && procedure.length()<=500){
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null,"Procedure too short or too long");
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Diagnosis too short or too long");
            return false;
        }
    }
}
