package Classes;
import jdbmysql.Driver;
import javax.print.Doc;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

abstract class Visit {
    protected String caseID;
    protected Driver myDriver;
    protected Connection conn;
    protected Patient patient;
    protected Doctor doctor;


     public Visit(){
         Driver myDriver = Driver.getInstance();
         // Establish connection with database
         Connection conn = myDriver.getMyConn();
         this.conn = conn;

    }

    public Timestamp createTimeStamp(){
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            return ts;
    }


}
