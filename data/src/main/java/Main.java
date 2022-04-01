import conection.DirectConnectionBuilder;
import conection.PoolConnectionBuilder;
import dao.EmployeeDirectoryDao;
import model.EmployeeCard;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EmployeeCard employeeCard = new EmployeeCard("Сенькина Ангелина", "3D designer",
                "six");

        EmployeeDirectoryDao dao = new EmployeeDirectoryDao();

        dao.save(employeeCard);
    }
}
