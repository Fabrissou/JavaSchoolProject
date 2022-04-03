package dao;

import liquibase.pro.packaged.D;
import model.EmployeeCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements BaseDao<EmployeeCard> {
    @Override
    public EmployeeCard get(long id) {
        EmployeeCard employeeCard = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from employees where id = ?")){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                PositionDao positionDao = new PositionDao();
                DepartmentDirectoryDao departmentDirectoryDao = new DepartmentDirectoryDao();
                employeeCard = new EmployeeCard();

                employeeCard.setPersonalData(resultSet.getString(2));
                employeeCard.setPosition(positionDao.get(resultSet.getLong(3)));
                employeeCard.setDepartment(departmentDirectoryDao.getDepartmentName(resultSet.getLong(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeCard;
    }

    @Override
    public List<EmployeeCard> getAll() {
        List<EmployeeCard> employeeCardList = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from employees")) {
            employeeCardList = new ArrayList<>();
            resultSetToList(employeeCardList, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeCardList;
    }

    @Override
    public void save(EmployeeCard employeeCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into employees (personal_info, position_id, department_id) values (?, ?, ?)")) {

            PositionDao positionDao = new PositionDao();
            DepartmentDirectoryDao departmentDirectoryDao = new DepartmentDirectoryDao();

            preparedStatement.setString(1, employeeCard.getPersonalData());
            preparedStatement.setLong(2, positionDao.getPositionId(employeeCard.getPosition()));
            preparedStatement.setLong(3, departmentDirectoryDao.getDepartmentId(employeeCard.getDepartment()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(EmployeeCard employeeCard) {

    }

    public List<EmployeeCard> getEmployeeList(long department_id) {
        List<EmployeeCard> employeeCardList = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from employees where department_id = ?")) {
            preparedStatement.setLong(1, department_id);
            employeeCardList = new ArrayList<>();
            resultSetToList(employeeCardList, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeCardList;
    }

    private void resultSetToList(List<EmployeeCard> employeeCardList, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        PositionDao positionDao = new PositionDao();
        DepartmentDirectoryDao departmentDirectoryDao = new DepartmentDirectoryDao();


        while (resultSet.next()) {
            EmployeeCard employeeCard = new EmployeeCard();

            employeeCard.setPersonalData(resultSet.getString(2));
            employeeCard.setPosition(positionDao.get(resultSet.getLong(3)));
            employeeCard.setDepartment(departmentDirectoryDao.getDepartmentName(resultSet.getLong(4)));

            employeeCardList.add(employeeCard);
        }
    }
}
