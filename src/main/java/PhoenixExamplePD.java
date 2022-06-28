
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
//import org.apache.hadoop.hbase.client.HConnectionManager;

public class PhoenixExamplePD {
    public static void main(String args[]) throws Exception {
        Connection conn;
        Properties prop = new Properties();
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        conn =  DriverManager.getConnection("jdbc:phoenix:192.168.2.13:2181/hbase");
        System.out.println("got connection");
        ResultSet rst = conn.createStatement().executeQuery("select * from javatest");
        while (rst.next()) {
            System.out.println(rst.getString(1) + " " + rst.getString(2));
        }
    }
}
