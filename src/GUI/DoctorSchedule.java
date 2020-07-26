package GUI;

import Classes.Doctor;
import Classes.VisitLocal;

import javax.print.Doc;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DoctorSchedule {


    private JPanel Main;
    private JButton backButton;
    private JTable schedualeTable;
    private Doctor doctor;
    private Connection conn;
    private JFrame frame;

    public DoctorSchedule(){

    }

    public DoctorSchedule(Doctor doctor,Connection conn,JFrame frame){
        this.doctor=doctor;
        this.conn=conn;
        this.frame=frame;
        createListeners();
        VisitLocal visit = new VisitLocal(conn);
        schedualeTable.setModel(visit.getDoctorsSchedualeModel(doctor));
    }

    public static void main(Doctor doctor, Connection conn) {

        // Creates the Patient's framework
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new DoctorSchedule(doctor,conn,frame).Main);
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
    }

}
