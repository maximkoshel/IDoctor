package GUI;

import Classes.Doctor;
import Classes.Patient;
import Classes.VisitLocal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class PatientAppoitmentWithDoctor {


    private JPanel Main;
    private JButton backButton;
    private JTable avaibleTable;
    private JComboBox comboBoxDate;
    private JComboBox comboBoxTime;
    private JButton buttonMakeAppoitment;
    private Patient patient;
    private Doctor doctor;
    private Connection conn;
    private JFrame frame;

    public PatientAppoitmentWithDoctor(){
    }

    public PatientAppoitmentWithDoctor(Patient patient, Connection conn, JFrame frame){
        this.patient=patient;
        this.conn = conn;
        this.frame=frame;
        createListeners();
        setComboBoxDate();
        createInformationTable();
    }


    public static void main(Patient patient, Connection conn) {
        JFrame frame = new JFrame("Main");
        frame.setPreferredSize(new Dimension(1500, 500));
        frame.setContentPane(new PatientAppoitmentWithDoctor(patient, conn, frame).Main);
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

        comboBoxDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createInformationTable();
            }
        });

        buttonMakeAppoitment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAppoitment();
            }
        });
    }

    private void createInformationTable() {

        DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Time"}, 0);
        ResultSet myRs = null;
        PreparedStatement ps = null;
        comboBoxTime.removeAllItems();

        try {
            ps = conn.prepareStatement("SELECT * from zzz? where patientId=0 AND openDate = ?");
            ps.setInt(1,Integer.parseInt(patient.getFamiliyDocotrId()));
            ps.setString(2,String.valueOf(comboBoxDate.getSelectedItem()));
            myRs = ps.executeQuery();

            while (myRs.next()){
                model.addRow(new Object[]{myRs.getString(1),myRs.getString(2)});
                comboBoxTime.addItem(myRs.getString(2));
            }

        }
        catch (Exception exc){exc.printStackTrace();}
        avaibleTable.setModel(model);
    }

    private void setComboBoxDate(){
        LocalDate date = LocalDate.now();
        for(int i=0;i<30;i++) {
            comboBoxDate.addItem(date.toString());
            date = date.plusDays(1);
        }
    }

    private void makeAppoitment(){

        VisitLocal visit = new VisitLocal(conn);
        if(visit.createNewAppoitment(String.valueOf(comboBoxDate.getSelectedItem()),String.valueOf(comboBoxTime.getSelectedItem()),patient)){
            JOptionPane.showMessageDialog(null,"Appointment Made");
            frame.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null,"Problem Accursed");
        }


    }
}

