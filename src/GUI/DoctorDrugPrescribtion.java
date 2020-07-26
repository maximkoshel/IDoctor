package GUI;

import Classes.Patient;
import Classes.Drug;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DoctorDrugPrescribtion {
    private JPanel Main;
    private JButton backButton;
    private JTable patientDrugTable;
    private JButton drugsListButton;
    private JButton prescribeButton;
    private JTextPane drugIdPane;
    private JCheckBox regularCheckBox;
    private JButton revertButton;
    private JFrame frame;
    private Patient patient;
    private String caseId;
    private Connection conn;
    private Drug drug;
    private String drugId;

    public DoctorDrugPrescribtion(Connection conn, Patient patient, JFrame frame){
        this.frame = frame;
        this.conn = conn;
        this.patient = patient;
        this.caseId = caseId;
        this.drug = new Drug(conn);
        createEventListeners();
        createPatientDrugTable();

    }

    public DoctorDrugPrescribtion() {


    }

    public static void main(Connection conn, Patient patient) {

        // Creates the Patient's framework
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new DoctorDrugPrescribtion(conn,patient,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
    private void createEventListeners(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        drugsListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllDrugs drugs = new AllDrugs();
                drugs.main(conn);
            }
        });

        prescribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    drugId = drugIdPane.getText();
                if(regularCheckBox.isSelected()) {
                    drug.prescribeNewDrug(patient, drugIdPane.getText(), patient.getCaseID(), "YES");

                }
                else
                    drug.prescribeNewDrug(patient,drugIdPane.getText(),patient.getCaseID(),"NO");

                patientDrugTable.setModel(drug.getPatientAllDrugsModel(patient));
            }

        });

        revertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                drug.deleteDrug(caseId,drugId,conn);
                patientDrugTable.setModel(drug.getPatientAllDrugsModel(patient));
            }
        });

    }
    private void createPatientDrugTable(){
        patientDrugTable.setModel(drug.getPatientAllDrugsModel(patient));
    }
}
