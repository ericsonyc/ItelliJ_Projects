package exercises;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by ericson on 2015/8/16 0016.
 */
public class RollableTest {
    public static void main(String[] args) throws Exception{
        ResultSet rs=null;
        PreparedStatement ps=null;
        Connection conn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){

        }
    }
}
