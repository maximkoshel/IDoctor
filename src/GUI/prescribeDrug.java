package GUI;

import Classes.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;

public class prescribeDrug {
    private JLabel stringDrugComapny;
    private JLabel stringDrugBrand;
    private JLabel stringDrugName;
    private JLabel stringDrugCost;
    private JLabel pictureCode;
    private JLabel pictureIdoctor;
    private JLabel pictureSignature;
    private JLabel stringPatientID;
    private JLabel stringFirstName;
    private JLabel stringLastName;
    private JButton xButton;
    private JPanel Main;
    private JButton printButton;
    private String firstName;
    private String lastName;
    private String ID;
    private String drugCompany;
    private String drugBrand;
    private String drugName;
    private String drugCost;
    private JFrame frame;

    public prescribeDrug() {


    }

    public prescribeDrug(String firstName, String lastName, String ID,String drugCompany,String drugBrand,
                         String drugName,String drugCost,JFrame frame) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.drugCompany = drugCompany;
        this.drugBrand = drugBrand;
        this.drugName = drugName;
        this.drugCost = drugCost + "$";

        createUIComponents();
        this.frame = frame;

        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printComponent();
            }
        });
    }
    private void createUIComponents() {
        stringDrugComapny.setText(drugCompany);
        stringDrugBrand.setText(drugBrand);
        stringDrugName.setText(drugName);
        stringDrugCost.setText(drugCost);
        stringPatientID.setText(ID);
        stringFirstName.setText(firstName);
        stringLastName.setText(lastName);


    }

    public static void main(String firstName, String lastName, String ID,String drugCompany,String drugBrand,
                            String drugName,String drugCost) {

        JFrame frame = new JFrame("Main");

        frame.setContentPane(new prescribeDrug( firstName,  lastName,  ID, drugCompany, drugBrand,
                 drugName, drugCost,frame).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void printComponent() {

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");

        pj.setPrintable (new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                if (pageNum > 0) return Printable.NO_SUCH_PAGE;

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                frame.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        });

        if (pj.printDialog() == false) return;

        try {
            pj.print();
        } catch (PrinterException ex) {
            // handle exception
        }
    }
}
