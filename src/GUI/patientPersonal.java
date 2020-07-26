package GUI;

import Classes.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class patientPersonal {
    private JPanel Main;
    private JTable personalDataTable;
    private JButton backButton;
    private JTable doctorsDataTable;
    private JTable appoitmentsTable;
    private JTable onlineVisitTable;
    private JButton changeBillingOptionButton;
    private JTable drugsTable;
    private Connection conn;
    private Patient patient;
    private JFrame frame;


    public  patientPersonal(){

    }

    public patientPersonal(Patient patient, Connection conn,JFrame frame) {
        this.conn = conn;
        this.frame = frame;
        this.patient = patient;

        createEventListeners();
        createMineInformationTable();
        createDoctorsInformationTable();
        createAppoitmentTable();
        createOnlineTable();
        createDrugTable();
    }

    public static void main(Patient patient,Connection conn) {

        // Creates the sPatient'ss framework
        JFrame frame = new JFrame("Main");
        frame.setPreferredSize(new Dimension(1000,800));
        frame.setContentPane(new patientPersonal(patient,conn,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    private void createMineInformationTable() {
        personalDataTable.setModel(patient.getPatientModel());
    }


    private void  createDoctorsInformationTable(){
        Doctor doctor = new Doctor(patient.getFamiliyDocotrId());
        DoctorClinic clinic = new DoctorClinic(doctor.getId());
        DefaultTableModel model = new DefaultTableModel(new String[]{"First Name", "Last Name", "Email","City","Street","Phone","Opens","Closing"}, 0);
        model.addRow(new Object[]{doctor.getFirstName(),doctor.getLastName(),doctor.getEmail(),clinic.getCity(),clinic.getStreet(),clinic.getPhone(),clinic.getOpen(),clinic.getClose()});
        doctorsDataTable.setModel(model);
    }

    private void createAppoitmentTable(){
        VisitLocal visit = new VisitLocal(conn);
        appoitmentsTable.setModel(visit.getAllPatientsLocalVisits(patient));
    }

    private void createOnlineTable(){
        onlineVisitTable.setModel(patient.getAllPatientVisitInformation());
    }

    private void createDrugTable(){
        Drug drug = new Drug(conn);
        drugsTable.setModel(drug.getPatientAllDrugsModel(patient));
    }

    private void createEventListeners(){
        // Add listener for closing window
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        changeBillingOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            patient.changeBillingMethod();
            createMineInformationTable();
            }
        });

    }


}

