package Classes;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import jdbmysql.Driver;


// Creates new table in the database to make appointments
public class AppoitmentSystemDoctors {


    private String id;
    private Connection conn;


    public  AppoitmentSystemDoctors(){ }


    public  AppoitmentSystemDoctors(String id,String start,String end,Connection conn){
        this.conn = conn;
        Doctor doctor = new Doctor(id);
        LocalTime opening = LocalTime.parse(start);
        LocalTime closing = LocalTime.parse(end);
        LocalDate todaysDate = LocalDate.now();

        createTable(doctor.getId(),opening,closing,todaysDate);
    }



    private void createTable(String id,LocalTime open,LocalTime close,LocalDate today) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("CREATE TABLE zzz? (openDate varchar(500),start varchar (500),end varchar (500),patientId varchar (500))");
            ps.setInt(1,Integer.parseInt(id));
            ps.executeUpdate();
        }
        catch(Exception exc){exc.printStackTrace();
        }

        for (int i = 0; i < 180; i++) {

                today = today.plusDays(1);
                LocalTime opening = LocalTime.parse(open.toString());
                LocalTime closing = LocalTime.parse(close.toString());


                int value;
                value = opening.compareTo(close);

                while (value !=0) {
                    try {
                        ps = conn.prepareStatement("INSERT INTO zzz? (openDate,start,end,patientId) VALUES (?,?,?,?)");
                        ps.setInt(1,Integer.parseInt(id));
                        ps.setString(2, today.toString());
                        ps.setString(3, opening.toString());
                        ps.setString(4, closing.toString());
                        ps.setInt(5, 0);
                        ps.executeUpdate();
                        opening = opening.plusMinutes(30);
                        value = opening.compareTo(close);

                    }
                    catch(Exception exc){exc.printStackTrace();
                    }
                }
            }
        }
    }






