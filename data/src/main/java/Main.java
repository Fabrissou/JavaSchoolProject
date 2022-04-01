import conection.DirectConnectionBuilder;
import conection.PoolConnectionBuilder;
import dao.DepartmentDirectoryDao;
import dao.EmployeeDirectoryDao;
import model.DepartmentCard;
import model.EmployeeCard;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DepartmentDirectoryDao dao = new DepartmentDirectoryDao();
        DepartmentCard departmentCard = dao.get(13);
        departmentCard.setName("newDepartment");
        departmentCard.setHierarchy("three");
        dao.save(departmentCard);
    }
}
