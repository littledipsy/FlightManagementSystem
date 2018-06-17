package Customer;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect
{
    Connection conn;
    public Connection connect()
    {
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";

        //URL
        String url = "jdbc:mysql://localhost:3306/FlightManagementSystem?characterEncoding=utf8&useSSL=true";

        // MySQL配置时的用户名
        String user = "root";

        // MySQL配置时的密码
        String password = "eric123.";

        try
        {
            // 加载驱动程序
            Class.forName(driver);

            // 连续数据库
            conn = DriverManager.getConnection(url, user, password);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  conn;
    }
}
