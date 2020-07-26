package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import Classes.AppoitmentSystemDoctors;

public class RegisterFormDoctor extends JFrame {
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
    private JTextField UniversityTextField;
    private JTextField PasswordTextField;
    private JComboBox GenderComboBox;
    private JButton backButton;
    private JTextField clinicCityTextField;
    private JTextField clinicStreetTextField;
    private JTextField clinicPhoneTextField;
    private JComboBox clinicOpeningHoursComboBox;
    private JComboBox clinicClosingHoursComboBox;
    private Border errorBorder = new BorderUIResource.LineBorderUIResource(Color.red);
    private Border validBorder = new BorderUIResource.LineBorderUIResource(Color.green);
    private JFrame frame;
    private Connection conn;


    public RegisterFormDoctor(){};

    public RegisterFormDoctor(Connection conn, JFrame frame){
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
    }
    private void initialize(){
        // Add cities/Jobs/University from DB
        GenderComboBox.addItem("Male");
        GenderComboBox.addItem("Female");
        initializeHoursComboBox();
    }
    private void addClient(){
        // Add to database
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO doctors(id,first_name,last_name,email,gender,city,street,phone,job_title,university) VALUES (?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, IDTextField.getText().toString());
            ps.setString(2, FirstNameTextField.getText().toString());
            ps.setString(3, LastNameTextField.getText().toString());
            ps.setString(4, EmailTextField.getText().toString());
            ps.setString(5, GenderComboBox.getSelectedItem().toString());
            ps.setString(6, CityTextField.getText().toString());
            ps.setString(7, StreetTextField.getText().toString());
            ps.setString(8, PhoneNumberTextField.getText().toString());
            ps.setString(9, "Family Doctor");
            ps.setString(10, UniversityTextField.getText().toString());
            ps.executeUpdate();

            ps = conn.prepareStatement("INSERT INTO loginDoctor(id,password) VALUES (?,?)");
            ps.setString(1, IDTextField.getText().toString());
            ps.setString(2, PasswordTextField.getText().toString());
            ps.executeUpdate();

            ps = conn.prepareStatement("INSERT INTO docotrsClinic(id,city,street,phone,openHour,closeHour) VALUES (?,?,?,?,?,?)");
            ps.setString(1,IDTextField.getText().toString());
            ps.setString(2,clinicCityTextField.getText().toString());
            ps.setString(3,clinicStreetTextField.getText().toString());
            ps.setString(4,clinicPhoneTextField.getText().toString());
            ps.setString(5,clinicOpeningHoursComboBox.getSelectedItem().toString());
            ps.setString(6,clinicClosingHoursComboBox.getSelectedItem().toString());
            ps.executeUpdate();

            createDoctorsScheduale();
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
    // Creates new table in database for this specific doctor scheduale
    private void createDoctorsScheduale(){
        JOptionPane.showMessageDialog(frame,
                "We are creating your new schedule clinic hours please wait until we will finish," + "\n" +
                        "we will notify when we are done don't worry it's not stuck, please wait this process will take few minutes",
                "Please Wait",
                JOptionPane.WARNING_MESSAGE);
        AppoitmentSystemDoctors sys = new AppoitmentSystemDoctors(IDTextField.getText().toString(),clinicOpeningHoursComboBox.getSelectedItem().toString(),clinicClosingHoursComboBox.getSelectedItem().toString(),conn);
        JOptionPane.showMessageDialog(frame,
                "We are done you can continue.");
    }

    public boolean validateInput(){
        if(validateId(IDTextField.getText().toString()) && validatePassword(PasswordTextField.getText().toString()) && validateName(FirstNameTextField.getText().toString(),LastNameTextField.getText().toString()) &&
                validatePhoneNumber(PhoneNumberTextField.getText().toString(),clinicPhoneTextField.getText().toString()) && validateEmail(EmailTextField.getText().toString()) && validateHours()
                 && validateCity(CityTextField.getText().toString(),clinicCityTextField.getText().toString()) && validateStreet(StreetTextField.getText().toString(),clinicStreetTextField.getText().toString())
                 && validateUniversity(UniversityTextField.getText().toString()))
            return true;
        else return false;
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
    public boolean validateHours() {
        String open = clinicOpeningHoursComboBox.getSelectedItem().toString();
        String close = clinicClosingHoursComboBox.getSelectedItem().toString();
        if(open == close){

            JOptionPane.showMessageDialog(null,"Clinic's closing hours can't be the same as open");
            return false;}
        else {
            return true;
        }
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
        boolean isValid =  firstName.length() > 1  && firstName.length()<=100 &&lastName.length()<=100 &&lastName.length() > 1;

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

    public boolean validatePhoneNumber(String phoneNumber,String clinicPhoneNumber){

        boolean isValid =  phoneNumber.length() == 10;
        boolean isValid2 =  clinicPhoneNumber.length() == 10;

        if(isValid && isValid2){
            for (int i = 0; i < phoneNumber.length() && isValid; i++){
                isValid = Character.isDigit(phoneNumber.charAt(i));
            }
            for (int i = 0; i < clinicPhoneNumber.length() && isValid2; i++){
                isValid2 = Character.isDigit(clinicPhoneNumber.charAt(i));
            }
        }

        if(!isValid){
            PhoneNumberTextField.setBorder(errorBorder);
            return false;
        }
        else{
            PhoneNumberTextField.setBorder(validBorder);
            if(!isValid2){
                clinicPhoneTextField.setBorder(errorBorder);
                return false;

            }
            else {
                clinicPhoneTextField.setBorder(validBorder);
                return true;
            }
        }


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

    public boolean validateCity(String city, String clinicCity){
        boolean isValid = true;
        if(city.length()>=3 && city.length()<=100){
            for(int i = 0 ; i < city.length() && isValid;i++){
                isValid = Character.isAlphabetic(city.charAt(i));
            }
            if(isValid)
                CityTextField.setBorder(validBorder);
            else {
                clinicCityTextField.setBorder(errorBorder);
                return false;
            }
            for(int i =0; i< clinicCity.length() && isValid;i++){
                isValid = Character.isAlphabetic(clinicCity.charAt(i));
            }
        }
        return isValid;
    }

    public boolean validateStreet(String street,String clinicStreet ){

        boolean isValid = true;
        if(street.length()>=3 && street.length()<=100){
            for(int i = 0 ; i < street.length() && isValid;i++){
                isValid = Character.isAlphabetic(street.charAt(i));
            }
            if(isValid)
                StreetTextField.setBorder(validBorder);
            else {
                clinicStreetTextField.setBorder(errorBorder);
                return false;
            }
            for(int i =0; i< clinicStreet.length() && isValid;i++){
                isValid = Character.isAlphabetic(clinicStreet.charAt(i));
            }

        }
        return isValid;
    }

    public boolean validateUniversity(String university){
        boolean isValid = university.length()>=3 && university.length()<=100;

        for (int i =0; i< university.length() && isValid;i++) {
            isValid = Character.isAlphabetic(university.charAt(i));
        }

        if(isValid)
            UniversityTextField.setBorder(validBorder);
        else
            UniversityTextField.setBorder(errorBorder);

        return isValid;
    }


    public static void main(Connection conn){
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new RegisterFormDoctor(conn, frame).rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void initializeHoursComboBox(){
        LocalTime opening = LocalTime.parse("00:00");
        for(int i=0;i<23;i++){
            clinicOpeningHoursComboBox.addItem(opening.toString());
            clinicClosingHoursComboBox.addItem(opening.toString());
            opening = opening.plusMinutes(60);
        }


    }
}
