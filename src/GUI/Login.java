package GUI;
import java.awt.event.*;
import java.sql.*;
import jdbmysql.Driver;
import javax.swing.*;
import java.sql.Connection;


public class Login {
    private JButton btnLogin;
    private JPanel LoginMain;
    private JTextField textUsername;
    private JTextField textPassword;
    private JLabel labelImg;
    private JCheckBox doctorCheckBox;
    private JCheckBox patientCheckBox;
    private JButton btnNewAccount;
    private JFrame frame;
    private Connection conn;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login(frame).LoginMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }


    public Login(){
    }


    public Login(JFrame frame) {

        this.frame = frame;
        Driver myDriver = Driver.getInstance();
        // Establish connection with database
        Connection conn = myDriver.getMyConn();
        this.conn = conn;

        // Makes button group for doctor or patient option
        createBtnGroup();

        // Create Key Listeners
        createKeyListeners();

    }



    public boolean checkUsernameANDpassword(String username, String password, boolean type,Connection conn){

        ResultSet myRs = null;
        PreparedStatement ps = null;

        try {
            // IF type == doctor then true
            if(type){
             ps = conn.prepareStatement("SELECT * from loginDoctor where id=? and password=? "); }

            else{
                 ps = conn.prepareStatement("SELECT * from loginPatient where id=? and password=? "); }

            ps.setString(1, username);
            ps.setString(2, password);
            myRs = ps.executeQuery();

            if(myRs.next()){
                return  true; }
            else
            {
                return false; }

        }
        catch (Exception exc){
            exc.printStackTrace();
            return false;
        }
    }


    // Idoctor icon
    private void createUIComponents() {
        // TODO: place custom component creation code here
        labelImg = new JLabel(new ImageIcon("E:\\Idoctor-project\\project\\lib\\idoctor.jpg"));

    }


    public void createBtnGroup(){
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(doctorCheckBox);
        btnGroup.add(patientCheckBox);
        patientCheckBox.doClick();

    }


    public void createKeyListeners(){
        //Check with MySQL database if username and password match when pressed enter or manually
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressed();
            }
        });

        //Check with MySQL database if username and password match when pressed enter or manually when ENTER pressed
        textPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==10)
                    pressed();
            }
        });
        // New account button
        btnNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newAccountPressed();
            }
        });


    }

    private void newAccountPressed(){

        if(doctorCheckBox.isSelected()) {
            RegisterFormDoctor registerPanelDoctor = new RegisterFormDoctor();
            registerPanelDoctor.main(conn);
            frame.dispose();
        }
        else {
            RegisterFormPatient registerPanelPatient = new RegisterFormPatient();
            registerPanelPatient.main(conn);
            frame.dispose();

        }
    }


    private void pressed(){

        String username = textUsername.getText();
        String password = textPassword.getText();

        if(username.isEmpty()|| password.isEmpty()){
            JOptionPane.showMessageDialog(null,"Username or password are empty");
        }
        else{
            // checks if it is doctor or patient box was checked
            boolean type = true;
            if(doctorCheckBox.isSelected()) type = true;
            else type = false;

            if(checkUsernameANDpassword(username,password,type,conn))
            {
                if(type == false) {
                    PatientMain patientMainPanel = new PatientMain();
                    patientMainPanel.main(username, conn);
                    frame.dispose();
                }
                else{
                    DoctorMain doctorMainPanel = new DoctorMain();
                    doctorMainPanel.main(username,conn);
                    frame.dispose();
                }

            }
            else{
                JOptionPane.showMessageDialog(null,"Username/password wrong or patient/doctor check box");
            }
        }
    }

}
