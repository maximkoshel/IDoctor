package GUI;

import javax.swing.*;
import Classes.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DoctorMain {
    private JPanel Main;
    private JButton buttonOnline;
    private JButton buttonLocal;
    private JButton visitButton;
    private String id;
    private Connection conn;
    private JFrame frame;
    private Doctor doctor;

    public DoctorMain(){


    }

    public DoctorMain(String id, Connection conn, JFrame frame){
        this.conn = conn;
        this.frame = frame;
        this.id = id;
         doctor = new Doctor(id);
        createListeners();

    }

    void createListeners(){
        buttonOnline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOnlineVisits();
            }
        });
        buttonLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDoctorScheduale();
            }
        });
        visitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createVisitFrame();
            }
        });
    }
    public static void main(String id, Connection conn) {

        // Creates the Patient's framework
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new DoctorMain(id,conn,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    private void createOnlineVisits(){
        DoctorOnlineAnswers doc = new DoctorOnlineAnswers();
        doc.main(doctor,conn);
    }
    private void createDoctorScheduale(){
        DoctorSchedule doc = new DoctorSchedule();
        doc.main(doctor,conn);
    }
    private void createVisitFrame(){
        DoctorLocalAnswer doc = new DoctorLocalAnswer();
        doc.main(doctor,conn);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
