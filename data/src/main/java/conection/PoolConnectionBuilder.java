package conection;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder {
    private static BasicDataSource basicDataSource = new BasicDataSource();

    static {
        String url = "jdbc:postgresql://localhost:5432/", dbName = "javaschool", user = "postgres", pass = "1234";
        basicDataSource.setUrl(url + dbName);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pass);
        basicDataSource.setMinIdle(5);
        basicDataSource.setMinIdle(15);
        basicDataSource.setInitialSize(15);
    }

    public static Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }
}
