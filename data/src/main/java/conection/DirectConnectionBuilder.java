package conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DirectConnectionBuilder {
    private static String url = "jdbc:postgresql://localhost:5432/", dbName = "javaschool",
            user = "postgres", pass = "1234";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url + dbName, user, pass);
    }
}
