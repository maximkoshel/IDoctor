package GUI;

import Classes.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Laboratorie {

    private JPanel Main;
    private JButton backButton;
    private JTable clinicTable;
    private Connection conn;
    private Patient patient;
    private JFrame frame;

    public Laboratorie() {

    }

    public Laboratorie(Patient patient, Connection conn, JFrame frame) {
        this.patient = patient;
        this.conn = conn;
        this.frame = frame;

        addListeners();
        createInformationTable();

    }

    public static void main(Patient patient, Connection conn) {

        // Creates the sPatient'ss framework
        JFrame frame = new JFrame("Main");
        frame.setPreferredSize(new Dimension(1500, 500));
        frame.setContentPane(new Laboratorie(patient, conn, frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    private void addListeners() {


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }


    private void createInformationTable() {

        DefaultTableModel model = new DefaultTableModel(new String[]{"Clinic Id", "City", "Street", "Clinic opens", "Clinic closes", "Procedures"}, 0);

        ResultSet myRs = null;
        PreparedStatement ps = null;

        try {

                ps = conn.prepareStatement("SELECT * from publicClinic ");
                myRs = ps.executeQuery();

                while (myRs.next()){
                    model.addRow(new Object[]{myRs.getString(1),myRs.getString(2),myRs.getString(3),
                            myRs.getString(4),myRs.getString(5),myRs.getString(6)});
                }

        }
        catch (Exception exc){exc.printStackTrace();}



        clinicTable.setModel(model);
    }
}

