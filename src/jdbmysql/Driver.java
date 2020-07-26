//package jdbmysql;
//
//import java.sql.*;
//
//public class Driver {
//
//    public static void main(String[] args) throws SQLException {
//
//        Connection myConn = null;
//        Statement myStmt = null;
//        ResultSet myRs = null;
//
//        try {
//            // 1. Get a connection to database
//            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/idoctor", "root" , "1234");
//
//            // 2. Create a statement
//            myStmt = myConn.createStatement();
//
//            // 3. Execute SQL query
//
////            String sql = "insert into doctorslogins " + " (username,password)" - Insert data
////                    + " values ('checing789', 'David')";
////            myStmt.executeUpdate(sql);
////
////            myRs = myStmt.executeQuery("select * from doctorslogins");  - select data
//
////            PreparedStatement ps =
////                    myConn.prepareStatement
////                            ("select username from doctorslogins where username =  ? ");
//
////            String usernameCheck = "John";
////
////            PreparedStatement ps =
////                    myConn.prepareStatement
////                            ("SELECT username FROM doctorslogins WHERE username = ?");
////            ps.setString (1, usernameCheck);
////             myRs = ps.executeQuery();
//
//
//
//
//            //myRs = myStmt.executeQuery("select username from doctorslogins where username =  ");
//
//            // 4. Process the result set
//            if (myRs.next()) { //while
//                System.out.println(myRs.getString("username"));
////                System.out.println(myRs.getString("username") + ", " + myRs.getString("password"));
//            }
//            else{
//                System.out.println("NULL");
//            }
//        }
//        catch (Exception exc) {
//            exc.printStackTrace();
//        }
//        finally {
//            if (myRs != null) {
//                myRs.close();
//            }
//
//            if (myStmt != null) {
//                myStmt.close();
//            }
//
//            if (myConn != null) {
//                myConn.close();
//            }
//        }
//    }
//
//}

package jdbmysql;
import java.sql.*;

public  class Driver {

    private static Driver instance = new Driver();
    private Connection myConn = null;

   private Driver(){
        try{
            myConn = DriverManager.getConnection("jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2356427", "sql2356427", "tT3*bA2!");

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public Connection getMyConn() {
        return myConn;
    }

    public void closeConn(){

       try{
           myConn.close();
       }

       catch (Exception exc){
           exc.printStackTrace();
       }
    }
    public static Driver getInstance(){
       return instance;

    }

}