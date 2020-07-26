package GUI;

import Classes.Doctor;
import Classes.Drug;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AllDrugs {
    private JPanel Main;
    private JTable drugsTable;
    private JButton backButton;
    private JTextPane searchComapny;
    private JTextPane searchByBrand;
    private JTextPane searchByName;
    private Connection conn;
    private JFrame frame;
    private Drug drug;
    public AllDrugs(){


    };

    public AllDrugs(Connection conn,JFrame frame){
        this.conn = conn;
        this.frame=frame;
        Drug drug = new Drug(conn);
        this.drug =drug;
        createListeners();
        createDrugTable();

    }

    public static void main(Connection conn) {

        // Creates the Patient's framework
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new AllDrugs(conn,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    private void createDrugTable(){
        drugsTable.setModel(drug.getAllDrugsModel());
    }

    void createListeners(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        searchComapny.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                drugsTable.setModel(drug.getDrugBysearch(1,searchComapny.getText()));

            }
        });
        searchByBrand.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                drugsTable.setModel(drug.getDrugBysearch(2,searchByBrand.getText()));

            }
        });
        searchByName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                drugsTable.setModel(drug.getDrugBysearch(3,searchByName.getText()));

            }
        });
    }
}
