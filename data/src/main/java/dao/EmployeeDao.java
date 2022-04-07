package dao;

import model.EmployeeCard;

import java.sql.*;
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
                employeeCard = new EmployeeCard();

                employeeCard.setPersonalData(resultSet.getString(2));
                employeeCard.setPosition_id(resultSet.getLong(3));
                employeeCard.setDepartment_id(resultSet.getLong(4));
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

            preparedStatement.setString(1, employeeCard.getPersonalData());
            if (employeeCard.getPosition_id() == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            } else {
                preparedStatement.setLong(2, employeeCard.getPosition_id());
            }
            preparedStatement.setLong(3, employeeCard.getDepartment_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(EmployeeCard employeeCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from employees where personal_info = ? and position_id = ? and department_id = ?")) {
            preparedStatement.setString(1, employeeCard.getPersonalData());
            preparedStatement.setLong(2, employeeCard.getPosition_id());
            preparedStatement.setLong(3, employeeCard.getDepartment_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(EmployeeCard employeeCard, long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update employees set personal_info = ?, position_id = ?, department_id = ? where id = ?")) {

            preparedStatement.setString(1, employeeCard.getPersonalData());
            if (employeeCard.getPosition_id() == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            } else {
                preparedStatement.setLong(2, employeeCard.getPosition_id());
            }
            preparedStatement.setLong(3, employeeCard.getDepartment_id());
            preparedStatement.setLong(4, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        while (resultSet.next()) {
            EmployeeCard employeeCard = new EmployeeCard();

            employeeCard.setPersonalData(resultSet.getString(2));
            employeeCard.setPosition_id(resultSet.getLong(3));
            employeeCard.setDepartment_id(resultSet.getLong(4));

            employeeCardList.add(employeeCard);
        }
    }

    public void redefineEmployee(EmployeeCard employee, long newDepartmentId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update employees set department_id = ? where (personal_info = ? and position_id = ? and department_id = ?)")) {

            preparedStatement.setLong(1, newDepartmentId);
            preparedStatement.setString(2, employee.getPersonalData());
            preparedStatement.setLong(3, employee.getPosition_id());
            preparedStatement.setLong(4, employee.getDepartment_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDepartmentEmployees(long departmentId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update employees set department_id = ? where department_id = ?")) {

            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setLong(2, departmentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeDepartmentEmployees(long departmentId, long departmentParentId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update employees set department_id = ? where department_id = ?")) {
            preparedStatement.setLong(1, departmentParentId);
            preparedStatement.setLong(2, departmentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
