
import Classes.AppoitmentSystemDoctors;
import GUI.Login;

import javax.swing.*;
import javax.swing.UIManager.*;

import jdbmysql.Driver;
import com.sun.jdi.connect.spi.Connection;

public class Main{
    public static void main(String[] args) {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        //Creating login Panel which is the first panel to activate all other panels
        Login loginPanel = new Login();
        loginPanel.main(null);





    }
}