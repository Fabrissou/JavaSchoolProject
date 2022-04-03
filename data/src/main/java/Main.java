import dao.DepartmentDirectoryDao;
import dao.EmployeeDao;
import dao.PositionDao;
import model.DepartmentCard;
import model.EmployeeCard;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EmployeeCard d = new EmployeeCard();
        EmployeeDao dao = new EmployeeDao();

        d.setPersonalData("gdbgb");
        d.setPosition("designer");
        d.setDepartment("section7");

        dao.save(d);
    }
}
