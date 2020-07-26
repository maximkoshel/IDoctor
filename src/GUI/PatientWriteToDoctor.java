package GUI;

import Classes.Patient;
import Classes.Doctor;
import Classes.VisitOnline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class PatientWriteToDoctor {

    private JLabel labelDoctorsName;
    private JPanel Main;
    private JButton backButton;
    private JTextField message;
    private JButton sendButton;
    private JTable patientMessageTable;
    private Doctor doctor;
    private Patient patient;
    private Connection conn;
    private JFrame frame;
    private VisitOnline visit;

    public PatientWriteToDoctor(){
    }

    public PatientWriteToDoctor(Patient patient, Connection conn, JFrame frame){
        this.patient = patient;
        this.doctor = new Doctor(patient.getFamiliyDocotrId());
        this.conn = conn;
        this.frame = frame;
        labelDoctorsName.setText("Write To "+doctor.getFirstName().concat(" "+doctor.getLastName())+" Your Family Doctor");
        addEventListeners();
        createOnlineTable();
    }

    public static void main(Patient patient, Connection conn) {

        // Creates the sPatient'ss framework
        JFrame frame = new JFrame("Main");
        //frame.setPreferredSize(new Dimension(1500,500));
        frame.setContentPane(new PatientWriteToDoctor(patient, conn,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void addEventListeners(){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOnlineVisit(message.getText());
                frame.dispose();
            }
        });
    }

    private void createOnlineVisit(String message){
        if(validateTextLength(message)){
        visit.createNewMessage(message,patient);
        }
        else{
            JOptionPane.showMessageDialog(null,"Message too long or too short");

        }

    }
    private void createOnlineTable(){
        visit = new VisitOnline(conn);
        patientMessageTable.setModel(visit.getPatientModel(patient));
    }
    public boolean validateTextLength(String message){
        if(message.length()>0 && message.length()<=500)
            return true;
        else return false;

    }
}
