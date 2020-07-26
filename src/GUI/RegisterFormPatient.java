package GUI;

import Classes.Doctor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterFormPatient extends JFrame {
    private JPanel RegisterPanel;
    private JLabel LogoLabel;
    private JTextField PhoneNumberTextField;
    private JButton RegisterBottom;
    private JTextField IDTextField;
    private JTextField FirstNameTextField;
    private JTextField LastNameTextField;
    private JTextField EmailTextField;
    private JTextField CityTextField;
    private JTextField StreetTextField;
    private JTextField PasswordTextField;
    private JComboBox GenderComboBox;
    private JButton backButton;
    private JComboBox billOptionComboBox;
    private JComboBox doctorIDcomboBox;
    private JTable doctorDataTable;
    private Border errorBorder = new BorderUIResource.LineBorderUIResource(Color.red);
    private Border validBorder = new BorderUIResource.LineBorderUIResource(Color.green);
    private JFrame frame;
    private Connection conn;


    public RegisterFormPatient(){
    };

    public RegisterFormPatient(Connection conn, JFrame frame){
        super("Register");
        this.setContentPane(this.RegisterPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.conn = conn;
        this.frame = frame;
        initialize();

        RegisterBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateInput())
                    addClient();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login loginPanel = new Login();
                loginPanel.main(null);
            }
        });

        doctorIDcomboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillDoctorsDataTable();
            }
        });
    }
    private void initialize(){

        // Adding combobox options
        GenderComboBox.addItem("Male");
        GenderComboBox.addItem("Female");
        billOptionComboBox.addItem("Email");
        billOptionComboBox.addItem("Mail");
        intializeDoctorsIDComboBox();
    }

    //If everything passed the tests add new client to database
    private void addClient(){
        // Add to database
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO patients(id,first_name,last_name,email,gender,city,street,phone,bill_option,family_docotor_id,total_cost,online_case_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, IDTextField.getText());
            ps.setString(2, FirstNameTextField.getText());
            ps.setString(3, LastNameTextField.getText());
            ps.setString(4, EmailTextField.getText());
            ps.setString(5, GenderComboBox.getSelectedItem().toString());
            ps.setString(6, CityTextField.getText());
            ps.setString(7, StreetTextField.getText());
            ps.setString(8, PhoneNumberTextField.getText());
            ps.setString(9, billOptionComboBox.getSelectedItem().toString());
            ps.setString(10, doctorIDcomboBox.getSelectedItem().toString());
            ps.setString(11, "0");
            ps.setString(12, "0");
            ps.executeUpdate();

            ps = conn.prepareStatement("INSERT INTO loginPatient(id,password) VALUES (?,?)");
            ps.setString(1, IDTextField.getText());
            ps.setString(2, PasswordTextField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"User Added");
            Login loginPanel = new Login();
            loginPanel.main(null);
            frame.dispose();
        }
        catch (Exception exc){
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null,"Enter unique ID");

        }
    }

    private boolean validateInput(){
        if(validateId(IDTextField.getText().toString()) && validatePassword(PasswordTextField.getText().toString()) && validateName(FirstNameTextField.getText().toString(),LastNameTextField.getText().toString())
                && validatePhoneNumber(PhoneNumberTextField.getText().toString()) && validateEmail(EmailTextField.getText().toString())&&validateCity(CityTextField.getText().toString())&&validateStreet(StreetTextField.getText().toString()))
            return true;
        else {
            JOptionPane.showMessageDialog(null,"Not filled right");
            return false;
        }
    }

    public boolean validateId(String id){

        boolean isValid = id.length() == 9;
        if(isValid){
            for (int i = 0; i < id.length() && isValid; i++){
                isValid = Character.isDigit(id.charAt(i));
            }
        }
        if(!isValid)
            IDTextField.setBorder(errorBorder);
        else IDTextField.setBorder(validBorder);

        return isValid;
    }

    public boolean validatePassword(String password){
        boolean isValid = password.length() >= 10 ;
        byte countDigit = 0;

        if(isValid) {

            for (int i = 0; i < password.length(); i++) {

                if(Character.isDigit(password.charAt(i))){
                    countDigit++;
                }
                else if (!Character.isAlphabetic(password.charAt(i))){
                    isValid = false;
                    break;
                }
            }
        }

        if(!isValid){
            PasswordTextField.setBorder(errorBorder);
        }
        else{
            PasswordTextField.setBorder(validBorder);
        }

        isValid = countDigit > 0 && countDigit < password.length();

        return isValid;
    }

    public boolean validateName(String firstName,String lastName){

        boolean isValid =  firstName.length() > 1  && firstName.length()<=100 && lastName.length()<=100 &&lastName.length() > 1;

        if(isValid){
            for (int i = 0; i < firstName.length(); i++){
                if(Character.isDigit(firstName.charAt(i))){
                    isValid = false;
                    break;
                }
            }
        }
        if(!isValid){
            FirstNameTextField.setBorder(errorBorder);
        }
        else{
            FirstNameTextField.setBorder(validBorder);
        }
        if(isValid){
            for (int i = 0; i < lastName.length(); i++){
                if(Character.isDigit(lastName.charAt(i))){
                    isValid = false;
                    break;
                }
            }
        }

        if(!isValid){
            LastNameTextField.setBorder(errorBorder);
        }
        else
            LastNameTextField.setBorder(validBorder);

        return isValid;
    }

    public boolean validatePhoneNumber(String phoneNumber){

        boolean isValid =  phoneNumber.length() == 10;

        if(isValid){
            for (int i = 0; i < phoneNumber.length() && isValid; i++){
                isValid = Character.isDigit(phoneNumber.charAt(i));
            }
        }

        if(!isValid){
            PhoneNumberTextField.setBorder(errorBorder);
        }
        else{
            PhoneNumberTextField.setBorder(validBorder);
        }

        return isValid;
    }

    public boolean validateEmail(String email){
        int countStrudel = 0;
        int countDot = 0;
        if(email.length() > 5 && email.length()<=100){
            for (int i = 0; i < email.length(); i++){
                if(email.charAt(i) == '@'){
                    countStrudel++;
                }
                else if(email.charAt(i) == '.'){
                    countDot++;
                }
            }
        }

        if(countDot == 1 && countStrudel == 1){
            EmailTextField.setBorder(validBorder);
            return true;
        }
        else{
            EmailTextField.setBorder(errorBorder);
            return false;
        }
    }

    public boolean validateCity(String city){
        boolean isValid = city.length()>=3 && city.length()<=100;
        for(int i = 0; i< city.length() && isValid;i++){
            isValid = Character.isAlphabetic(city.charAt(i));
        }

        return isValid;
    }

    public boolean validateStreet(String street){

       boolean isValid = street.length()>=3 && street.length()<=100;

       for (int i =0; i< street.length() && isValid;i++) {
           isValid = Character.isAlphabetic(street.charAt(i));
       }

       if(isValid)
        StreetTextField.setBorder(validBorder);
        else
        StreetTextField.setBorder(errorBorder);

        return isValid;
    }

    public static void main(Connection conn){
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new RegisterFormPatient(conn, frame).rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
    private void fillDoctorsDataTable(){
        DefaultTableModel model = new DefaultTableModel(new String[]{ "First Name", "Last Name", "Email", "Gender",  "Phone", "Speciality",}, 0);
        Doctor doctor = new Doctor(doctorIDcomboBox.getSelectedItem().toString());
        model.addRow(new Object[]{doctor.getFirstName(),doctor.getLastName(),doctor.getEmail(),doctor.getGender(),doctor.getPhone(),doctor.getJobTitle()});
        doctorDataTable.setModel(model);
    }
    private void intializeDoctorsIDComboBox(){
        ResultSet myRs = null;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement("SELECT id FROM doctors");
            myRs = ps.executeQuery();
            while (myRs.next())
            {
                doctorIDcomboBox.addItem(myRs.getString(1));
            }
            fillDoctorsDataTable();

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }
}
